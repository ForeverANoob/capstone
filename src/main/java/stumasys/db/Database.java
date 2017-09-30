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

    private Connection con;
    private final String portNumber = "3306";
    private final String userName = "root";
    private final String password = "j9bct840s";
    private final String dbms = "mysql";
    private final String serverName = "localhost";
    private final String dbName = "testing";

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

    public Database(){              // TODO: sql
        try{
            this.con = this.getConnection();
            System.out.println("#SmokeWeedEveryday #420 #ConnectionMake");
            con.setAutoCommit(false);

            //String query = "INSERT INTO testing (id, Acedemic program, fname, surname, emplid, subject, class nbr, Term, Final grade, Catalog nbr) VALUES (dude, woof, swag, mlg, 420, smoke weed, bewbs, gone, gg, 18);";
            //con.prepareStatement(query);
            //con.commit();

            //System.out.println(con.getSchema());
            //System.out.println("hi");
            Statement st = con.createStatement();
            //String sql = "INSERT INTO /Unnamed/mysql/testing (id, Acedemic program, fname, surname, emplid, subject, class nbr, Term, Final grade, Catalog nbr) VALUES (dude, woof, swag, mlg, 420, smoke weed, bewbs, gone, gg, 18);";
            String sql = "INSERT INTO capstone.users VALUES ('TKDF', 'JOHN'), ('IUF2', 'ANDRE'), ('VRE', 'TIM'), ('RTFD1', 'ALEX')";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
               String id = rs.getString("id");
                //String str1 = rs.getString("second_column_name");
                System.out.println(id+" ");
            }
            st = con.createStatement();
            //String sql = "INSERT INTO /Unnamed/mysql/testing (id, Acedemic program, fname, surname, emplid, subject, class nbr, Term, Final grade, Catalog nbr) VALUES (dude, woof, swag, mlg, 420, smoke weed, bewbs, gone, gg, 18);";
            sql = "SELECT id FROM capstone.users";
            rs = st.executeQuery(sql);
            while(rs.next()) {
               String id = rs.getString("id");
                //String str1 = rs.getString("second_column_name");
                System.out.println(id+" ");
            }
            con.close();

        }catch(SQLException e){
            System.out.println("------------------------------------------------->  This connection is just like...no bruh  <----------------------------------------");
            System.out.println(e);
        }

    }
    public List<Course> getLikeCourse(String name){
        return null;            // TODO: implement SQL search query for courses with similar name
    }
    public List<Course> getLikeYear(String year){
        return null;            // TODO: implement SQL search for courses in this year
    }

    public Course getCourse(String code, int year) {    // TODO: sql
        return null;
    }

    public User getUser(String id) {                    // TODO: sql
        return null;
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
