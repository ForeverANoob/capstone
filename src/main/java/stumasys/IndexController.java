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
public class IndexController {

    //private static final String PATH = "/error";

    @RequestMapping(value = "/")
    public String indexHandler(
            Model model,
            HttpServletResponse servletRes
    ){
        /*if (cookie.equals("")) {
            model.addAttribute("x", "FiftyNineRedHorses");
            servletRes.addCookie(new Cookie("authCookie", "SixtySixRedHorses"));
        } else {
            model.addAttribute("x", cookie);
        }*/

        // TODO: remove this class or the LoginController? (not sure if necessary)

        return "login";
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
