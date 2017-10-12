package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class RawAssessment implements Assessment {      //
    private final int id;
    private String cc;
    private int year;
    private Connection con;
    //private HashMap<String, >

    public RawAssessment(int id, String cc, int year, Connection con) {
        this.cc = cc;
        this.year = year;
        this.con = con;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT name FROM assessments.assessments WHERE ass_id = "+this.id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {

                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error: getting name " + e);
        }
        return null;
    }

    public int getMarkCap() {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT mark_cap FROM assessments.assessments WHERE ass_id = "+this.id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getInt("mark_cap");
            }
        } catch (SQLException e) {
            System.out.println("Error: getting mark_cap " + e);
        }
        return -1;
    }

    public int getStudentMark(Student s){
        int um = getUncappedStudentMark(s);
        int mc = getMarkCap();
        return (um > mc ? mc : um);
    }

    public int getUncappedStudentMark(Student s) {
        String str = "a"+id;
        try {
            Statement st = con.createStatement();
            String sql = "SELECT "+str+" FROM courses."+year+"_"+cc+" WHERE id = '"+s.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getInt(this.getName());             // TODO: name or id
            }
        } catch (SQLException e) {
            System.out.println("Error: getting uncapped student mark " + e);
        }
        return -1;
    }

    public void setStudentMark(Student stu, int mark) {
        try {
            System.out.println(mark);
            Statement st = con.createStatement();
            String sql = "UPDATE courses."+year+"_"+cc+" SET "+getName()+" = "+mark+" WHERE id = '"+stu.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void setMarkCap(int mc) {
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE assessments.assessments SET mark_cap = "+mc+" WHERE ass_id = "+id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Map<String, Integer> getWholeTable() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM courses."+year+"_"+cc+"";     // TODO: make more efficient
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                map.put(rs.getString("id"), rs.getInt(this.getName()));   // TODO: name or id
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return map;
    }

    public boolean isPublished() {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT published FROM assessments.assessments WHERE ass_id = "+id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return 1 == rs.getInt("published");
            }
        } catch (SQLException e) {
            System.out.println("Error: getting mark_cap " + e);
        }
        return false;
    }

    public boolean isAvailableOnStudentHome() { // : SQL
        try{
            Statement st = con.createStatement();
            String sql = "SELECT stu_home_availability FROM assessments.assessments WHERE ass_id = "+this.id +" AND year = "+this.year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("stu_home_availability") == 1;
            }
        }catch(SQLException e){ System.out.println(e); }
        return false;
    }

    public void setStudentHomeAvailability(boolean available) { // : SQL
        try{
            Statement st = con.createStatement();
            String sql = "UPDATE assessments.assessments SET stu_home_availability = "+available+" WHERE ass_id = "+this.id +" AND year = "+this.year+" AND course_code = '"+cc+"'";
            st.executeQuery(sql);
        }catch(SQLException e){ System.out.println(e); }
    }

    public void setPublishState(boolean v) {
        int t = (v ? 1 : 0);
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE assessments.assessments SET published = "+t+" WHERE ass_id = "+id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean isUploaded() { // TODO: this method used to be called "isUploaded", which we have determined was not the desired thing
        try {
            Statement st = con.createStatement();
            String sql = "SELECT uploaded FROM assessments.assessments WHERE ass_id = "+id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return 1 == rs.getInt("uploaded");
            }
        } catch (SQLException e) {
            System.out.println("Error: getting mark_cap " + e);
        }
        return false;
    }

    public void setUpload(boolean v) { // TODO: see above. This might be unneeded.
        int t = (v ? 1 : 0);
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE assessments.assessments SET published = "+t+" WHERE ass_id = "+id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs  =st.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void setCalculation(String a) {
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE assessments.assessments SET calculation = '"+a+"' WHERE ass_id = "+id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs  =st.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public String getCalculation() {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT calculation FROM assessments.assessments WHERE ass_id = "+id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                return rs.getString("calculation");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

//    public String toString(){
//        return ""+this.id+" "+this.markCap;
//    }
}
