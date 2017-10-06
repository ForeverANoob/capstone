package stumasys.db;

import java.util.List;
import java.util.Collections;


public class AdminStaff extends User {
    private String department;
    private List<Course> recentCourses;

    public AdminStaff(
            String id,
            String department,
            List<Course> recentCourses
    ){
        super(id);
        this.department = department;
        this.recentCourses = recentCourses;
    }

    public String getDepartment() {
        return department;
    }

    // add, or push-to-top, this course to the list of recently viewed courses available on the adminstaffer's homepage
    public void updateRecentlyVeiwedCourses(Course course){
        recentCourses.remove(course);
        recentCourses.add(0, course);
        if (recentCourses.size() > 10) { // TODO: is this a reasonable constraint? (list of admin staff's recent courses is never more than 10)
            recentCourses.remove(10);
        }
    }

    public List<Course> getRecentlyViewedCourses(){
        return Collections.unmodifiableList(this.recentCourses);
    }

}
