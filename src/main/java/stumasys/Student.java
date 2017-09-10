package stumasys;

import java.util.List;
import java.util.Collections;

public class Student extends User {
    private List<Course> involvedIn; // list of all courses this student
                                     // is enrolled in, tutoring for, etc.
    public Student(
            String id, ArrayList<Courses> courses,
            byte[] pwDHash, byte[] pwHashSalt
    ){
        super(id, pwDHash, pwHashSalt);
        involvedIn = courses;
    }

    public List<Course> getInvolvedCourses() {
        return Collections.unmodifiableList(involvedIn);
    }

//<<<<<<< HEAD
    public ArrayList<Courses> getCourses(){
        return this.courses;    // will have to make a copy of courses to be safe
//=======
}
    public boolean addCourse(Course c) { // returns a success status
        involvedIn.add(c);
        return true;
    }

    public boolean removeCourse(Course c) { // returns success status
        return involvedIn.remove(c);
//>>>>>>> e7ddbc83812f92db5ee2d292b304943d5b63e23e
    }

    // TODO: stage 4: track additions/removals from courses to update the DB correctly
}
