package stumasys;

import java.util.HashMap;
import java.util.Map;
import java.security.Principal;
import java.util.*;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import stumasys.db.Database;
import stumasys.db.Course;
import stumasys.db.Assessment;
import stumasys.db.User;

@Controller
public class SearchController {

    private Database db;

    @Autowired
    public void setDatabase(Database db) {
        this.db = db;
    }

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

    @RequestMapping(value = "/api/get_search_results", method = RequestMethod.POST) // TODO: search REST api function that actually searches and gives results, that will be fetched by JS on the /search webpage
    public String yieldSearchResults(
        @RequestParam("searchTerm")
        String searchTerm
    ){
        // TODO: get the search results for this search term from the DB class
        // It must return a String-formatted JSON object
        // the JSON object must be a map, with each year mapped to a set
        // of course codes.
        //
        // we'll then later write Javascript that converts that information
        // into a whole bunch of DIVs inside the search page, when you click
        // on them they redirect you to /course/YEAR/COURSE_CODE

        String proxy = "csc1";  // TODO: This will act as the course typed in the search result
/*
        String split = proxy.split("_");

        Course c = db.getCourse(proxy[0], Integer.parseint(proxy[1]));

        if (c == null){
            return "that course does not exist"
        }

        List<Assessment> a = c.getAssessments();
        if (a == null){
            return "No assessments";
        }
        String ret = "[[" + proxy[0]+","+ proxy[1] +"],";

        for (int i = 0; i < c.size(); i++){

            Map<String,Integer> tmp = c.get(i).getWholeTable();

            Iterator<Map.Entry<String,Integer>> entryItr = tmp.entrySet().iterator();

            if (!entryItr.hasNext()) {
                return "[]";            // not sure about this
            }
            ret += "[";
            // encoding the mark table into JSON and returning it
            Map.Entry<String,Integer> entry = entryItr.next();
            ret += "[\"" + entry.getKey() + "\",\"" + entry.getValue().toString() + "]";

            while (entryItr.hasNext()) {
                entry = entryItr.next();
                ret += ",[\"" + entry.getKey() + "\",\"" + entry.getValue().toString() + "]";
            }
            ret += "]";

        }

        return ret+"]";*/
        List<Course> cs = null;
        if (this.isInteger(proxy)){      // distiguish between year and course typed in
            cs = db.getLikeYear(proxy);
        }
        else{
            cs = db.getLikeCourse(proxy);
        }

        if (cs == null){
            return "No course like that was found";
        }

        String ret = "[";

        for (int i = 0; i < cs.size(); i++){
            if(i == 0){
                ret += "\"" + cs.get(0).getId() + "\"";
            }else{
                ret += ",\"" + cs.get(i).getId() + "\"";
            }
        }
        ret += "]";
        return ret;

    }
/*
    @GetMapping
    public Map<String, String> sayHello(){
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");

        return map;
    }
*/

private boolean isInteger(String str) {     // checks if the str is a number
    int size = str.length();

    for (int i = 0; i < size; i++) {
        if (!Character.isDigit(str.charAt(i))) {
            return false;
        }
    }
    return size > 0;
    }

}
