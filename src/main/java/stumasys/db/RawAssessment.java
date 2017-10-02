package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class RawAssessment implements Assessment {
    private final int id;

    private String name;

    private int markCap;
    private Map<String, Integer> markTbl;

    private boolean published = false;
    private boolean onStudentHome = false;

    public RawAssessment(int id, String name, int markCap, Map<String, Integer> markTbl) {
        this.id = id;
        this.name = name;
        this.markCap = markCap;
        this.markTbl = markTbl;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMarkCap() {
        return markCap;
    }

    public int getStudentMark(Student s) {
        int rm = getUncappedStudentMark(s);
        return ((markCap <= rm) ? markCap : rm);
    }

    public int getUncappedStudentMark(Student s) {
        System.out.println(" ============= " + markTbl);
        return markTbl.get(
                s.getId());
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
    public void setPublishState(boolean v){
        this.published = v;
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
