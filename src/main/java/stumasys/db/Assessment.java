package stumasys.db;

import java.util.Map;

// TODO:NEW: seriously need to think about how the ID happens. the whole
// "get id from the name" necessitates that we create a new columns every
// time the name changes. it makes far more sense to just create a unique
// ID that we know never changes, and allow its name to change arbitrarily.

public interface Assessment {
    public int getId();

    public String getName();

    public int getMarkCap();
    public int getStudentMark(Student stu);
    public int getUncappedStudentMark(Student stu);
    public Map<String, Integer> getWholeTable();

    public boolean isPublished();
    public void setPublishState(boolean v);

    public boolean isUploaded();
    public void setUpload(boolean v);

    public boolean isAvailableOnStudentHome();
}
