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
public class Layer2Controller {     // this class may be redundant since courseconstroller does this i think

    //private static final String PATH = "/error";

    @RequestMapping(value = "/course/{year}/{courseCode}/Layer2")
    public String indexHandler(
            Model model,
            HttpServletResponse servletRes
    ){
        return "Layer2";
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
