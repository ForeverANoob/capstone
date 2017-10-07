package stumasys.db;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdminStaff extends User {
    public AdminStaff(String id, Connection con){
        super(id, con);

    }

    public String getDepartment() {
        String department = null;
        try{
            Statement st = con.createStatement();
            String sql = "SELECT department FROM users.user_info WHERE id = '" + this.id + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                department = rs.getString("department");
            }
        }catch(SQLException e){ System.out.println("Could not get admin staff department " + e); }
        return department;
    }

    public void updateRecentlyVeiwedCourses(Course course) { // TODO: sql
         // TODO: impose limit on number of "recently viewed" in list?
         try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM users.user_courses WHERE user_id = '"+id+"' AND course_id = '"+course.getCode()+"' AND year = "+course.getYear()+"";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return;     // the course already exists
            }
            st = con.createStatement();
            sql = "INSERT INTO users.user_courses VALUES ('"+id+"', '"+course.getCode()+"', "+course.getYear()+", 'admin')";
            rs = st.executeQuery(sql);

         } catch (SQLException e) {
             System.out.println(e);
         }

    }
    public List<Course> getRecentlyViewedCourses() {
        try {
            List<Course> courses = new ArrayList<Course>();

            Statement st = con.createStatement();
            String sql = "SELECT * FROM users.user_courses WHERE user_id = '" + this.id + "'";
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                Course c = new Course(rs.getString("course_id"), rs.getInt("year"), this.con);
                courses.add(c);
            }

            return courses;
        } catch (SQLException e) {
            System.out.println("Error in getting involved courses " + e);
            return null;
        }    // not sure about this
    }

    //public String toString(){
    //    return super.toString() + " admin staff";
    //}
}
