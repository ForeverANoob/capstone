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
import java.util.LinkedList;

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
        final String id = p.getName();
        HashMap<String, List<Assessment>> subjectsAndMarks = new HashMap<String, List<Assessment>>();

        Student stu = (Student) db.getUser(id);
        List<Course> courses = stu.getInvolvedCourses();

        for (Course c : courses) {
            List<Assessment> al = c.getAssessments();
            LinkedList<Assessment> fas = new LinkedList<Assessment>(); // Filtered ASsessments

            for (Assessment a : al) {
                if (a.isPublished() && a.isAvailableFromStudentHome()) {
                    fas.add(a);
                }
            }

            subjectsAndMarks.put(c.getCode().toUpperCase(), fas);
        }

        model.addAttribute("username", id);
        model.addAttribute("subnmarks", subjectsAndMarks);

        return "StudentHome";
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
