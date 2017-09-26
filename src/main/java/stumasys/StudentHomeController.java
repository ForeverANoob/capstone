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


import stumasys.db.User;
import stumasys.db.Database;
import stumasys.db.Course;
import stumasys.db.Student;
import java.util.Hashtable;
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

    @RequestMapping(value = "/{ID}")    // originally was /StudentHome
    public String homeHandler(
            @PathVariable String ID,
            Model model,
            HttpServletResponse servletRes
    ){
        // TODO: return different (non-student/student) homepages depending on authorisation level
        // TODO: retrieve the list of "relevant" courses (for both non-students/students)

        User stu = db.getUser(ID);      // this assumes we are getting a student
        if (stu == null){
            return "Student does not exist";
        }

        model.addAttribute("StudentID", ID);    // will probably extand this to userId

        return "StudentHome";
    }

    @RequestMapping(value = "/api/get_studenthomepage/{sId}", produces = "application/json")    // right directory?
    @ResponseBody
    public String getCoursePage(
        @PathVariable String sId
    ){

        Student stu = (Student)db.getUser(sId);     // assuming a student already

        if (stu == null){
            return "that student does not exist";
        }

        List<Course> c = stu.getInvolvedCourses();  //all the courses

        // TODO: Get a map working for the json messages
        return "";
    }
}
