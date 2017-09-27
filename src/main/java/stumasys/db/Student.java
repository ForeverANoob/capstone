package stumasys.db;

import java.util.List;
import java.util.Collections;

public class Student extends User {
    private List<Course> involvedIn; // list of all courses this student
                                     // is enrolled in, tutoring for, etc.  // should it maybe be a map?
    public Student(
            String id, List<Course> courses,
            byte[] pwDHash, byte[] pwHashSalt
    ){
        super(id, pwDHash, pwHashSalt);
        involvedIn = courses;
    }

    public List<Course> getInvolvedCourses() {
        return Collections.unmodifiableList(involvedIn);
    }

    public boolean addCourse(Course c) { // returns a success status
        involvedIn.add(c);
        return true;
    }

    public boolean removeCourse(Course c) { // returns success status
        return involvedIn.remove(c);
    }
/*
    public boolean setRawAssessmentMark(String cId, String aId, int mark){
        //Course tmp = involvedIn.get(cId);        // get the course id to then get the assessment id
        Course tmp = null;
        for(int i = 0; i < involvedIn.size(); i++){ // yes Danny i know its inefficient
            if (cId.equals(involvedIn.get(i).getID())){
                tmp = involvedIn.get(i);
                break;
            }
        }
        if (tmp == null){       // course not found
            return false;
        }
        // modify the mark
        return tmp.getAssessment(aId).setStudentMark(mark);     // returns false if the assessment doesn't exist
    }*/
    
    // TODO: stage 4: track additions/removals from courses to update the DB correctly
}
