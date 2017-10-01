package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
    private String code; // these two variables uniquely identify a given Course
    private int year;
    private List<Assessment> ass;
    private Connection con;

    public Course(String code, int year, Connection con){  // TODO: sql
        this.code = code;
        this.year = year;
        this.con = con;
        ass = new ArrayList<Assessment>(); // TODO:fill in the assessments


    }
    public String getId(){          // TODO: sql
        return year+"_"+code;
    }

    // assessments things
    public Assessment getAssessment(String id) {    // TODO: sql
        return null;
    }

    public void addAssessment(Assessment a){    // TODO: sql not here

    }
    public void setAssessments(List<Assessment> all){ // TODO: sql

    }
    public List<Assessment> getAssessments(){   // TODO: sql
        return null;
    }

    public boolean isRegistered(Student stu){ // TODO:    sql
        return true;
    }
    public void deregisterStudent(Student stu){ // TODO: sql

    }

    public void setParticipants(List<User> users){  // TODO: sql

    }

    public Lecturer getCourseConviner(){    // TODO: sql
        return null;
    }
    public void setCourseConviner(Lecturer c){  // TODO: sql

    }

    public List<Student> getTA(){       // TODO: sql
        return null;
    }

    public void setTA(Student ta){      // TODO: sql

    }
}
