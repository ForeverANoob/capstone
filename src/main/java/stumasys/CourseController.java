package stumasys;

import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import stumasys.db.*;

@Controller
public class CourseController {

    private Database db;

    // the Database singleton is automatically injected here by Spring:
    @Autowired
    public void setDatabase(Database db) {
        this.db = db;
    }

    // controller for course page
    @RequestMapping(value = "/course/{year}/{courseCode}")
    public String courseHandler(
            @PathVariable String year,
            @PathVariable String courseCode,
            HttpServletResponse servletRes,
            HttpServletRequest servletReq,
            Model model,
            Principal p
    ){

        /* 1. Check if this course exists with the DB:
         *       if not, return error page. otherwise procede.
         * Admin staff/lecturers:
         *      2. load the last-used visible columns info from last visit, if available
         *      3. inject visible columns info into the JS and deliver the page
         *      4. client page then requests columns via REST api calls in the JS
         * Students:
         *      2. Generate a page with all assessments visible
         * */

        final String userId = p.getName();
        final User user = db.getUser(userId);

        Course course = db.getCourse(courseCode.toLowerCase(), Integer.parseInt(year));

        if (course == null) {
            return "404"; // TODO: respond with a better error page
        }


        model.addAttribute("courseCode", courseCode.toUpperCase());
        model.addAttribute("year", year);

        if (servletReq.isUserInRole("ADMIN_STAFF") || servletReq.isUserInRole("LECTURER")) {
            ((AdminStaff) user).updateRecentlyVeiwedCourses(course);

            Map<Assessment, Boolean> visibleCols = course.getPrevVisibleColumns((AdminStaff) user); // visibility of the columns, 
            Iterator<Map.Entry<Assessment, Boolean>> it = visibleCols.entrySet().iterator();

            // preparing some Javascript that determines what columns are visible when the page first loads:
            String configJs = "var visibleCols = {";
                if (it.hasNext()) {
                    Map.Entry<Assessment, Boolean> e = it.next();
                    configJs += "\"" + e.getKey().getId() + "\":[" + e.getValue() + ",\"" + e.getKey().getName() + "\"]";

                    while (it.hasNext()) {
                        e = it.next();
                        configJs += ",\"" + e.getKey().getId() + "\":[" + e.getValue() + ",\"" + e.getKey().getName() + "\"]";
                    }
                }
            configJs += "};\nvar courseCode = \""+courseCode+"\";\nvar courseYear = "+year+";";

            model.addAttribute("configJs", configJs);

            return "course";    // course?
        } else if (servletReq.isUserInRole("STUDENT")) {
            if (!course.isRegistered((Student)user)) {
                return "404"; // TODO: more appropriate error page
            }

            model.addAttribute("assessments", course.getAssessments());
            model.addAttribute("student", (Student)user);
            return "stuview_course";
        } else {
            // This should never happen, since the WebSecurityCfg specifies this page requires authentication, implying the user has some role
            System.err.println("ERROR: User without recognised role attempted to access course page.");
            return "500";
        }

    }

    @RequestMapping(value ="/course/{year}/{code}/{assId}")
    public String assessmentPageHandler( // TODO: this
            @PathVariable String year,
            @PathVariable String code,
            @PathVariable String assId,
            Model model
    ){
        Course course = db.getCourse(code, Integer.parseInt(year));
        if (course == null) {
            return "404";
        }

        Assessment assessment = course.getAssessment(Integer.parseInt(assId));
        if (assessment == null) {
            return "404";
        }

        // TODO: dynamise the 'assessment' template

        return "assessment";
    }

    // controls course creation page
    @RequestMapping(value="/create")
    public String courseCreationHandler() {
        return "CreateCourse"; // TODO: create another handler for the form action on this page.
    }

    // REST api, returns mark table of a given assessment as JSON
    @RequestMapping(value = "/api/get_assessment/{code}/{year}/{aId}", produces = "application/json")
    @ResponseBody
    public String getAssessmentMarks(
            @PathVariable String code,
            @PathVariable String year,
            @PathVariable String aId
    ){
        Course c = db.getCourse(code, Integer.parseInt(year));
        if (c == null) {
            return "null";
        }

        Map<String,Integer> markTbl = c.getAssessment(Integer.parseInt(aId)).getWholeTable();

        Iterator<Map.Entry<String,Integer>> entryItr = markTbl.entrySet().iterator();

        if (!entryItr.hasNext()) {
            return "{}";
        }

        // encoding the mark table into JSON and returning it
        String json = "{";
            Map.Entry<String,Integer> entry = entryItr.next();
            json += "\"" + entry.getKey() + "\":" + entry.getValue().toString();

            while (entryItr.hasNext()) {
                entry = entryItr.next();
                json += ",\"" + entry.getKey() + "\":" + entry.getValue().toString();
            }
        json += "}";

        return json;
    }

    // REST api, updates registration status of users in a course from a CSV exported by Peoplesoft
    @RequestMapping(value = "/api/upload_pplsoft/{year}/{courseCode}", method=RequestMethod.POST)
    @ResponseBody
    public String pplsoftHandler(
            @PathVariable String year,
            @PathVariable String courseCode,
            @RequestParam("file") MultipartFile file
    ){
        HashMap<String,Boolean> regStatus = new HashMap<String,Boolean>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;

            while ((line = br.readLine()) != null) {
                // there are nine columns:
                // Emplid,CampusID,Name,Term,ClassNbr,Subject,CatalogNbr,AcadProg,FinalGrade
                //        ^                                                       ^
                // The two with the little carots pointing to their first letters
                // are the only ones that matter to us.
                //
                // we look at the student numbers and updates the registration
                // status depending on if their "FinalGrade" column is INC
                String[] vals = line.split(",");

                if (vals.length != 9) {
                    return "Format error.";
                }

                regStatus.put(vals[1], Boolean.valueOf(!vals[8].equals("INC")));
            }
        } catch (Exception e) {
            System.err.println("An exception occured while attempting to parse a Peoplesoft CSV file.");
        }

        db.getCourse(courseCode, Integer.parseInt(year)).batchUpdateRegistrationStatus(regStatus);

        return "success";
    }

    
}
