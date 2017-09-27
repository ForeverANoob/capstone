package stumasys.db;

import java.util.Map;
import java.util.HashMap;

public class RawAssessment implements Assessment {
    private final String id;          // no id yet
    private int markCap;
    private Database db; // reference to the creator of this object so we can read marks from the database upon request

    public RawAssessment(int mc, Database db, String id) {
        markCap = mc;
        this.db = db;
        this.id = id;
    }

    public String getid(){
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
        return db.getRawAssessmentMark(this.id, stuId);
    }

    public void setStudentMark(String stuId, int mark) { // what danny
        db.setRawAssessmentMark(this.id, stuId, mark);
    }

    public void setMarkCap(int mc) {
        markCap = mc;
        db.setRawAssessmentMarkCap(id, mc);
    }

    public Map<String, Integer> getWholeTable() { // TODO: actually implement this
        return new HashMap<String,Integer>();
    }
}
