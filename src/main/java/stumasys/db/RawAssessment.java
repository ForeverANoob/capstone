package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class RawAssessment implements Assessment {
    private final String id;          // no id yet
    private Connection con;
    private String[] all;
    //private HashMap<String, >

    public RawAssessment(String id, Connection con) {
        this.id = id;
        this.con = con;
        this.all = id.split("_");
    }

    public String getId(){
        return this.id;
    }
    public String getName(){    // TODO: check again
        try{
            Statement st = con.createStatement();
            String sql = "SELECT ass_id FROM assessments.assessments WHERE ass_id = '"+this.id+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                String tmp = rs.getString("ass_id");
                String[] args = tmp.split("_");
                return args[2];
            }
        }catch(SQLException e){ System.out.println("Error: getting name " + e); }
        return "";
    }

    public int getMarkCap() {
        try{
            Statement st = con.createStatement();
            String sql = "SELECT mark_cap FROM assessments.assessments WHERE ass_id = '"+this.id+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getInt("mark_cap");
            }
        }catch(SQLException e){ System.out.println("Error: getting mark_cap " + e); }
        return -1;

    }
    public int getStudentMark(Student s){
        return-1;
    }

    public int getStudentMark(String stu_id) {  // capped student mark?
        try{
            Statement st = con.createStatement();
            String sql = "SELECT "+all[2]+" FROM courses."+all[0]+"_"+all[1]+" WHERE id = '"+stu_id+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getInt(all[2]);
            }
        }catch(SQLException e){ System.out.println("Error: getting mark_cap " + e); }
        return -1;
    }

    public int getUncappedStudentMark(Student s) {
        return -1;  // returns student's mark
    }

    public boolean setStudentMark(String stuId, int mark) {
        return true;
    }

    public void setMarkCap(int mc) {
    }

    public Map<String, Integer> getWholeTable() { // TODO: actually implement this
        return null;
    }
    public boolean isPublished(){   // TODO: sql
        try{
            Statement st = con.createStatement();
            String sql = "SELECT published FROM assessments.assessments WHERE ass_id = '"+id+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return 1 == rs.getInt("published");
            }
        }catch(SQLException e){ System.out.println("Error: getting mark_cap " + e); }
        return false;
    }
    public void publishMarks(){     // TODO: sql?
        //this.published = true;
    }

    public boolean isUploaded() {       // TODO: sql
        try{
            Statement st = con.createStatement();
            String sql = "SELECT uploaded FROM assessments.assessments WHERE ass_id = '"+id+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return 1 == rs.getInt("uploaded");
            }
        }catch(SQLException e){ System.out.println("Error: getting mark_cap " + e); }
        return false;
    }

    public void setStudentHomeAvailability(boolean v) {
        //onStudentHome = v;
    }

//    public String toString(){
//        return ""+this.id+" "+this.markCap;
//    }
}
