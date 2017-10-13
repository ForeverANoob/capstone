package stumasys.db;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class CalculatedAssessment implements Assessment {
    // TODO: xtend this class to allow arbitrary calculations
    // including min/max, branching, etc, since weighted averages are not the
    // only computations commonly done.

    // TODO: write something along the lines of a "rational number"
    // class for weighting in these calculations, rather than using int's.
    // (Normal float/double aren't appropriate for various reasons.)

    private final int id;
    private String cc;
    private int year;
    private Connection con;

    public static int calculateAppropriateMarkCap(List<Assessment> src, List<Integer> weight) {
        Iterator<Integer> wIter = weight.iterator();
        int mc = 0;
        for (Assessment a : src) {
            mc += a.getMarkCap()*wIter.next();
        }
        return mc;
    }

    public CalculatedAssessment(int id, String cc, int year, Connection con){
        this.cc = cc;
        this.year = year;
        this.con = con;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName(){ // TODO: sql
        try{
            Statement st = con.createStatement();
            String sql = "SELECT name FROM assessments.assessments WHERE ass_id = "+this.id+" AND year = "+year+" AND course_code = '"+cc+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error: getting name " + e);
        }
        return null;
    }

    public void setName(String n) {
        try {
            Statement st = con.createStatement();
            String sql = "UPDATE assessments.assessments SET name = '"+n+"' WHERE ass_id = '"+id+"' AND course_code = '"+cc+"' AND year = "+year+"";
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

    public int getMarkCap() {
        try {
            Statement st = con.createStatement();
            String sql = "SELECT mark_cap FROM assessments.assessments WHERE ass_id = "+this.id+" WHERE course_code = '"+this.cc+"' AND year = "+this.year+"";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("mark_cap");
            }
        } catch (SQLException e) {
            System.out.println("Error: getting mark_cap " + e);
        }
        return -1;
    }

    //public void setStudentMark(Student s, int mark){} // This doesn't apply here: calculation determines mark, can only directly modify RawAssessments'

    public int getStudentMark(Student stu) {
        int um = getUncappedStudentMark(stu);
        int mc = getMarkCap();
        return (um > mc ? mc : um);
    }

    public int getUncappedStudentMark(Student stu) { // TODO: convert this old hardcoded stuff into SQL
        //             HOW TO DO THE CONVERSION:
        // these two lists should have the weights and the source assessments
        // (source assessments are those used to calculcate the result from,
        // e.g. section A/B are source assessments for test1), respectively.
        List<Integer> weight = null;
        List<Assessment> src = null;

        // TODO: make this NOT NULL *_only_* if we are implementing a more advanced CalculatedAssessment !!!
        List<Boolean> useUncapped = null;

        // then the rest of this code will work just fine
        int mark = 0;
        Iterator<Integer> wIter = weight.iterator();
        if (useUncapped == null) {
            for (Assessment a : src) {
                mark += wIter.next() * a.getStudentMark(stu);
            }
        } else {
            Iterator<Boolean> uncapIter = useUncapped.iterator();
            for (Assessment a : src) {
                int m;
                if (uncapIter.next()) {
                    m = a.getUncappedStudentMark(stu);
                } else {
                    m = a.getStudentMark(stu);
                }
                mark += wIter.next() * m;
            }
        }
        return mark;
    }

    public Map<String, Integer> getWholeTable() { // TODO: figure out how to update the calculated assessments when a change is made to the raw assessments!
        try {

            Statement st = con.createStatement();
            String sql = "SELECT * FROM courses."+year+"_"+cc+"";
            ResultSet rs = st.executeQuery(sql);

            Map<String, Integer> map = new HashMap<String, Integer>();
            while(rs.next()){
                map.put(rs.getString("id"), rs.getInt("a"+this.id));
            }
            return map;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean isPublished() { //  sql
        try {
            Statement st = con.createStatement();
            String sql = "SELECT published FROM assessments.assessments WHERE ass_id = "+id+" AND course_code = '"+cc+"' AND year = "+year+"";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
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
            String sql = "UPDATE assessments.assessments SET published = "+t+" WHERE ass_id = "+id+" AND course_code = '"+cc+"' AND year = "+year+"";
            ResultSet rs = st.executeQuery(sql);

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
    public String getCalculation(){
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
}
