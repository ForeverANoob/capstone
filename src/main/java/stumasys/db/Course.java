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
        this.participants = participants;
        this.assessments = new ArrayList<Assessment>(); // TODO: adding assessments now???

    }

    public Course(String name, String year){
        this.name = name;
        this.year = Integer.parseInt(year);
        this.assessments = new ArrayList<Assessment>();
        this.participants = new ArrayList<User>();
    }

    public Assessment getAssessment(int id) {
        return assessments.get(id);
    }

    public boolean addRawAssessment(int mc, Database db){
        assessments.add(new RawAssessment(mc, db));
        return true;
    }

    public String getID(){
        return name+"_"+Integer.toString(year);
    }

    // TODO: stage 4: reflect any changes directly to the DB
}
