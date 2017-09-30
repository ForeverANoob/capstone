package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Connection;

public class Course {
    private String code; // these two variables uniquely identify a given Course
    private int year;

    public Course(String code, int year, Connection con){  // TODO: sql

    }
    public String getId(){          // TODO: sql
        return "";
    }

    // assessments things
    public Assessment getAssessment(String id) {    // TODO: sql
        return null;
    }

    public void addAssessment(Assessment a){    // TODO: sql

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
