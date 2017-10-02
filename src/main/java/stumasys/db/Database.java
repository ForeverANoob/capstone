package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
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
            sql = "CREATE TABLE assessments."+ "ass1" +" (id NVARCHAR(50), mark INT)";
            //rs = st.executeQuery(sql);

            System.out.println(this.checkUser("id1"));
            System.out.println(this.checkUser("id1"));
            this.addUser("id1", "brt", "admin", "vra", "com sci");
            System.out.println(this.checkUser("id1"));
            System.out.println(this.getUser("id1"));

            System.out.println(checkAssessment("2017_csc1015_pie"));
            addAssessment("2017_csc1015_pie", 2, 2, 2, "a + b");
            System.out.println(checkAssessment("2017_csc1015_pie"));

            System.out.println(checkCourse("csc1015", 2017));
            String n = "id vqvr vqe rtv pie";
            addCourse("2017_csc1015", n.split(" "));
            System.out.println(checkCourse("csc1015", 2017));

            addMarkToAssessment("2017_csc1015_pie", "id1", 69);


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
            DatabaseMetaData meta = con.getMetaData();
            String id = year + "_"+name;
            ResultSet rs = meta.getTables(null, null, id, new String[] {"TABLE"});
            if (rs.next()){
                return true;
            }
            return false;
        }catch(SQLException e){ System.out.println("An error has occured: "+e); return true;}
    }
    public boolean checkAssessment(String id){
        try{
            /*
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, id, new String[] {"TABLE"});
            if (rs.next()){
                return true;
            }
            return false;
            */
            Statement st = con.createStatement();
            String sql = "SELECT * FROM assessments.assessments WHERE ass_id = '" + id + "'";
            ResultSet rs = st.executeQuery(sql);
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
    public void addCourse(String id, String[] args){
        String arg = " (" + args[0] +" NVARCHAR(50), ";                                    // remember this
        for (int i = 1; i < args.length; i++){
            if (i == args.length - 1){
                arg += "" +args[i]+" INT)";
            }
            else{
                arg += "" +args[i]+ " INT, ";
            }
        }
        try{
            Statement st = con.createStatement();
            String sql = "CREATE TABLE courses."+id+arg;
            ResultSet rs = st.executeQuery(sql);

            String[] tmp = args[0].split("_");
            st = con.createStatement();
            sql = "INSERT INTO courses.courses_info VALUES ('"+tmp[0]+"', "+tmp[1]+")";
            rs = st.executeQuery(sql);

        }catch(SQLException e){ System.out.println("An error has occured: "+e); }
    }
    public void addAssessment(String id, int upload, int published, int mark_cap, String cal){               // string or int?               // done
        try{
            Statement st = con.createStatement();
            String sql = "INSERT INTO assessments.assessments VALUES ('"+id+"', "+upload+", "+published+", "+mark_cap+", '"+cal+"')";   // TODO: test
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
            String sql = "INSERT INTO courses."+year+"_"+course_id + " VALUES ('"+user_id+"')"; // TODO: the other values
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }

    public void addMarkToAssessment(String ass_id, String user_id, int mark){   // assumes assessment exists
        String[] args = ass_id.split("_");
        try{
            Statement st = con.createStatement();
            String sql = "UPDATE courses."+args[0]+"_"+args[1]+" SET "+args[2]+" = "+mark+" WHERE id = '"+user_id+"'";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }


    /*   Getting info from the database   */
    public User getUser(String id){

        User user = new User(id, con);
        String role = user.findRole();

        if(role.equals("admin")){
            user = new AdminStaff(id, this.con);

        }else if (role.equals("lecturer")){
            user = new Lecturer(id, this.con);

        }else if(role.equals("student")){
            user = new Student(id, this.con);

        }else{ System.out.println("User has no defined role: " + role); }
        return user;

    }
    public Course getCourse(String code, int year) {    // TODO: check if correct
        return new Course(code, year, con);
    }
    public Assessment getAssessment(String id){     // TODO: sql, assumes Assessment exists and check if correct
        try{
            Statement st = con.createStatement();
            String sql = "SELECT calculation FROM assessments.assessments WHERE ass_id = '"+id+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                String tmp = rs.getString("calculation");
                if (tmp.equals("")){
                    return new RawAssessment(id, con);
                }else{
                    return new CalculatedAssessment(id, con);
                }
            }
        }catch(SQLException e){ System.out.println(e); }
        return null;
    }

    public String getStudentAssMark(String ass_id, String stu_id){  // TODO
        try{
            Statement st = con.createStatement();
            String sql = "SELECT * FROM assessments." + ass_id + "WHERE id ='" + stu_id + "'";
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
            List<Course> tmp = new ArrayList<Course>();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM courses.courses_info WHERE course_code = '"+name+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                tmp.add(new Course(rs.getString("course_code"), rs.getInt("year"), con));
            }

            return tmp;
        }catch (SQLException e){ System.out.println("An error has occured "+e); return null;}

    }
    public List<Course> getLikeYear(String year){ // TODO: implement SQL search for courses in this year
        try{
            List<Course> tmp = new ArrayList<Course>();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM courses.courses_info WHERE year = "+year+"";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                tmp.add(new Course(rs.getString("course_code"), rs.getInt("year"), con));
            }

            return tmp;
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
