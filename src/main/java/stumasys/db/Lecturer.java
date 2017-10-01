package stumasys.db;

import java.util.List;
import java.util.Collections;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lecturer extends User {

    private String department = "";

    public Lecturer(String id, Connection con){
        super(id, con);
        getDepartment();
        getCourses();
    }

    public String getDepartment() {         // TODO: sql
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
    public void setDepartment(String d){    // TODO: sql

    }

    public boolean addCourse(Course c) {    // TODO: sql
        return true;
    }

    public boolean removeCourse(Course c) { // TODO: sql
        return true;
    }

    public List<Course> getCourses(){       // TODO: sql
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
        return super.toString() + " lecturer";
    }
}
