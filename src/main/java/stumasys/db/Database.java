package stumasys.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;
import javax.annotation.PostConstruct;

import org.mariadb.jdbc.MariaDbDataSource; // the alternative to: import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

// a hard-coded dummy that is supposed to be replaced by one that uses SQL, as
// is the case with all the classes in the db package. We couldn't finish
// the SQL backend in time for the GUI to function in it's fullest state,
// so we are submitting this. You can checkout the SQL version under the branch
// sql_backend.

@Component
public class Database {

    @Autowired
    DriverManagerDataSource dataSource;

    /*  variables  */
    private Connection con;
    private final String portNumber = "3306";
    private final String userName = "root";
    private final String password = "dogzrool";
    private final String dbms = "mysql";
    private final String serverName = "localhost";
    private final String dbName = "testing";

    /*   Establishing a connection  
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if(this.dbms.equals("mysql")) {
            conn = DriverManager.getConnection(
                   "jdbc:" + this.dbms + "://" +
                   this.serverName +
                   ":" + this.portNumber + "/",
                   connectionProps);
           System.out.println("jdbc:" + this.dbms + "://" +  this.serverName + ":" + this.portNumber + "/" + connectionProps);
        } else if (this.dbms.equals("derby")){
            conn = DriverManager.getConnection(
                   "jdbc:" + this.dbms + ":" +
                   this.dbName +
                   ";create=true",
                   connectionProps);
                   System.out.println("Give up");
        }
        System.out.println();
        return conn;
    }*/


    /*   constructor   */
    public Database() { }

    @PostConstruct
    public void openConnection() {
        try {
            System.out.println("--------------->>>>>>>>>>>>>>>>>>> ");
            System.out.println(dataSource);
            this.con = dataSource.getConnection();
            System.out.println("#SmokeWeedEveryday #420 #ConnectionMake");
            con.setAutoCommit(true);

            //test.delete(con); // TODO(Danny): uncomment this line after the first run of this program !!!
            //test.create(con, this);

        } catch(SQLException e) {
            System.out.println("------------------------------------------------->  This connection is just like...no bruh  <----------------------------------------");
            System.out.println(e);
        }
    }


