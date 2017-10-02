package stumasys.db;

import java.util.Map;

public interface Assessment {
    public int getId();

    public String getName();

    public int getMarkCap();
    public int getStudentMark(Student stu);
    public int getUncappedStudentMark(Student stu);
    public Map<String, Integer> getWholeTable();

    public boolean isPublished();
    public void setPublishState(boolean v);

    public boolean isAvailableFromStudentHome();
    public void setStudentHomeAvailability(boolean v);
}
