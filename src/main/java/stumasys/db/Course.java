package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Course {
    private String code; // these two variables uniquely identify a given Course
    private int year;

    private Lecturer coordinator;
    private List<Lecturer> lecturers;
    private List<Student> teachingAssistants;
    private List<Student> students;
    private Map<Student, Boolean> registrationStaus;

    private Map<String, Assessment> assessments;

    public Course(
            String code, int year,
            Lecturer coordinator, List<Lecturer> lecturers,
            List<Student> teachingAssistants, List<Students> students,
            Map<Student, Boolean> registrationStaus
    ){
        this.code = code;
        this.year = year;

        this.coordinator = coordinator;
        this.lecturers = lecturers;

        this.teachingAssistants = teachingAssistants;
        this.students = students;
        this.registrationStatus = registrationStatus;
    }

    public String getId(){
        return Integer.toString(year)+"_"+code;
    }

    // assessments things
    public Assessment getAssessment(String id) {
        return assessments.get(id);
    }
    public void addAssessment(Assessment a){
        assessments.add(a);
    }
    public void setAssessments(List<Assessment> all){
        assessments.addAll(all);
    }
    public List<Assessment> getAssessments(){
        return Collections.unmodifiableList(this.assessments);
    }

    public boolean isRegistered(Student stu){ // TODO:    for all users or just student
        if (registrationStaus.get(stu)
        return registrationStaus.
    }
    public void deregisterStudent(Student stu){ // TODO: unsure about this
        if (students.contains(stu)){
            for (int i = 0; i < students.size(); i++){
                if(students.get(i).getId().equals(stu.getId())){
                    students.get(i).deregister(year+"_"+code);
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
