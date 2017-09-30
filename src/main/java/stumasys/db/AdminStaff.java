package stumasys.db;

import java.util.List;
import java.util.Collections;
import java.sql.Connection;

public class AdminStaff extends User {

    public AdminStaff(String id, Connection con){
        super(id, con);
    }

    public String getDepartment() { // TODO: sql
        return null;
    }

    public void updateRecentlyVeiwedCourses(Course course){ // TODO: sql
         // TODO: is this a logical constraint? (list of admin staff's recent courses is never more than 10)

    }
    public List<Course> getRecentlyViewedCourses(){ //TODO: sql
        return null;
    }

}