    /*   Checking if the info exists in the database   */
    public boolean checkUser(String id) {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT id FROM users.user_info WHERE id = '"+id+"'";

            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e){
            System.out.println("Database error: " + e);
            return false;
        }
        return false;
    }

    public boolean checkCourse(String name, int year) {
        try {
            DatabaseMetaData meta = con.getMetaData();
            String id = year + "_"+name;

            ResultSet rs = meta.getTables(null, null, id, new String[] {"TABLE"});
            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch(SQLException e) {
            System.out.println("An error has occured: "+e);
            return true;
        }
    }

    public boolean checkAssessment(int id, String code, int year) {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM assessments.assessments WHERE ass_id = " + id + " AND year = " + year + " AND course_code = '" + code + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return true;
            }
            return false;
        } catch(SQLException e) {
            System.out.println("An error has occured: " + e);
            return true;
        }
    }


    /* --------   Adding stuff to the databases  ---------  */

    public void addUser(String id, String name, String role, String degree, String department, String password) {
        // FIRST Check if user already exists before calling this method
        try {
            Statement st = con.createStatement();
            String sql = "INSERT INTO users.user_info VALUES ('"+id+"', '"+name+"', '"+role+"', '"+degree+"', '"+department+"', '"+password+"', 1);";
            ResultSet rs = st.executeQuery(sql);
        } catch(SQLException e) {
            System.out.println("An error has occurred "+e);
        }
    }

    public void addCourse(String id, String[] args){
        String arg = " (" + args[0] +" NVARCHAR(50), ";
        String[] tmp = id.split("_");
        for (int i = 1; i < args.length; i++){
            if (i == args.length - 1){
                arg += "" +args[i]+" INT)";
            }
            else{
                arg += "" +args[i]+ " INT, ";
            }
            this.addAssessment(args[i], tmp[1], Integer.parseInt(tmp[0]), 0, 0, 100, "");
        }

        try {
            Statement st = con.createStatement();
            String sql = "CREATE TABLE courses."+id+arg;
            ResultSet rs = st.executeQuery(sql);

            st = con.createStatement();
            sql = "INSERT INTO courses.courses_info VALUES ('"+tmp[1]+"', "+tmp[0]+", "+args.length+")";
            rs = st.executeQuery(sql);

        } catch(SQLException e) {
            System.out.println("An error has occured: "+e);
        }
    }

    public void addAssessment(String name, String course_id, int year, int upload, int published, int mark_cap, String cal) {
        try {
            int id = 0;
            if (!this.tableIsEmpty()) {
                Statement st = con.createStatement();
                String sql = "SELECT ass_id FROM assessments.assessments ORDER BY ass_id DESC";
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    id = rs.getInt("ass_id") + 1;
                }
            }
            Statement st = con.createStatement();
            String sql = "INSERT INTO assessments.assessments VALUES ("+id+", '"+name+"', '"+course_id+"', "+year+", "+upload+", "+published+", "+mark_cap+", '"+cal+"')";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("An error has occured: "+e);
        }
    }

    /*   this is for the unique id of the assessments  */
    public boolean tableIsEmpty() {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM assessments.assessments";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return false;
            }
        } catch(SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    /*  these next two normally go together but don't have to  */
    public void addCourseToUser(int year, String course_id, String user_id){
        try {
            String role = "";
            Statement st = con.createStatement();
            String sql = "SELECT role FROM users.user_info WHERE id = '"+user_id+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                role = rs.getString("role");
            }
            if (role.equals("student")){    // TODO: this can be reduced
                role = "student";
            }else if(role.equals("lecturer")){
                role = "lecturer";
            }else if(role.equals("admin")){
                role = "admin";
            }else { System.out.println("The role which the user has is undefined"); return; } // testing purpose

            st = con.createStatement();
            sql = "INSERT INTO users.user_courses VALUES ('"+user_id+"', '"+course_id+"', "+year+", '"+role+"')";
            rs = st.executeQuery(sql);
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    public void addUserToCourse(int year, String course_id, String user_id) {
        try {
            String values = "";
            Statement st = con.createStatement();
            String sql = "SELECT num_ass FROM courses.courses_info WHERE course_code = '"+course_id+"' AND year = "+year+"";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                for (int i = 0; i < rs.getInt("num_ass"); i++) {
                    values += ", 0"; // TODO: change this default value to a negative that makes sense. TODO: agree on a set of values that mean different thigns when the mark is negative
                }
                st = con.createStatement();
                sql = "INSERT INTO courses."+year+"_"+course_id + " VALUES ('"+user_id+"'"+values+")"; //
                rs = st.executeQuery(sql);
            } // if not, there are no assessments in this course so dont need to modify the assessment table
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addMarkToAssessment(String ass_id, String user_id, int mark) {   // assumes assessment exists
        String[] args = ass_id.split("_");
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE courses."+args[0]+"_"+args[1]+" SET "+args[2]+" = "+mark+" WHERE id = '"+user_id+"'";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /*   Getting info from the database   */
    public User getUser(String id){
        User user = new User(id, con);
        String role = user.findRole();

        if (role.equals("admin")) {
            System.out.println("------------------------- Got a new admin!");
            user = new AdminStaff(id, this.con);
        } else if (role.equals("lecturer")) {
            user = new Lecturer(id, this.con);
        } else if(role.equals("student")) {
            user = new Student(id, this.con);
        } else {
            System.out.println("User has no defined role: " + role);
        }
        return user;
    }

    public Course getCourse(String code, int year) {
        return new Course(code, year, con);
    }

    public Assessment getAssessment(int id, String code, int year) {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT calculation FROM assessments.assessments WHERE ass_id = "+id+" AND course_id = '"+code+"' AND year = "+year;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String tmp = rs.getString("calculation");
                if (tmp.equals("")) {
                    return new RawAssessment(id, code, year, con);
                } else {
                    return new CalculatedAssessment(id, code, year, con);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    /*    Get similar objects   */
    public List<Course> getLikeCourse(String name){ // TODO: implement SQL search for courses with this name
        try {
            List<Course> tmp = new ArrayList<Course>();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM courses.courses_info WHERE course_code = '"+name+"'";

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                tmp.add(new Course(rs.getString("course_code"), rs.getInt("year"), con));
            }
            return tmp;
        } catch (SQLException e) {
            System.out.println("An error has occured "+e);
            return null;
        }
    }

    public List<Course> getLikeYear(String year){ // TODO: implement SQL search for courses in this year
        try {
            List<Course> tmp = new ArrayList<Course>();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM courses.courses_info WHERE year = "+year+"";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                tmp.add(new Course(rs.getString("course_code"), rs.getInt("year"), con));
            }

            return tmp;
        } catch (SQLException e) {
            System.out.println("An error has occured "+e);
            return null;
        }
    }
}
