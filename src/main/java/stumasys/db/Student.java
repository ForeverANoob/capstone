package stumasys.db;

import java.util.List;
import java.util.Collections;
import java.sql.Connection;

public class Student extends User {

    public Student(String id, Connection con){
        super(id, con);
    }

    public List<Course> getInvolvedCourses() {         // TODO: sql
        return null;
    }

    public boolean addCourse(Course c) {              // TODO: sql
        return true;
    }

    public boolean removeCourse(Course c) {            // TODO: sql
        return true;
    }
    public void deregister(String id){
        //
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
