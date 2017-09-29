package stumasys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

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


import stumasys.db.User;
import stumasys.db.Database;
import stumasys.db.Course;
import stumasys.db.Student;
import stumasys.db.Assessment;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

@Controller
public class StudentHomeController {

    private Database db;

    @Autowired
    public void setDatabase(Database db) {
        this.db = db;
    }

    @RequestMapping(value = "/StudentHome")    // originally was /StudentHome
    public String homeHandler(
            Principal p,
            Model model,
            HttpServletResponse servletRes
    ){
        // TODO: return different (non-student/student) homepages depending on authorisation level
        // TODO: retrieve the list of "relevant" courses (for both non-students/students)
        final String ID = p.getName();

        //ArrayList<HashMap<String, Integer>> everthing = new ArrayList<HashMap<String, Integer>>();
        //ArrayList<List<Integer>> everthing = new ArrayList<List<Integer>>();
        //ArrayList<List<Assessment>> everthing = new ArrayList<List<Assessment>>();
        //ArrayList<String> everthing = new ArrayList<String>();
        HashMap<String, List<Assessment>> everthing = new HashMap<String, List<Assessment>>();

        System.out.println("Getting student with name: " + ID);
        Student stu = (Student)db.getUser(ID);
        if (stu == null){
            return "Student does not exist";
        }

        List<Course> c = stu.getInvolvedCourses();
        if (c == null){
            return "In no courses brah";
        }
        //List<Integer> ma = new ArrayList<Integer>();
        //HashMap<String, Integer> ass = new HashMap<String, Integer>();
        for (int i = 0; i < c.size(); i++){
            List<Assessment> tmp = c.get(i).getAssessments();
            
            everthing.put(c.get(i).getID(), tmp);     // This holds all the assessments of all of the courses, may have to be changed

        }
        model.addAttribute("username", ID);
        model.addAttribute("subnmarks", everthing);    // will probably extand this to userId

        return "StudentHome";       // TODO: give proper html page
    }



/*
    @RequestMapping(value = "/api/get_studenthomepage/{sId}", produces = "application/json")    // right directory?
    @ResponseBody
    public String getCoursePage(
        @PathVariable String sId
    ){

        Student stu = (Student)db.getUser(sId);     // assuming a student already ....correct?

        if (stu == null){
            return "that student does not exist";
        }

        List<Course> c = stu.getInvolvedCourses();  //all the courses

        if (!c.isEmpty()) {
            return "[]";
        }

        String ret = "[";
        ret += "[\"" + i + "\",\"" + c.get(i).getID() + "]";
        for (int i = 1; i < c.size(); i++){
            ret += ",[\"" + i + "\",\"" + c.get(i).getID() + "]";  // TODO: check if correct
        }
        ret += "]";

        return ret;
    }*/
}
