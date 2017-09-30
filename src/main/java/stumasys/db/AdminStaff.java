package stumasys.db;

import java.util.List;

public class AdminStaff extends User {
    private String department;
    private List<Course> recentCourse;      // sorted

    public AdminStaff(
            String id,
            String department
    ){
        super(id);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void updateRecentlyVeiwedCourses(Course course){
        if(recentCourse.contains(course)){
            // sort, maybe

            return;
        }
        recentCourse.add(course);
    }
    public List<Course> getRecentlyViewedCourses(){
        return this.recentCourse;
    }

}
