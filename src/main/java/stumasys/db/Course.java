package stumasys.db;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
    private String code; // these two variables uniquely identify a given Course
    private int year;
    private Connection con;

    public Course(String code, int year, Connection con){
        this.code = code;
        this.year = year;
        this.con = con;
    }

    public Map<Assessment, Boolean> getPrevVisibleColumns(AdminStaff u) { // TODO(merge): sql!
        /* The non-sql version that just says that every assessment should immediately be visible on the course page is this:

        HashMap<Assessment, Boolean> vis = new HashMap<Assessment, Boolean>();
        for(Assessment a : assessments) {
            vis.put(a, Boolean.TRUE);
        }
        return vis;

        // this is the way we remember which adminstaff has which columns chosen to be
        // displayed (with the checkboxes). you'll need to store this information
        // somewhere in the database, andre. it's different for every admin:
        // don't store it as a constant for the whole course! I'd guess a table
        // structure like this might work:
        //      admin_id | binary string ("1010101110")
        // where the string is indicating 1 -> visible, 0 -> not visible, if
        // we are using integer ID's (or just if we can establish a universal
        // ordering on the IDs that will never get invalidated)

        */
        return null;
    }

    public String getId(){
        return year+"_"+code;
    }

    public String getCode() {
        return code;
    }

    public int getYear(){
        return this.year;
    }

    public Assessment getAssessment(String name){
        try{
            Statement st = con.createStatement();
            String sql = "SELECT * FROM assessments.assessments WHERE name = " + name + " AND year = "+year +" AND  course_code = '"+code+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                if(rs.getString("calculation").equals("")){
                    return new RawAssessment(rs.getInt("ass_id"), code, year, this.con);
                }else{
                    return new CalculatedAssessment(rs.getInt("ass_id"), code, year, this.con);
                }
            }
        }catch(SQLException e){ System.out.println("Error: " + e); }

        return null;
    }

    public Assessment getAssessment(int id) {
        try{
            Statement st = con.createStatement();
            String sql = "SELECT calculation FROM assessments.assessments WHERE ass_id = " + id + " AND year = "+year +" AND  course_code = '"+code+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                if(rs.getString("calculation").equals("")){
                    return new RawAssessment(id, code, year, this.con);
                }else{
                    return new CalculatedAssessment(id, code, year, this.con);
                }
            }
        }catch(SQLException e){ System.out.println("Error: " + e); }

        return null;
    }

    public void addAssessment(Assessment a){    // TODO: sql
    }

    public void setAssessments(List<Assessment> all){ // TODO: sql DOES NOT WORK
        try{
            for (int i = 0; i < all.size(); i++){
                Statement st = con.createStatement();
                String sql = "INSERT INTO courses."+year+"_"+code+" VALUES ()";
                ResultSet rs = st.executeQuery(sql);
            }
        }catch(SQLException e){ System.out.println(e); }
    }

    public List<Student> getTeachingAssistants() { //
        List<Student> lst = new ArrayList<Student>();
        try{
            Statement st = con.createStatement();
            String sql = "SELECT user_id FROM users.user_courses WHERE course_id = '"+this.code+"' AND year = "+year+"";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lst.add(new Student(rs.getString("user_id"), this.con));
            }
        }catch(SQLException e){ System.out.println(e);}
        return lst;
    }

    public void addTeachingAssistant(Student ta) { //
        try{
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_courses SET role = 'ta' WHERE user_id = '"+ta.getId()+"' AND year = "+year+" AND course_id = '"+code+"'";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e);}
    }

    public void removeTeachingAssistant(Student ta) { // TODO: sql
    }

    public List<Assessment> getAssessments(){   //
        List<Assessment> lst = new ArrayList<Assessment>();
        try{
            Statement st = con.createStatement();
            String sql = "SELECT * FROM assessments.assessments";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getString("calculation").equals("")){
                    lst.add(new RawAssessment(rs.getInt("ass_id"), code, year, this.con));
                }else{
                    lst.add(new CalculatedAssessment(rs.getInt("ass_id"), code, year,this.con));
                }
            }
        }catch(SQLException e){ System.out.println(e); }
        return lst;
    }

    public boolean isRegistered(Student stu){ // TODO:    sql
        try{
            Statement st = con.createStatement();
            String sql = "SELECT registered FROM users.user_courses WHERE user_id = '" + stu.getId() + "' AND course_id '"+this.getId()+"'";  // remember
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                if(rs.getString("registered").equals("registered")){
                    return true;
                }
            }
        }catch(SQLException e){ System.out.println("Error: is registered " + e); }
        return false;
    }

    public void deregisterStudent(Student stu){ // TODO: sql
        try{
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_courses SET role = 'deregister' WHERE user_id = '"+stu.getId()+"' AND course_id = '"+this.getId()+"'"; // remember
            ResultSet rs = st.executeQuery(sql);

        }catch(SQLException e){ System.out.println("Error: is registered " + e); }
    }

    public void setParticipants(List<User> users){  // TODO: is this all i need to do
        try{
            String values = "";

            Statement st = con.createStatement();
            String sql = "SELECT num_ass FROM courses.courses_info WHERE course_code = '"+code+"' AND year = "+year+"";
            ResultSet rs = st.executeQuery(sql);
            System.out.println(users.size() + "first");
            if(rs.next()){}
            for (int i = 1; i < rs.getInt("num_ass"); i++){
                values += ", 0";                // TODO: chack if right default
            }
            for (int i = 0; i < users.size(); i++){
                st = con.createStatement();
                sql = "INSERT INTO courses."+year+"_"+code+" VALUES ('"+users.get(i).getId()+"'"+values+")";
                rs = st.executeQuery(sql);

            }
        }catch(SQLException e){ System.out.println(e); }
    }

    public Lecturer getCourseCoordinator(){
        try{
            Statement st = con.createStatement();
            String sql = "SELECT user_id FROM users.user_courses WHERE role = 'coord' AND course_id = '"+this.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                return new Lecturer (rs.getString("user_id"), this.con);
            }
            // no coordinator
        }catch(SQLException e){ System.out.println("Error: is registered " + e); }
        return null;
    }

    public void batchUpdateRegistrationStatus(Map<String,Boolean> regStatus) {
        return;
    }

    public void setCourseCoordinator(Lecturer c){
        try{
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_courses SET role = 'coord' WHERE user_id = '"+c.getId()+"' AND course_id = '"+this.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }

    public List<Student> getTAs(){
        List<Student> lst = new ArrayList<Student>();
        try{

            Statement st = con.createStatement();
            String sql = "SELECT user_id FROM users.user_courses WHERE role = 'ta' AND course_id = '"+this.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lst.add(new Student(rs.getString("user_id"), this.con));
            }
        }catch(SQLException e){ System.out.println(e); }
        return lst;
    }

    public void addTA(Student ta){
        try{
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_courses SET role = 'ta' WHERE user_id = '"+ta.getId()+"' AND course_id = '"+this.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }

    //public void batchUpdateRegistrationStatus(Map<String,Boolean> regStatus) {  // TODO

    //}
}
