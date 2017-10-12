package stumasys.db;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
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
    public Map<Assessment, Boolean> getPrevVisibleColumns(AdminStaff u) { // TODO: sql; possibly move this to individual people and have limit as to how many of these things it will remember (so we dont store for eternity the visibile column preference)
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

        HashMap<Assessment, Boolean> vis = new HashMap<Assessment, Boolean>();
        for(Assessment a : getAssessments()) {
            vis.put(a, Boolean.TRUE);
        }
        return vis;
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

    public Assessment getAssessment(int id) {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT calculation FROM assessments.assessments WHERE ass_id = " + id + " AND year = "+year +" AND  course_code = '"+code+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                if (rs.getString("calculation").equals("")) {
                    return new RawAssessment(id, code, year, this.con);
                } else {
                    return new CalculatedAssessment(id, code, year, this.con);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public int createNewRawAssessment(String name, int markCap, Map<String,Integer> markTable) { // TODO: this entire thing
        int id = -1;
        try {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT num_ass FROM courses.courses_info "
                  + "WHERE course_code = '" + code + "' AND year = " + year
                );

            if (rs.next()){
                id = rs.getInt("num_ass"); System.out.println("ghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            }
            con.createStatement().executeQuery(
                    "UPDATE courses.courses_info SET num_ass = " + (id+1)   // this +1 might be why the numbers start at 3
                  + " WHERE course_code = '" + code + "'"
                  + " AND year = " + year + ";"
                );

            con.createStatement().executeQuery(
                      "ALTER TABLE courses." + year + "_" + code+ " "
                    + "ADD COLUMN a" + id + " INT;"
                );

            String sql = "INSERT INTO assessments.assessments VALUES ("+id+", '', '"+ code+"', "+year+", 0, 0, "+markCap+", '', 0)";
            rs = con.createStatement().executeQuery(sql);

            Iterator<Map.Entry<String,Integer>> entryItr = markTable.entrySet().iterator();
            Map.Entry<String,Integer> entry = null;
            Statement st = con.createStatement();

            while (entryItr.hasNext()){ // #shouldWork
                entry = entryItr.next();
                st.addBatch("UPDATE courses."+year+"_"+code+" SET a"+id+" = " +entry.getValue()+" WHERE id = '"+entry.getKey()+"';");
            }
            st.executeBatch();  // return nothing?
            con.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("An error has occured: "+e);
            e.printStackTrace();
        } finally {
        }

        return id;
    }

    public int createNewCalculatedAssessment() { // TODO: figure this entire thing out

        return -1;
    }

    public List<Student> getTeachingAssistants() {
        List<Student> lst = new ArrayList<Student>();
        try {
            Statement st = con.createStatement();
            String sql = "SELECT user_id FROM users.user_courses WHERE course_id = '"+this.code+"' AND year = "+year+"";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lst.add(new Student(rs.getString("user_id"), this.con));
            }
            return lst;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void addTeachingAssistant(Student ta) {
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_courses SET role = 'ta' WHERE user_id = '"+ta.getId()+"' AND year = "+year+" AND course_id = '"+code+"'";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void removeTeachingAssistant(Student ta) { // TODO: show be fine
        try{
            Statement st = con.createStatement();
            String sql = "DELETE FROM users.user_courses WHERE user_id = '"+ta.getId()+"' AND course_id = '"+this.code+"' AND year "+this.year+"";
            ResultSet rs = st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }

    public List<Assessment> getAssessments() {
        List<Assessment> lst = new ArrayList<Assessment>();
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM assessments.assessments";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                if(rs.getString("calculation").equals("")) {
                    lst.add(new RawAssessment(rs.getInt("ass_id"), code, year, this.con));
                } else {
                    lst.add(new CalculatedAssessment(rs.getInt("ass_id"), code, year,this.con));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return lst;
    }

    public boolean isRegistered(Student stu) {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT registered FROM users.user_courses WHERE user_id = '" + stu.getId() + "' AND course_id '"+this.getId()+"'";  // remember
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("registered").equals("registered")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: is registered " + e);
        }
        return false;
    }

    public void deregisterStudent(Student stu) {
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_courses SET role = 'deregistered' WHERE user_id = '"+stu.getId()+"' AND course_id = '"+this.getId()+"' AND year = "+this.year+""; // remember
            ResultSet rs = st.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println("Error: is registered " + e);
        }
    }


    public Lecturer getCourseCoordinator() {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT user_id FROM users.user_courses WHERE role = 'coord' AND course_id = '"+this.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                return new Lecturer (rs.getString("user_id"), this.con);
            }
            // no coordinator
        } catch (SQLException e) {
            System.out.println("Error: is registered " + e);
        }
        return null;
    }

    public void batchUpdateRegistrationStatus(Map<String,String> regStatus) {
        try {
            con.setAutoCommit(false);
            PreparedStatement upd_user_courses = con.prepareStatement(
                      "INSERT INTO users.user_courses VALUES (?, ?, ?, 'student') "
                    + "ON DUPLICATE KEY UPDATE role = role;"
                );
            PreparedStatement upd_course_status = con.prepareStatement(
                      "INSERT INTO courses." + year+"_"+code + "(id, status) VALUES (?, ?)"
                    + "ON DUPLICATE KEY UPDATE status = ?;"
                );

            Iterator<Map.Entry<String, String>> it = regStatus.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                upd_user_courses.setString(1, e.getKey());
                upd_user_courses.setString(2, this.code);
                upd_user_courses.setInt(3, this.year);
                upd_user_courses.addBatch();

                upd_course_status.setString(1, e.getKey());
                upd_course_status.setString(2, e.getValue());
                upd_course_status.setString(3, e.getValue());
                upd_course_status.addBatch();
            }
            upd_user_courses.executeBatch();
            upd_course_status.executeBatch();

            con.commit();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("");
            System.out.println("");
            System.out.println("------------------------------>>>" + e);
            System.out.println("");
            System.out.println("");
        }
        /*
        try {
            Statement st = con.createStatement();
            Iterator<Map.Entry<String, Boolean>> it = regStatus.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, Boolean> e = it.next();
                if (e.getValue()) {
                    st.addBatch("UPDATE users.user_courses SET role = 'registered' WHERE user_id = '"+e.getKey()+"' AND course_id = '"+this.code+"' AND year = "+this.year+"");
                } else {
                    st.addBatch("UPDATE users.user_courses SET role = 'deregistered' WHERE user_id = '"+e.getKey()+"' AND course_id = '"+this.code+"' AND year = "+this.year+"");
                }
            }
            int[] rs = st.executeBatch();

        } catch (SQLException e) {
            System.out.println(e);
        }
        */
    }

    public void setCourseCoordinator(Lecturer c) {
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_courses SET role = 'coord' WHERE user_id = '"+c.getId()+"' AND course_id = '"+this.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Student> getTAs() {
        List<Student> lst = new ArrayList<Student>();
        try {
            Statement st = con.createStatement();
            String sql = "SELECT user_id FROM users.user_courses WHERE role = 'ta' AND course_id = '"+this.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lst.add(new Student(rs.getString("user_id"), this.con));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return lst;
    }

    public void addTA(Student ta) {
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE users.user_courses SET role = 'ta' WHERE user_id = '"+ta.getId()+"' AND course_id = '"+this.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
