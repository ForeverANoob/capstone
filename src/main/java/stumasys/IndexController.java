package stumasys;

import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class IndexController {

    //private static final String PATH = "/error";
    //private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    // TODO: move the handler methods of AdminHomeController and StudentHomeController into this file, maybe rename this file to HomeController, so that we don't have to do this ridiculous bullshit hackery belo:

    
    private StudentHomeController stuHomeCtrler;
    private AdminHomeController admHomeCtrler;

    @Autowired
    public void setStudentHomeController(StudentHomeController stuHomeCtrler) {
        this.stuHomeCtrler = stuHomeCtrler;
    }

    @Autowired
    public void setAdminHomeController(AdminHomeController admHomeCtrler) {
        this.admHomeCtrler = admHomeCtrler;
    }

    @RequestMapping(value = "/")
    public String indexHandler(
            Model model,
            HttpServletResponse servletRes,
            HttpServletRequest servletReq,
            Principal p
    ){
        if (servletReq.isUserInRole("ADMIN_STAFF")) {
            return admHomeCtrler.homeHandler(model, servletRes);
        } else if (servletReq.isUserInRole("STUDENT")) {
            return stuHomeCtrler.homeHandler(p, model, servletRes);
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
