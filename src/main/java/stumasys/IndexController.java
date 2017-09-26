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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class IndexController {

    //private static final String PATH = "/error";
    //private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/")
    public String indexHandler(
            Model model,
            HttpServletResponse servletRes
    ){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET) // doe nothing
    public String re(
        Model model,
        HttpServletResponse servletRes
    ){
        return "/AdminHome";
    }

}
