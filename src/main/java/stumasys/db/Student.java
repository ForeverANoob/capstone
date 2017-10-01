package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends User {

    public Student(String id, Connection con){
        super(id, con);
        getInvolvedCourses();
    }

    public List<Course> getInvolvedCourses() {         // TODO: sql

        try{
            Statement st = con.createStatement();
            String sql = "SELECT * FROM users.user_courses WHERE user_id = '" + this.id + "'";
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                Course c = new Course(rs.getString("course_id"), rs.getInt("year"), this.con);
                courses.add(c);
            }

            return courses;
        }catch(SQLException e){ System.out.println("Error in getting involved courses " + e); return null; }    // not sure about this
    }

    public boolean addCourse(Course c) {              // TODO: sql, maybe?
        return true;
    }

    public boolean removeCourse(Course c) {            // TODO: sql, mabe?
        return true;
    }
    public void deregister(String id){                 // TODO: maybe?
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
    public String toString(){
        return super.toString() + " student";
    }
}
