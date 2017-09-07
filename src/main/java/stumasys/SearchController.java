package stumasys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.boot.autoconfigure.web.ErrorController;

@Controller
public class CourseController {

    //private static final String PATH = "/error";

    @RequestMapping(value = "/search/**")
    public String courseHandler(
            HttpServletRequest servletReq,
            Model model,
    ){
        return "search";
    }
}
