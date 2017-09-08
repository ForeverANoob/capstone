
package stumasys;

import java.util.ArrayList;


public class Student extends User{

    private ArrayList<Courses> courses = new ArrayList<Courses>();

    public Student(String id, String pass, ArrayList<Courses> c ){
        super(id, pass);
        courses = c;    // hard code?

    }



}
