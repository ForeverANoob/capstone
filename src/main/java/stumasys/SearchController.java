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
public class SearchController {

    @RequestMapping(value = "/search/**")
    public String courseHandler(
            HttpServletRequest servletReq,
            Model model
    ){
        String reqURI = servletReq.getRequestURI();
        if (reqURI.length() > 8) {
            String searchTerm = reqURI.substring(8); 
            //model.addAttribute("searchTerm", java.net.URLDecoder.decode(searchTerm));
            model.addAttribute("searchTerm", (searchTerm));
        } else {
            model.addAttribute("searchTerm", "");
        }
        return "search";
    }

    @RequestRestMapping(value = "/api/get_search_results") // TODO: search REST api function that actually searches and gives results, that will be fetched by JS on the /search webpage
    public String yieldSearchResults() { // should return a String encoding of a Javascript object.
    }

}
