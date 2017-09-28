package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Database {

    // TODO: stage 4: create a proper database backend that interacts with
    // an SQL server instead of these tables

    private Map<String, Course> courses;
    private Map<String, User> users =new HashMap<String, User>();        // remember to change this
    private Map<String, Assessment> assessments;

    public Database() {     // this sht has so much hardcoding in it

        courses = new HashMap<String,Course>();
        assessments = new HashMap<String, Assessment>();

        // creating sample users
        User u1 = new Student("BRRAND016", new ArrayList<Course>(), new byte[20] , new byte[12]);
        User u2 = new Student("BRRAND017", new ArrayList<Course>(), new byte[20] , new byte[12]);
        Student u3 = new Student("qwe", new ArrayList<Course>(), new byte[20] , new byte[12]);

        // adding those users to our "users" map
        users.put("BRRAND016", u1);
        users.put("BRRAND017", u2);
        users.put("qwe", u3);

        // list of users, for adding to sample courses
        ArrayList<User> userList = new ArrayList<User>();
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);

        // creating sample courses; NB: none have any assessments inside!
        Course c = new Course("CSC3002S", "2017", this);  // has no assessments in it
        courses.put("csc3002s_2017", c);

        Course csc1015f_2017 = new Course("CSC1015F", 2017, userList, this);
        courses.put("csc1015f_2017", csc1015f_2017);

        Course csc1016s_2017 = new Course("CSC1015S", 2017, userList, this);
        courses.put("csc1016s_2017", csc1016s_2017);

        Course csc1015f_2016 = new Course("CSC1015F", 2016, userList, this);
        courses.put("csc1015f_2016", csc1015f_2016);

        Course csc1016s_2016 = new Course("CSC1015S", 2016, userList ,this);
        courses.put("csc1016s_2016", csc1016s_2016);

        u3 = (Student)u3;
        u3.addCourse(csc1015f_2017);
        u3.addCourse(csc1016s_2016);
        u3.addCourse(csc1015f_2016);

        csc1015f_2017.addRawAssessment(32, this);

    }

    public Course getCourse(String code, int year) {
        return courses.get(code.toLowerCase() +"_"+ Integer.toString(year));
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public boolean isAuthorised(String saltpass){

        return true;
    }
    /* The following are an interface used by the objects returned by this class
     * to fetch additional data from, and synchronise changes with, the database
     * underlying the system. For now, we're hardcoding things without having an
     * actual DB present.
     */

    protected void setPassword(String userId, byte[] pwHash) { // TODO:
        User tmp = users.get(userId);
        tmp.setPassword(pwHash);
    }

    protected int getRawAssessmentMark(String aId, String uId) {  // TODO: Have to fix this
        Assessment a = assessments.get(aId);
        if (a == null){return 24;}                                // if the assignment doesn't exist
        return (assessments.get(aId)).getStudentMark(uId);        // wrong?
    }

    protected void setRawAssessmentMark(String aId, String uId, int mark) { // going through the student vs. the course // TODO:
        //User tmp = users.get(uId);
        //return tmp.setRawAssessmentMark(cId, aId, mark);
        ((RawAssessment)assessments.get(aId)).setStudentMark(uId, mark); // should we return a boolean value
    }

    protected void setRawAssessmentMarkCap(String aId, int mc) {  // TODO:
        ((RawAssessment)assessments.get(aId)).setMarkCap(mc);     // done?
    }

    protected Map<String, Assessment> getAssessments(){
        return assessments;                              // security leak?
    }

}
