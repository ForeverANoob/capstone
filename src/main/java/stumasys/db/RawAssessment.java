package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class RawAssessment implements Assessment {
    private final String id;          // no id yet
    private int markCap;
    //private Database db; // reference to the creator of this object so we can read marks from the database upon request
    private HashMap<String, Integer> stu; // only need the marks of the student
    private boolean published = false;
    //private HashMap<String, >

    public RawAssessment(int mc, String id) {
        markCap = mc;
        //this.db = db;
        this.id = id;
        this.stu = new HashMap<String, Integer>();
    }

    public String getId(){
        return this.id;
    }

    public int getMarkCap() {
        return markCap;
    }

    public int getStudentMark(String id) {
        int rm = getUncappedStudentMark(id);
        return ((markCap <= rm) ? markCap : rm);
    }

    public int getUncappedStudentMark(String stuId) {
        //return db.getRawAssessmentMark(this.id, stuId); // TODO: do this instead of hardcode
        return stu.get(stuId);  // returns student's mark
    }

    public boolean setStudentMark(String stuId, int mark) {
        if(!stu.containsKey(stuId)) { return false; }   // doesn't contain the student
        stu.put(stuId, mark);
        return true;
    }

    public void setMarkCap(int mc) {
        markCap = mc;
    }

    public Map<String, Integer> getWholeTable() { // TODO: actually implement this
        return Collections.unmodifiableMap(this.stu);
    }
    public boolean isPublished(){
        return this.published;
    }
    public boolean publishMarks(){
        this.published = true;
        return true;                        // why we have this
    }
    public String toString(){
        return ""+this.id+" "+this.markCap;
    }
}
