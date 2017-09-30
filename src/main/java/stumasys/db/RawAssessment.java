package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class RawAssessment implements Assessment {
    private final String id;

    private int markCap;
    private Map<String, Integer> markTbl;

    private boolean published = false;
    private boolean onStudentHome = false;

    public RawAssessment(String id, String name, int markCap, Map<String, Integer> markTbl) {
        this.id = id;
        this.markCap = markCap;
        this.markTbl = markTbl;
    }

    public String getId(){
        return id;
    }

    public int getMarkCap() {
        return markCap;
    }

    public int getStudentMark(Student s) {
        int rm = getUncappedStudentMark(s);
        return ((markCap <= rm) ? markCap : rm);
    }

    public int getUncappedStudentMark(Student s) {
        return markTbl.get(s.getId());
    }

    public boolean setStudentMark(Student stu, int mark) {
        if(!markTbl.containsKey(stu.getId())) { return false; }
        markTbl.put(stu.getId(), mark);
        return true;
    }

    public void setMarkCap(int mc) {
        markCap = mc;
    }

    public Map<String, Integer> getWholeTable() {
        return Collections.unmodifiableMap(this.markTbl);
    }
    public boolean isPublished(){
        return this.published;
    }
    public void publishMarks(){
        this.published = true;
    }

    public boolean isAvailableFromStudentHome() {
        return onStudentHome;
    }

    public void setStudentHomeAvailability(boolean v) {
        onStudentHome = v;
    }

    public String toString(){
        return ""+this.id+" "+this.markCap;
    }
}
