package stumasys.db;

import java.util.List;
import java.util.Collections;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminStaff extends User {

    private String department = "";

    public AdminStaff(String id, Connection con){
        super(id, con);
        getDepartment();
        getRecentlyViewedCourses();
    }

    public String getDepartment() { // TODO: sql
        try{
            Statement st = con.createStatement();
            String sql = "SELECT department FROM users.user_info WHERE id = '" + this.id + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                this.department = rs.getString("department");
            }else{
                this.department = "";
            }
        }catch(SQLException e){ System.out.println("Could not get admin staff department " + e); }
        return department;
    }

    public void updateRecentlyVeiwedCourses(Course course){ // TODO: sql
         // TODO: is this a logical constraint? (list of admin staff's recent courses is never more than 10)

    }
    public List<Course> getRecentlyViewedCourses(){ //TODO: sql
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

    public String toString(){
        return super.toString() + " admin staff";
    }
}
