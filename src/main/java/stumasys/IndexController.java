package stumasys;

import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;



import stumasys.db.User;
import stumasys.db.AdminStaff;
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
public class IndexController {

    private Database db;

    @Autowired
    public void setDatabase(Database db) {
        this.db = db;
    }

    @RequestMapping(value = "/")
    public String indexHandler(
            Model model,
            HttpServletResponse servletRes,
            HttpServletRequest servletReq,
            Principal p
    ){
        final String id = p.getName();

        if (servletReq.isUserInRole("ADMIN_STAFF")) {
            // NEW:TODO: Put recently viewed courses into this page as exemplified by the static HTML (currently DOING:)
            AdminStaff u = (AdminStaff) db.getUser(id);
            model.addAttribute("recentlyViewed", u.getRecentlyViewedCourses());
            return "AdminHome";

        } else if (servletReq.isUserInRole("STUDENT")) {
            HashMap<String, List<Assessment>> subjectsAndMarks = new HashMap<String, List<Assessment>>();

            Student stu = (Student) db.getUser(id);
            List<Course> courses = stu.getInvolvedCourses();

            for (Course c : courses) {
                List<Assessment> al = c.getAssessments();
                LinkedList<Assessment> fas = new LinkedList<Assessment>(); // Filtered ASsessments

                for (Assessment a : al) {
                    System.out.println("ALOHA _--------------");
                    if (a.isPublished() && a.isAvailableFromStudentHome()) {

                        System.out.println("PRETTT _--------------");
                        fas.add(a);
                    }
                }

                subjectsAndMarks.put(c.getCode().toUpperCase() + ", " + c.getYear(), fas);
            }

            model.addAttribute("username", id);
            model.addAttribute("student", (Student)db.getUser(id));
            model.addAttribute("subnmarks", subjectsAndMarks);

            return "StudentHome";
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/login")
    public String re(
        Model model,
        HttpServletResponse servletRes
    ){
        return "login";
    }
/*
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }*/
}
