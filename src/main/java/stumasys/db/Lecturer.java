package stumasys.db;

import java.util.List;
import java.util.Collections;
import java.sql.Connection;

public class Lecturer extends User {

    public Lecturer(String id, Connection con){
        super(id, con);
    }

    public String getDepartment() {         // TODO: sql
        return "";
    }
    public void setDepartment(String d){    // TODO: sql

    }

    public boolean addCourse(Course c) {    // TODO: sql
        return true;
    }

    public boolean removeCourse(Course c) { // TODO: sql
        return true;
    }

    public List<Course> getCourses(){       // TODO: sql
        return null;
    }
}
