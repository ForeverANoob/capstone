package stumasys;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestRestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping(value = "/api/get_search_results") // TODO: search REST api function that actually searches and gives results, that will be fetched by JS on the /search webpage
    public String yieldSearchResults(

    ){
        // TODO: get the search results for this search term from the DB class
        // It must return a String-formatted JSON object
        // the JSON object must be a map, with each year mapped to a set
        // of course codes.
        //
        // we'll then later write Javascript that converts that information
        // into a whole bunch of DIVs inside the search page, when you click
        // on them they redirect you to /course/YEAR/COURSE_CODE
        return "final";

    }
/*
    @GetMapping
    public Map<String, String> sayHello(){
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");

        return map;
    }
*/
}
