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

// makes error messages in the browser page rather than just in the console of
// the server.

@Controller
public class ErrorHandler implements ErrorController{

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(){
        return "An error has occured, v2";
    }
    @Override
    public String getErrorPath(){
        return PATH;
    }
}
