package stumasys.db;

import java.util.List;
import java.util.Collections;

public class Lecturer extends User {
    private String department;
    private List<Course> involvedIn; // list of courses lecturing-in/convening

    public Lecturer(
            String id, String department, List<Course> courses

    ){
        super(id);
        this.involvedIn = courses;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String d){
        this.department = d;
    }

    public void addCourse(Course c) {
        if(involvedIn.contains(c)){
            return false;
        }
        involvedIn.add(c);
        return true;
    }

    public void removeCourse(Course c) {
        return involvedIn.remove(c);
    }

    public List<Course> getCourses(){
        return Collections.unmodifiableList(this.involvedIn);
    }
}
