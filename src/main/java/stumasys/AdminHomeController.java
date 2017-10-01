package stumasys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import stumasys.db.Database;
import stumasys.db.Course;
import stumasys.db.User;
import stumasys.db.Student;
import stumasys.db.AdminStaff;
import stumasys.db.Lecturer;



@Controller
public class AdminHomeController {

    private Database db;

    @Autowired
    public void setDatabase(Database db) {
        this.db = db;
    }

    @RequestMapping(value = "/AdminHome")
    public String homeHandler(
            Model model,
            HttpServletResponse servletRes
    ){
        // TODO: return different (non-student/student) homepages depending on authorisation level
        // TODO: retrieve the list of "relevant" courses (for both non-students/students)
        return "AdminHome";
    }


    /**
      *
      * Querys to do with courses
      *
      */
    @RequestMapping(value = "/api/get_course/{year}/{courseCode}", produces = "application/json")
    @ResponseBody
    public String getCourse(      
        @PathVariable int year,
        @PathVariable String courseCode
    ){
        Course c = db.getCourse(courseCode, year);  // TODO: check if this is fine
        if (c == null) {
            return "null";
        }
        String aId = "no"; // what?
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


    /**
      *
      * For querying about participants
      *
      */
    @RequestMapping(value = "/api/get_user/{uId}", produces = "application/json")
    @ResponseBody
    public String getUser(   // TODO: student or user
        @PathVariable String uId
    ){
        User user = db.getUser(uId);

        if (user == null){
            return "User does not exist";
        }
        else if (user instanceof Student){
            return this.stu((Student)user, uId);                 // TODO: i wonder if this is legit
        }
        else if (user instanceof AdminStaff){
            return this.admin((AdminStaff)user, uId);
        }
        else{   // is a lecturer
            return this.lect((Lecturer)user, uId);
        }
    }

    /**
      * Student json info
      */
    private String stu(Student stu, String id){
        List<Course> c = stu.getInvolvedCourses();  //all the courses
        String ret = "["+"["+id+"]";   // TODO: check if correct

        if (!c.isEmpty()) { // returns user with no courses
            return ret+"]";
        }

        for (int i = 0; i < c.size(); i++){
            ret += ",[\"" + i + "\",\"" + c.get(i).getId() + "]";
        }
        ret += "]";

        return ret;
    }
    /**
      * Admin json info
      */
    private String admin(AdminStaff adm, String id){
        return "["+"["+id+"]"+",["+adm.getDepartment()+"]";
    }
    /**
      * lecturer json info
      */
    private String lect(Lecturer lect, String id){
        String ret = "[" +"["+ id +"]" + ",["+lect.getDepartment()+"]"; // if lecturers has no department?
        List<Course> c = lect.getCourses();

        if (c == null){
            return ret+"]" ;
        }

        for (int i = 0; i < c.size(); i++){
            ret += ",[\"" + i + "\",\"" + c.get(i).getId() + "]";
        }
        ret += "]";

        return ret;
    }

}
