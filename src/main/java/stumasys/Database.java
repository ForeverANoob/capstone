package stumasys;
//import org.springframework.batch.item.ItemReader;
import java.util.ArrayList;

/**
    the main 'talker' to the from work

**/


public class Database/* implements ItemReader<Courses> */{

    private ArrayList<Courses> courses = new ArrayList<Courses>();
    private ArrayList<User> users = new ArrayList<User>(); // not sure about this
    private ArrayList<String> names = new ArrayList<String>();



    public Database(){  // hardcose, for prototype
        ArrayList<Courses> course = new ArrayList<Courses>();

        int[] a = new int[3];   a[0] = 50; a[1] = 70; a[2] = 60;
        int[] b = new int[3];   b[0] = 70; b[1] = 75; b[2] = 54;
        int[] c = new int[3];   c[0] = 65; c[1] = 87; c[2] = 65;
        int[] d = new int[3];   d[0] = 76; d[1] = 56; d[2] = 87;

        course.add(new Courses("csc1001f", 2015, a));
        course.add(new Courses("csc1002s", 2015, b));
        course.add(new Courses("csc2001f", 2016, c));
        course.add(new Courses("csc2001s", 2016, d));

        User admin = new Admin("admin", "123qwe");
        User stu = new Student("stu", "qwe123" , course);

        course.get(0).addUser(stu);
        course.get(1).addUser(stu);
        course.get(2).addUser(stu);
        course.get(3).addUser(stu);

        users.add(admin);
        users.add(stu);


        initialize();
    }

    public ArrayList<Courses> getlist(){
        return this.courses;
    }

    public void initialize(){

    }
    public Courses read(){
        return courses.get(0);
    }

    public String getRequest(String request){   // this gets the request from spring (in the future)?
        return null;
    }

    public void addCourse(String name, int year){ // admin can do this

    }


}
