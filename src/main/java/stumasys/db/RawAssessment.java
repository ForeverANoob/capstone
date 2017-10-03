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

    public String getId() {
        return id;
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
        return null;
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
        int um = getUncappedStudentMark(s);
        int mc = getMarkCap();
        return (um > mc ? mc : um);
    }

    public int getUncappedStudentMark(Student s) {
        try{
            Statement st = con.createStatement();
            String sql = "SELECT "+all[2]+" FROM courses."+all[0]+"_"+all[1]+" WHERE id = '"+s.getId()+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getInt(all[2]);
            }
        }catch(SQLException e){ System.out.println("Error: getting mark_cap " + e); }
        return -1;
    }

    public void setStudentMark(Student stu, int mark) { // TODO: sql
    }

    public void setMarkCap(int mc) { // TODO: sql
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

    public void setPublishState(boolean v){ // TODO: sql
    }

    public boolean isAvailableOnStudentHome() {       // TODO: this method used to be called "isUploaded", which we have determined was not the desired thing
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

    public void setStudentHomeAvailability(boolean v) { // TODO: sql
        //onStudentHome = v;
    }

//    public String toString(){
//        return ""+this.id+" "+this.markCap;
//    }
}
