package stumasys.db;

import java.util.Map;

// All grades are stored by Assessments.

// TODO: this should have been an abstract class with getters for ID/name
// already contrete because both Calculated and Raw assessment share this
// functionality

public interface Assessment {
    // unique identifier for this assessment under the context of the course it belongs to
    public int getId();

    public String getName();

    // the maximum mark awarded for this assessment
    public int getMarkCap();
    
    // marks can be higher than the cap, in some cases
    public int getUncappedStudentMark(Student stu);
    public int getStudentMark(Student stu);

    // returns the entire table of marks from this assessment
    public Map<String, Integer> getWholeTable();

    // assessments are not visible to students until they are publushed
    public boolean isPublished();
    public void setPublishState(boolean v);

    // some assessments need not be seen directly on the student homepage (e.g. subsections of tests)
    public boolean isAvailableFromStudentHome();
    public void setStudentHomeAvailability(boolean v);
}
