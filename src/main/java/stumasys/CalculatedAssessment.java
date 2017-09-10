import java.util.List;
import java.util.Iterator;

public class CalculatedAssessment implements Assessment {
    // TODO: stage 4: extend this class to allow arbitrary calculations 
    // including min/max, branching, etc, since weighted averages are not the
    // only computations commonly done.

    // TODO: stage 4: write something along the lines of a "rational number"
    // class for weighting in these calculations, rather than using int's.
    // (Normal float/double aren't appropriate for various reasons.)

    private List<Assessment> src;
    private List<Boolean> useUncapped; // <--- if null, only uses capped marks
    private List<Integer> weight;
    private int markCap;

    public CalculatedAssessment(List<Assessment> src, List<Integer> weight) {
        assert(src.size() == weight.size());

        Iterator<Integer> wIter = weight.iterator();
        int mc = 0;
        for (Assessment a : src) {
            mc += a.getMarkCap()*wIter.next();
        }

        this(src, null, weight, mc);
    }

    public CalculatedAssessment(
            List<Assessment> src, List<Boolean> useUncapped, List<int> weight,
            int markCap
    ){
        this.src = src;
        this.useUncapped = useUncapped;
        this.weight = weight;
        this.markCap = markCap;
    }

    public int getMarkCap() {
        return markCap;
    }

    public int getStudentMark(String id) {
        int mark = getUncappedStudentMark(id);
        return ((markCap < mark) ? markCap : mark);
    }

    public int getUncappedStudentMark(String id) {
        int mark = 0;
        Iterator<Integer> wIter = weight.iterator();
        if (useUncapped == null) {
            for (Assessment a : src) {
                Iterator<Integer> wIter = weight.iterator();
                mark += a.getStudentMark();
            }
        } else {
            Iterator<Boolean> uncapIter = weight.iterator();
            for (Assessment a : src) {
                int m;
                if (uncapIter.next()) {
                    m = a.getUncappedStudentMark(id);
                } else {
                    m = a.getStudentMark(id);
                }
                mark += wIter.next() * m;
            }
        }
        return mark;
    }

}
