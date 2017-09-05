package stumasys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String indexHandler(
            @CookieValue(value = "authCookie", defaultValue = "") String cookie,
            Model model,
            HttpServletResponse servletRes
    ){
        if (cookie.equals("")) {
            model.addAttribute("x", "FiftyNineRedHorses");
            servletRes.addCookie(new Cookie("authCookie", "SixtySixRedHorses"));
        } else {
            model.addAttribute("x", cookie);
        }
        return "index";
    }

}
