package stumasys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping(value = "/course/{year}/{courseCode}")
    public String courseHandler(
            @PathVariable String year,
            @PathVariable String courseCode,
            Model model,
            HttpServletResponse servletRes
    ){
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("year", year);

        // TODO: load more actual content into the Model (requires simultaneous work on the HTML template)

        return "course";
    }
}
