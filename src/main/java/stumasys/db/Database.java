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
import java.sql.DatabaseMetaData;

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
            sql = "CREATE TABLE assignments."+ "ass1" +" (id NVARCHAR(50), mark INT)";
            //rs = st.executeQuery(sql);

            System.out.println(this.checkUser("id1"));
            System.out.println(this.checkUser("id1"));
            this.addUser("id1", "brt", "admin", "vra", "com sci");
            System.out.println(this.checkUser("id1"));
            System.out.println(this.getUser("id1"));

            System.out.println(checkAssignment("pie"));
            addAssignment("pie");
            System.out.println(checkAssignment("pie"));

            addMarkToAssessment("pie", "id1", 69);


        }catch(SQLException e){
            System.out.println("------------------------------------------------->  This connection is just like...no bruh  <----------------------------------------");
            System.out.println(e);
        }
    }


    /*   Checking if the info exists in the database   */
    public boolean checkUser(String id){    // works
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
    public boolean checkCourse(String name, int year){
        try{
            Statement st = con.createStatement();
            String sql = "SELECT id FROM courses.course_ass WHERE name = '"+name+"' AND year = "+year;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return true;
            }
            return false;
        }catch(SQLException e){ System.out.println("An error has occured: "+e); return true;}
    }
    public boolean checkAssignment(String id){
        try{
            DatabaseMetaData meta = con.getMetaData();

            ResultSet rs = meta.getTables(null, null, id, new String[] {"TABLE"});
            if (rs.next()){
                return true;
            }
            return false;
        }catch(SQLException e){ System.out.println("An error has occured: "+e); return true;}
    }


    /*   Adding stuff to the databases   */  // TODO
    public void addUser(String id, String name, String role, String degree, String department) throws SQLException{    // works
        // Check if user already exists
        try{
            Statement st = con.createStatement();
            String sql = "INSERT INTO users.user_info VALUES ('"+id+"', '"+name+"', '"+role+"', '"+degree+"', '"+department+"')";     // TODO: distinguish between users
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
            String sql = "CREATE TABLE assignments."+ id +" (id NVARCHAR(50), mark INT)";   // TODO: test
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println("An error has occured: "+e); }
    }

    /*  these next two normally go together but don't have to  */
    public void addCourseToUser(int year, String course_id, String user_id){
        try{
            Statement st = con.createStatement();
            String sql = "INSERT INTO users.user_courses VALUES ('"+user_id+"', '"+course_id+"', "+year+")";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }
    public void addUserToCourse(int year, String course_id, String user_id){
        try{
            Statement st = con.createStatement();
            String sql = "";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }

    public void addMarkToAssessment(String ass_id, String user_id, int mark){   // assumes assignment exists
        try{
            Statement st = con.createStatement();
            String sql = "INSERT INTO assignments."+ ass_id + " VALUES ('"+user_id+"', '"+mark+"')";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }


    /*   Getting info from the database   */
    public User getUser(String id){                    // TODO: sql
        try{

            User user = new User(id, con);
            String role = user.findRole();

            if(role.equals("admin")){
                user = new AdminStaff(id, this.con);

            }else if (role.equals("lecturer")){
                user = new Lecturer(id, this.con);

            }else if(role.equals("student")){
                user = new Student(id, this.con);
                Statement st = con.createStatement();
                String sql = "SELECT * FROM users.user_info WHERE id = '" + id + "'";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()){
                    System.out.println(rs.getString("id") + " " + rs.getString("name"));
                }

            }else{ System.out.println("User has no defined role: " + role); }
            return user;
        }catch(SQLException e){ System.out.println(e); return null; }
    }
    public Course getCourse(String code, int year) {    // TODO: sql
        try{
            Statement st = con.createStatement();
            String sql = "";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
        return null;
    }
    public Assessment getAssessment(String id){
        try{
            Statement st = con.createStatement();
            String sql = "SELECT "+" FROM courses.";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
        return null;
    }

    public String getStudentAssMark(String ass_id, String stu_id){
        try{
            Statement st = con.createStatement();
            String sql = "SELECT * FROM assignments." + ass_id + "WHERE id ='" + stu_id + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getString("id") + " " + rs.getInt("mark");    // TODO: standardize
            }
            return "No data";
        }catch(SQLException e){ System.out.println(e); return "";}
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
