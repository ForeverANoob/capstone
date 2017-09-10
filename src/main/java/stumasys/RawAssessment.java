import java.util.Map;

public class RawAssessment implements Assessment {
    private Map<String, int> rawMarks;
    private int markCap;

    public RawAssessment(Map<String,int> rm, int mc) {
        rawMarks = rm;
        markCap = mc;
    }

    public int getMarkCap() {
        return markCap;
    }

    public int getStudentMark(String id) {
        int rm = rawMarks.get(id);
        return ((markCap <= rm) ? markCap : rm);
    }

    public int getUncappedStudentMark(String id) {
        return rawMarks.get(id);
    }
}
