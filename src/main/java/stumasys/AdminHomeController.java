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

import stumasys.db.Database;
import stumasys.db.Course;
import stumasys.db.User;


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

    @RequestMapping(value = "/api/get_course/{year}/{courseCode}", produces = "application/json")
    @ResponseBody
    public String getCourse(        // TODO: Fill this out
        @PathVariable String year,
        @PathVariable String courseCode
    ){


        return "";
    }

    @RequestMapping(value = "/api/get_user/{uId}", produces = "application/json")
    @ResponseBody
    public String getUser(   // TODO: student or user
        @PathVariable String uId
    ){
        User user = db.getUser(uId);

        if (user == null){
            return "User does not exist";
        }

        if (user instanceof Student)){
            //
        }
        else if (user instanceof AdminStaff){

        }
        else{   // is a lecturer

        }

        return "";
    }

    private String lect(){
        return "";
    }
    private String stu(){
        return "";
    }
}
