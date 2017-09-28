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
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class AssController {     // this class may be redundant since courseconstroller does this i think

    //private static final String PATH = "/error";

    @RequestMapping(value = "/course/{year}/{courseCode}/Assignment/{aId}")
    public String indexHandler(
            @PathVariable String year,
            @PathVariable String courseCode,
            @PathVariable String aId,
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
