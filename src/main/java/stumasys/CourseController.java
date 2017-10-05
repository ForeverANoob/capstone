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

@Controller         // response functionality
public class CourseController {
    /*

    @Autowired      // links this with the CourseService
    CourseService cs;

    @RequestMapping(value = "/all")
    public Hashtable<String, Courses> getAll(){
        return cs.getAll();
    }
    @RequestMapping(value = "/{key}")       // something is wrong here...to tired to know what
    public Courses getCourse(@PathVariable("key") String key){
        return cs.getCourse(key);
    }
    */
    private Database db;

    @Autowired
    public void setDatabase(Database db) {
        this.db = db;
    }

    @RequestMapping(value = "/course/{year}/{courseCode}") // sorry for commenting out all of your code
    public String courseHandler(
            @PathVariable String year,
            @PathVariable String courseCode,
            Model model,
            HttpServletResponse servletRes,
            HttpServletRequest servletReq,
            Principal p
    ){

        /*
         * 1. Check if this course exists with the DB:
         *      if not, return error page. otherwise procede.
         * 2. load the last-used visible columns info from last visit
         * 3. put that vis-columns info into the JS and deliver the page
         * 4. client page then requests columns via REST api calls in the JS
         * */

        final String uId = p.getName();
        final User user = db.getUser(uId);

        Course c = db.getCourse(courseCode.toLowerCase(), Integer.parseInt(year));

        if (c == null) {
            return "404";// TODO: respond with a proper error page when course doesnt exist
        }


        model.addAttribute("courseCode", courseCode.toUpperCase());
        model.addAttribute("year", year);

        if (servletReq.isUserInRole("ADMIN_STAFF")) {
            ((AdminStaff) user).updateRecentlyVeiwedCourses(c);

            // preparing some Javascript that determines what columns are visible when the page first loads:
            String configJs = "var visibleCols = {";

            Map<Assessment, Boolean> visibleCols = c.getPrevVisibleColumns((AdminStaff) user);
            Iterator<Map.Entry<Assessment, Boolean>> it = visibleCols.entrySet().iterator();

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
        } else {
            if (!c.isRegistered((Student)user)) {
                return "404"; // TODO: more appropriate error page
            }

            model.addAttribute("username", uId);
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------");
            System.out.println(c.getAssessments());
            model.addAttribute("assessments", c.getAssessments());
            model.addAttribute("student", (Student)user);
            return "stuview_course"; // TODO: show appropriate page for student users, and lecturers/coordinator
        }

    }

    @RequestMapping(value ="/course/{year}/{code}/{assId}")
    public String assessmentPageHandler( // TODO: this
            @PathVariable String year,
            @PathVariable String code,
            @PathVariable String assId
    ){
        /*
        Course c = db.getCourse(code, year);
        if (c == null) {
            return "You just got rekt, bro"; // in the spirit of andre's helpful error messages
        }

        Assessment a = c.getAssessment(assId);
        if (a == null) {
            return "I'm just a poor boy from a poor family";
        }
        */

        return "assessment";
    }

    @RequestMapping(value="/create")
    public String courseCreationHandler() {
        return "CreateCourse";
    }

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

        Map<String,Integer> markTbl = c.getAssessment(aId).getWholeTable();

        Iterator<Map.Entry<String,Integer>> entryItr = markTbl.entrySet().iterator();

        if (!entryItr.hasNext()) {
            return "{}";
        }

        // encoding the mark table into JSON and returning it
        String ret = "{";
            Map.Entry<String,Integer> entry = entryItr.next();
            ret += "\"" + entry.getKey() + "\":" + entry.getValue().toString();

            while (entryItr.hasNext()) {
                entry = entryItr.next();
                ret += ",\"" + entry.getKey() + "\":" + entry.getValue().toString();
            }
        ret += "}";

        return ret;
    }

    @RequestMapping(value = "/api/upload_pplsoft/{year}/{courseCode}", method=RequestMethod.POST)
    @ResponseBody
    public String pplsoftHandler(
            @PathVariable String year,
            @PathVariable String courseCode,
            @RequestParam("file") MultipartFile file
    ){
        // TODO: get InputStream from the MultipartFile, do CSV parsing from it,
        // use that to update registration status of the students. also need to
        // export this type of file at some point...
        //System.out.println("?AYATAWDFSADFASDFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        HashMap<String,Boolean> regStatus = new HashMap<String,Boolean>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;

            while ((line = br.readLine()) != null) {
                // there are nine columns:
                // Emplid CampusID Name Term ClassNbr Subject CatalogNbr AcadProg FinalGrade
                //        ^stunum       ^ middle 2 digits are the year            ^ a status thing
                //                 ^ full name        {^      ^} together make the course code
                // The six with the little carots pointing to their first letters
                // are the only ones that matter to us.
                //
                // this code needs to examine which people are inthe course, so it
                // looks at the student numbers and updates the registration status 
                // depending on if their "FinalGrade" column is INC
                String[] vals = line.split(",");

                if (vals.length != 9) {
                    return "Format error.";
                }

                regStatus.put(vals[1], Boolean.valueOf(!vals[8].equals("INC")));
            }
        } catch (Exception e) {
            System.err.println("An exception occured while attempting to parse a Peoplesoft CSV file.");
        }

        Course c = db.getCourse(courseCode, Integer.parseInt(year));
        c.batchUpdateRegistrationStatus(regStatus);

        return "success";
    }

    
}
