package stumasys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginHandler(
            Model model,
            HttpServletResponse servletRes
    ){
        return "login";
    }

    @RequestMapping("/login/final")
    public String Authorized(
        Model model,
        HttpServletResponse servletRes
    ){
        return "index";
    }
}
