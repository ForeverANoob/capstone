package stumasys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;


@Controller
public class User {
   //@RequestMapping(value = "/index", method = RequestMethod.GET)
   //public String index() {
    //  return "index";
  // }
   @RequestMapping(value = "/redirect/login", method = RequestMethod.GET)
   public String redirect() {
      return "redirect:finalPage";
   }
   @RequestMapping(value = "/final", method = RequestMethod.GET)    // this is what's redirecting the page
   public String finalPage() {
      return "final";
   }
   @RequestMapping(value = "/login#", method = RequestMethod.GET)
   public String tmp(){
       return "#";
   }
   @RequestMapping(value = "/greeting", method = RequestMethod.GET)
   public String indexHandler(){
       return "greeting";
   }

   @RequestMapping(value = "login/final", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("register");
        return mav;
    }
}
