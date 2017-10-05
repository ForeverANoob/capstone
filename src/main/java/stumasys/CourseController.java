package stumasys;

import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

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
            // TODO: show appropriate page for student users, and lecturers/coordinator
        return "course";
        }

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
            return "[]";
        }

        // encoding the mark table into JSON and returning it
        String ret = "[";
            Map.Entry<String,Integer> entry = entryItr.next();
            ret += "[\"" + entry.getKey() + "\",\"" + entry.getValue().toString() + "]";

            while (entryItr.hasNext()) {
                entry = entryItr.next();
                ret += ",[\"" + entry.getKey() + "\",\"" + entry.getValue().toString() + "]";
            }
        ret += "]";

        return ret;
    }

    @RequestMapping(value ="/course/{year}/{code}/{assId}")
    public String assessmentPageHandler(
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
    
}
