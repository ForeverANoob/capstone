package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

@Component
public class Database {

    // TODO: stage 4: create a proper database backend that interacts with
    // an SQL server instead of these tables

    /*  variables  */
    private Connection con;
    private final String portNumber = "3306";
    private final String userName = "root";
    private final String password = "j9bct840s";
    private final String dbms = "mysql";
    private final String serverName = "localhost";
    private final String dbName = "testing";

    /*   Establishing a connection  */
    public Connection getConnection() throws SQLException{
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if(this.dbms.equals("mysql")){
            conn = DriverManager.getConnection(
                   "jdbc:" + this.dbms + "://" +
                   this.serverName +
                   ":" + this.portNumber + "/",
                   connectionProps);
                   System.out.println("jdbc:" + this.dbms + "://" +  this.serverName + ":" + this.portNumber + "/" + connectionProps);
        }
        else if (this.dbms.equals("derby")){
            conn = DriverManager.getConnection(
                   "jdbc:" + this.dbms + ":" +
                   this.dbName +
                   ";create=true",
                   connectionProps);

        }
        System.out.println();
        return conn;
    }

    /*   constructor   */
    public Database(){              // TODO: sql
        try{
            this.con = this.getConnection();
            System.out.println("#SmokeWeedEveryday #420 #ConnectionMake");
            con.setAutoCommit(false);

            //String query = "INSERT INTO testing (id, Acedemic program, fname, surname, emplid, subject, class nbr, Term, Final grade, Catalog nbr) VALUES (dude, woof, swag, mlg, 420, smoke weed, bewbs, gone, gg, 18);";

            Statement st = con.createStatement();
            //String sql = "INSERT INTO /Unnamed/mysql/testing (id, Acedemic program, fname, surname, emplid, subject, class nbr, Term, Final grade, Catalog nbr) VALUES (dude, woof, swag, mlg, 420, smoke weed, bewbs, gone, gg, 18);";
            String sql = "INSERT INTO capstone.users VALUES ('TKDF', 'jorg', 1, NULL), ('tim', 'Dude', 1, NULL), ('nty', 'TIM', 1, NULL), ('sfs', 'ALEX', 1, NULL);";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
               String id = rs.getString("id");
                System.out.println(id+" ");
            }
            st = con.createStatement();
            sql = "SELECT * FROM capstone.users WHERE id = 'hrht';";
            rs = st.executeQuery(sql);
            while(rs.next()) {
               String id = rs.getString("id");
                String str1 = rs.getString("name");
                System.out.println(id+" "+str1);
            }

            st = con.createStatement();
            sql = "CREATE TABLE assignments."+ "ass1" +" (id NVARCHAR(50), mark INT)";
            rs = st.executeQuery(sql);

        }catch(SQLException e){
            System.out.println("------------------------------------------------->  This connection is just like...no bruh  <----------------------------------------");
            System.out.println(e);
        }
    }


    /*   Checking if the info exists in the database   */       // TODO
    public boolean checkUser(String id){
        try{
            Statement st = con.createStatement();
            String sql = "SELECT id FROM users.user_info WHERE id = '"+id+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return true;
            }

        }catch(SQLException e){ System.out.println("An error has occured: "+e); return true;}
        return false;
    }
    public boolean checkCourse(String id){
        try{
            Statement st = con.createStatement();
            String sql = "SELECT id FROM capstone.courses WHERE id = "+id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return true;
            }
            return false;
        }catch(SQLException e){ System.out.println("An error has occured: "+e); return true;}
    }
    public boolean checkAssignment(String id){
        try{
            Statement st = con.createStatement();
            String sql = "IF (EXISTS (SELECT * FROM assessments WHERE TABLE_SCHEMA = 'TheSchema' AND TABLE_NAME = '"+id+"'))";                // TODO
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return true;
            }
            return false;
        }catch(SQLException e){ System.out.println("An error has occured: "+e); return true;}
    }


    /*   Adding stuff to the databases   */  // TODO
    public void addUser(String id, String name, String role) throws SQLException{    // input a user
        // Check if user already exists
        try{
            Statement st = con.createStatement();
            String sql = "";     // TODO: distinguish between users
            ResultSet rs = st.executeQuery(sql);
            System.out.println(sql);        //
        }catch(SQLException e){ System.out.println("An error has occurred "+e); }
    }
    public void addCourse(String id){
        try{
            Statement st = con.createStatement();
            String sql = "CREATE TABLE courses."+id+" ()";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println("An error has occured: "+e); }
    }
    public void addAssignment(String id){               // string or int?               // done
        try{
            Statement st = con.createStatement();
            String sql = "CREATE TABLE assignments."+ id +" (id NVARCHAR(50), mark INT)";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println("An error has occured: "+e); }
    }

    /*  these next two normally go together but don't have to  */
    public void addCourseToUser(){

    }
    public void addUserToCourse(){

    }

    public void addMarkToAssessment(){

    }


    /*   Getting info from the database   */
    public User getUser(String id, int role){                    // TODO: sql
        if(role == 1){
            AdminStaff adm = new AdminStaff(id, this.con);

        }else if (role == 2){
            Lecturer lect = new Lecturer(id, this.con);
        }else if(role == 3){
            Student stu = new Student(id, this.con);
        }
        User user = new User(id, this.con);
        return null;
    }
    public Course getCourse(String code, int year) {    // TODO: sql
        return null;
    }
    public Assessment getAssessment(String id){
        return null;
    }


    /*    Get similar objects   */
    public List<Course> getLikeCourse(String name){ // TODO: implement SQL search for courses with this name
        try{
            Statement st = con.createStatement();
            String sql = "";
            ResultSet rs = st.executeQuery(sql);

            System.out.println(rs.getString("id"));
            return null;
        }catch (SQLException e){ System.out.println("An error has occured "+e); return null;}

    }
    public List<Course> getLikeYear(String year){ // TODO: implement SQL search for courses in this year
        try{
            Statement st = con.createStatement();
            String sql = "";
            ResultSet rs = st.executeQuery(sql);

            System.out.println(rs.getString("id"));
            return null;
        }catch (SQLException e){ System.out.println("An error has occured "+e); return null;}

    }


    public boolean isAuthorised(String saltpass){       // TODO: sql

        return true;
    }
    /* The following are an interface used by the objects returned by this class
     * to fetch additional data from, and synchronise changes with, the database
     * underlying the system. For now, we're hardcoding things without having an
     * actual DB present.
     */
/*
    protected void setPassword(String userId, byte[] pwHash) { // TODO:
        User tmp = users.get(userId);
        tmp.setPassword(pwHash);
    }
*/
    protected int getRawAssessmentMark(String aId, String uId) {  // TODO: Have to fix this
        return -1;      // wrong?
    }

    protected void setRawAssessmentMark(String aId, String uId, int mark) { // going through the student vs. the course // TODO: sql

    }

    protected void setRawAssessmentMarkCap(String aId, int mc) {  // TODO: sql

    }

    protected Map<String, Assessment> getAssessments(){         // TODO: sql
        return null;                              // security leak?
    }

}
