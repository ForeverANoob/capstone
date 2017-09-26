package stumasys.db;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Database {

    // TODO: stage 4: create a proper database backend that interacts with
    // an SQL server instead of these tables

    private Map<String, Course> courses;
    private Map<String, User> users;
    private Map<String, Map<String, Integer>> rawAssessments;

    public Database() {
        courses = new HashMap<String,Course>();
        Course csc1015f_2017 = new Course("CSC1015F", "2017");
        courses.put("csc1015f_2017", csc1015f_2017);
    }

    public Course getCourse(String code, int year) {
        return courses.get(code +"_"+ Integer.toString(year));
    }

    public User getUser(String id) {
        return users.get(id);
    }

    /* The following are an interface used by the objects returned by this class
     * to fetch additional data from, and synchronise changes with, the database
     * underlying the system. For now, we're hardcoding things without having an
     * actual DB present.
     */

    protected void setPassword(String userId, byte[] pwHash) { // TODO: 
    }

    protected int getRawAssessmentMark(String aId, String uId) {
        return rawAssessments.get(aId).get(uId);
    }

    protected void setRawAssessmentMark(String aId, String uId, int mark) { // TODO: 
    }

    protected void setRawAssessmentMarkCap(String aId, int mc) {
    }

}
