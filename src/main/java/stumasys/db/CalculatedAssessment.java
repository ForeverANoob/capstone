package stumasys.db;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class CalculatedAssessment implements Assessment {
    // TODO: stage 4: extend this class to allow arbitrary calculations
    // including min/max, branching, etc, since weighted averages are not the
    // only computations commonly done.

    // TODO: stage 4: write something along the lines of a "rational number"
    // class for weighting in these calculations, rather than using int's.
    // (Normal float/double aren't appropriate for various reasons.)

    private final int id;

    private String name;

    private List<Assessment> src;
    private List<Boolean> useUncapped; // <--- if null, only uses capped marks
    private List<Integer> weight;
    private int markCap;
    private boolean published = false;
    private boolean onStudentHome = false;
    private HashMap<String, Integer> markTbl;

    public static int calculateAppropriateMarkCap(List<Assessment> src, List<Integer> weight) {
        Iterator<Integer> wIter = weight.iterator();
        int mc = 0;
        for (Assessment a : src) {
            mc += a.getMarkCap()*wIter.next();
        }
        return mc;
    }

    public CalculatedAssessment(int id, String name, List<Assessment> src, List<Integer> weight) {
        this(id, name, false, false, src, null, weight, calculateAppropriateMarkCap(src, weight));
        assert(src.size() == weight.size());

    }

    public CalculatedAssessment(
            int id, String name,
            boolean published, boolean onStudentHome,
            List<Assessment> src, List<Boolean> useUncapped, List<Integer> weight,
            int markCap
    ){
        this.id = id;
        this.name = name;
        this.published = published;
        this.onStudentHome = onStudentHome;
        this.src = src;
        this.useUncapped = useUncapped;
        this.weight = weight;
        this.markCap = markCap;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getMarkCap() {
        return markCap;
    }

    public int getStudentMark(Student stu) {
        int mark = getUncappedStudentMark(stu);
        return ((markCap < mark) ? markCap : mark);
    }

    public int getUncappedStudentMark(Student stu) {
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

    public Map<String, Integer> getWholeTable() { // TODO: store in DB and update when underlying RawAssessments are updated.
        HashMap<String, Integer> markTbl = new HashMap<String, Integer>();

        return markTbl;
    }

    public int getId() {
        return this.id;
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
}
