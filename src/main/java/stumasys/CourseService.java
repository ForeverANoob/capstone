package stumasys;

import org.springframework.stereotype.Service;
import java.util.Hashtable;
import java.util.ArrayList;

@Service
public class CourseService{

    /*
    private CourseRepository cr;

    public CourseService(CourseRepository cr){
        this.cr = cr;
    }

    public Iterable<Courses> list(){
        return cr.findAll();
    }

    public Courses save(Courses c){
        return cr.save(c);
    }

    public Iterable<Courses> list(List<Courses> courses){
        return cr.save(courses);
    }
    */
    private Hashtable<String, Courses> courses = new Hashtable<String, Courses>();

    
    public CourseService(){             // used as an alpha test
        Courses c1 = new Courses("gender studies", "2013");
        Courses c2 = new Courses("Something", "2017");
        Courses c3 = new Courses("Sax", "6996");

        courses.put("1", c1);
        courses.put("2", c2);
        courses.put("3", c3);
    }/*
    public CourseService(){             // beta test
        Database bb = new Database();   // have to initialize or is it done for us
        ArrayList<Courses> c = bb.getlist();
        for (int i = 0; i < c.size(); i++){
            courses.put(Integer.toString(i+1), c.get(i));     // f.y.i. i am really digging atom
        }
    }*/

    /* these are the different functionalities that will be provided */
    public Courses getCourse(String id){
        //ArrayList<Courses> a = new ArrayList<Courses>();
        //for (int i = 0; i < courses.size(); i++){  // don't like this O(n) search
        //    if(courses.get(i).getYear().equals(id)){
        //        a.add(courses.get(i));
        //    }
        //}
        if (courses.containsKey(id)){
            return courses.get(id);
        }
        else{
            return null;
        }
        //if(a.isEmpty()){ a.add(courses.get(0)); } // yes i'll have to clean the meat cleaver after this
        //return a;
    }
    public Hashtable<String, Courses> getAll(){
        return courses;
    }

}
