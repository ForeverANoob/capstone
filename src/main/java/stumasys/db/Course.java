package stumasys.db;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class Course {
    final private String code; // these two variables uniquely identify a given Course
    final private int year;

    private Lecturer coordinator;
    private List<Lecturer> lecturers;
    private List<Student> teachingAssistants;
    private Map<Student, Boolean> registrationStatus; // this doubles as a list of student enrolled in the course

    private Map<String, Assessment> assessments;

    public Course(
            String code, int year,
            Lecturer coordinator, List<Lecturer> lecturers,
            List<Student> teachingAssistants, List<Student> students,
    ){
        this.code = code;
        this.year = year;

        this.coordinator = coordinator;
        this.lecturers = lecturers;

        this.teachingAssistants = teachingAssistants;
        this.students = students;
        this.registrationStatus = new HashMap<Student, Boolean>();

        for (Student stu : students) {
            registrationStatus.put(stu, Boolean.TRUE);
        }
    }
    public Course(
            String code, int year,
            Lecturer coordinator, List<Lecturer> lecturers,
            List<Student> teachingAssistants,
            Map<Student, Boolean> registrationStatus
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
    
    public String getCode() {
        return code;
    }

    public int getYear() {
        return year;
    }

    // assessments things
    public Assessment getAssessment(String id) {
        return assessments.get(id);
    }
    public void addAssessment(Assessment a, String id){
        assessments.put(id, a);
    }

    public Map<String, Assessment> getAssessments(){
        return Collections.unmodifiableMap(this.assessments);
    }

    public boolean isRegistered(Student stu){ // TODO:    for all users or just student
        if (registrationStatus.get(stu) != null) {
            return registrationStatus.get(stu).booleanValue();
        } else {
            return false;
        }
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

    public Lecturer getCourseConviner(){
        return coordinator;
    }
    public void setCourseCoordinator(Lecturer c){
        coordinator = c;
    }

    public List<Student> getTeachingAssistants() {
        return Collections.unmodifiableList(teachingAssistants);
    }

    public void addTeachingAssistant(Student ta) {
        teachingAssistants.add(ta);
    }

    public void removeTeachingAssistant(Student ta) {
        teachingAssistants.remove(ta);
    }
}
