package stumasys.db;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lecturer extends User {

    public Lecturer(String id, Connection con) {
        super(id, con);
    }

    public String getDepartment() {
        String department = "";
        try {
            Statement st = con.createStatement();
            String sql = "SELECT department FROM users.user_info WHERE id = '" + this.id + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                department = rs.getString("department");
            } else {
                department = "";
            }
        } catch(SQLException e) {
            System.out.println("Could not get admin staff department " + e);
        }
        return department;
    }

    public void setDepartment(String d) {
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_info SET department = '"+d+"' WHERE id = '"+this.id+"'";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addCourse(Course c) {
        try {
            Statement st = con.createStatement();
            String sql = "INSERT INTO users.user_courses VALUES ('"+this.id+"', '"+c.getCode()+"', "+c.getYear()+", 'lecturer')";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void removeCourse(Course c) { // TODO: sql
    }

    public List<Course> getCourses() {
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
    //    return super.toString() + " lecturer";
    //}
}
