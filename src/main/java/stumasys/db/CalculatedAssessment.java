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
    // TODO: stage 4: extend this class to allow arbitrary calculations
    // including min/max, branching, etc, since weighted averages are not the
    // only computations commonly done.

    // TODO: stage 4: write something along the lines of a "rational number"
    // class for weighting in these calculations, rather than using int's.
    // (Normal float/double aren't appropriate for various reasons.)
    
    private final String id;
    private Connection con;
    private String[] all;

    public static int calculateAppropriateMarkCap(List<Assessment> src, List<Integer> weight) {
        Iterator<Integer> wIter = weight.iterator();
        int mc = 0;
        for (Assessment a : src) {
            mc += a.getMarkCap()*wIter.next();
        }
        return mc;
    }

    public CalculatedAssessment(String id, Connection con){
        this.id = id;
        this.con = con;
        this.all = id.split("_");
    }

    public String getId() {
        return id;
    }

    public String getName(){ // TODO: sql
        return null;
    }

    public void setName(String name) { // TODO: sql
    }

    public int getMarkCap() { // TODO: sql
        return -1;
    }

    public int getStudentMark(Student stu) {
        int um = getUncappedStudentMark(stu);
        int mc = getMarkCap();
        return (um > mc ? mc : um);
    }

    public int getUncappedStudentMark(Student stu) { // TODO: convert this old hardcoded stuff into SQL (basically just 
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

    public Map<String, Integer> getWholeTable() { // TODO: sql. store whole calculated mark table in DB, then update it when underlying RawAssessments are updated.
        return null;
    }

    public boolean isPublished(){ // TODO: sql
        return true;
    }

    public void setPublishState(boolean v){ // TODO: sql
    }

    public boolean isAvailableOnStudentHome() { // TODO: sql
        return true;
    }

    public void setStudentHomeAvailability(boolean v) { // TODO: sql
    }
}
