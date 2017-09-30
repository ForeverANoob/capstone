package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Course {

    private String name;
    private int year;
    //private List<User> participants;
    private List<Assessment> assessments;
    private List<Student> TA;
    private List<Student> students;
    private List<Lecturer> lecturers;
    private Lecturer courseConviner;

    public Course(String name, int year, List<User> user) {
        this.name = name;
        this.year = year;
        //this.participants = participants;
        this.assessments = new ArrayList<Assessment>(); // TODO: adding assessments now???
        //this.assessments.add(new RawAssessment(100, db, "asdde"));
        this.TA = new ArrayList<Student>();
        this.students = new ArrayList<Student>();
        this.lecturers = new ArrayList<Lecturer>();
        this.setParticipants(user);
    }

    public Course(String name, String year){
        this.name = name;
        this.year = Integer.parseInt(year);
        this.assessments = new ArrayList<Assessment>();
        this.TA = new ArrayList<Student>();
        this.students = new ArrayList<Student>();
        this.lecturers = new ArrayList<Lecturer>();
        //this.participants = new ArrayList<User>();
        //this.assessments.add(new RawAssessment(100, db, "wqdde"));
    }

    public String getID(){
        return Integer.toString(year)+"_"+name;
    }

    // assessments things
    public Assessment getAssessment(int id) { // might need to be a hashmap
        return assessments.get(id);
    }
    public void addAssessment(Assessment raw){
        assessments.add(raw);   // all assessments are added like this
    }
    public void setAssessments(List<Assessment> all){
        assessments.addAll(all);
    }
    public List<Assessment> getAssessments(){
        return Collections.unmodifiableList(this.assessments);
    }
/*
    public List<User> getParticipates(){
        return this.participants;           // security breach?
    }
*/
    // TODO: stage 4: reflect any changes directly to the DB

    public boolean isRegistered(User user){ // TODO:    for all users or just student
        if (students.contains(user) || lecturers.contains(user)){
            return false;
        }
        return true;
    }
    public void deregisterStudent(Student stu){ // TODO: unsure about this
        if (students.contains(stu)){
            for (int i = 0; i < students.size(); i++){
                if(students.get(i).getID().equals(stu.getID())){
                    students.get(i).deregister(year+"_"+name);
                    break;
                }
            }
        }
    }
    public void setParticipants(List<User> users){
        for (int i = 0; i < users.size(); i++){
            User u = users.get(i);
            if (u instanceof Student){  // is a student
                students.add((Student)u);
            }
            else{       // is a lecturer
                lecturers.add((Lecturer)u);
            }
        }
    }

    public Lecturer getCourseConviner(){
        return courseConviner;
    }
    public void setCourseConviner(Lecturer c){
        courseConviner = c;
        if (!lecturers.contains(c)){    // TODO: check if correct
            lecturers.add(c);
            //participants.add(c);
        }
    }

    public List<Student> getTA(){
        return TA;
    }
    public void setTA(Student ta){
        this.TA.add(ta);
    }
}
