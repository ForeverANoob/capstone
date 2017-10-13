package stumasys;

import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

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

import org.apache.catalina.realm.GenericPrincipal;

import stumasys.db.User;
import stumasys.db.AdminStaff;
import stumasys.db.Database;
import stumasys.db.Course;
import stumasys.db.Student;
import stumasys.db.Assessment;
import stumasys.db.Lecturer;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

@Controller
public class IndexController {

    private Database db;

    // injects the Database singleton
    @Autowired
    public void setDatabase(Database db) {
        this.db = db;
    }

    // controller for home pages
    @RequestMapping(value = "/")
    public String indexHandler(
            Model model,
            HttpServletResponse servletRes,
            HttpServletRequest servletReq,
            Authentication auth
    ){
        final String id = auth.getName();

        boolean isAdmin = false;
        boolean isStudent = false;
        boolean isLecturer = false;
        boolean isPal = false;
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (ga.getAuthority().equals("student"))
                isStudent = true;
            if (ga.getAuthority().equals("admin"))
                isAdmin = true;
            if (ga.getAuthority().equals("lecturer"))
                isLecturer = true;
            if (ga.getAuthority().equals("pal"))
                isPal = true;
        }

        if(isPal){
            User u = db.getUser(id);
            model.addAttribute("recentlyViewed", new ArrayList<Course>());
            return "PalHome";

        }else if(isLecturer){
            Lecturer u = (Lecturer) db.getUser(id);
            model.addAttribute("recentlyViewed", u.getCourses());
            return "AdminHome";
        }
        else if (isAdmin) {
            AdminStaff u = (AdminStaff) db.getUser(id);
            model.addAttribute("recentlyViewed", u.getRecentlyViewedCourses());
            return "AdminHome";

        } else if (isStudent) {
            HashMap<Course, List<Assessment>> subjectsAndMarks = new HashMap<Course, List<Assessment>>();

            Student stu = (Student) db.getUser(id);
            List<Course> courses = stu.getInvolvedCourses();

            for (Course c : courses) {
                List<Assessment> al = c.getAssessments();
                LinkedList<Assessment> fas = new LinkedList<Assessment>(); // Filtered ASsessments

                for (Assessment a : al) {
                    if (a.isPublished() && a.isAvailableOnStudentHome()) {

                        fas.add(a);
                    }
                }

                subjectsAndMarks.put(c, fas);
            }

            model.addAttribute("username", id);
            model.addAttribute("student", (Student)db.getUser(id));
            model.addAttribute("subnmarks", subjectsAndMarks);
            model.addAttribute("courses", courses);

            return "StudentHome";
        } else {
            return null; // TODO: FIXME: if this ever occurs, things will crash/burn.. ACTUALLY: it turns out it just generates an empty page, because.... because Spring.
        }
    }

    // tells Spring which template to use for the login page
    @RequestMapping(value = "/login")
    public String re(
        Model model,
        HttpServletResponse servletRes
    ){
        return "login";
    }
}
