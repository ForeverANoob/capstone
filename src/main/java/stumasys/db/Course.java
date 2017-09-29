package stumasys.db;

import java.util.List;
import java.util.ArrayList;

public class Course {

    private String name;
    private int year;
    private List<User> participants;
    private List<Assessment> assessments;

    public Course(String name, int year, List<User> participants, Database db) {
        this.name = name;
        this.year = year;
        this.participants = participants;
        this.assessments = new ArrayList<Assessment>(); // TODO: adding assessments now???
        this.assessments.add(new RawAssessment(100, db, "asdde"));

    }

    public Course(String name, String year, Database db){
        this.name = name;
        this.year = Integer.parseInt(year);
        this.assessments = new ArrayList<Assessment>();
        this.participants = new ArrayList<User>();
        this.assessments.add(new RawAssessment(100, db, "wqdde"));

    }

    public Assessment getAssessment(int id) {
        return assessments.get(id);
    }

    public boolean addRawAssessment(int mc, Database db){
        assessments.add(new RawAssessment(mc, db, "id"));   // this id?
        return true;
    }

    public String getID(){
        return Integer.toString(year)+"_"+name;
    }


    public List<User> getParticipates(){
        return this.participants;           // security breach?
    }

    public List<Assessment> getAssessments(){
        return this.assessments;
    }

    // TODO: stage 4: reflect any changes directly to the DB
}
