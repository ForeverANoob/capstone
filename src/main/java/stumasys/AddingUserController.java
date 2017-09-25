package stumasys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.web.ErrorController;

@Controller
public class AddingUserController {

    //private static final String PATH = "/error";

    @RequestMapping(value = "/course/{year}/{courseCode}/AddingUser")
    public String indexHandler(
            Model model,
            HttpServletResponse servletRes
    ){
        return "AddingUser";
    }
    /*
    @RequestMapping(value = "/login/final")
    public String re(
        Model model,
        HttpServletResponse servletRes
    ){
        return "final";
    }
    */
}
