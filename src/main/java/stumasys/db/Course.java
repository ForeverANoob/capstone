package stumasys.db;

import java.util.List;
import java.util.ArrayList;

public class Course {

    private String name;
    private int year;
    private List<User> participants;
    private List<Assessment> assessments;

    public Course(String name, int year, List<User> participants) {
        this.name = name;
        this.year = year;
    }

    public Course(String name, String year){
        this.name = name;
        this.year = Integer.parseInt(year);
        assessments = new ArrayList<Assessment>();
        participants = new ArrayList<User>();
    }

    public Assessment getAssessment(int id) {
        return assessments.get(id);
    }

    

    // TODO: stage 4: reflect any changes directly to the DB
}
