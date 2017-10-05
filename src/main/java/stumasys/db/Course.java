package stumasys.db;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class Course {
    final private String code; // these two variables uniquely identify a given Course
    final private int year;

    private Lecturer coordinator;
    private List<Lecturer> lecturers;

    private List<Student> teachingAssistants;
    private Map<Student, Boolean> registrationStatus; // this doubles as a list of students enrolled in the course

    private List<Assessment> assessments;

    public Course(
            String code, int year,
            Lecturer coordinator, List<Lecturer> lecturers,
            List<Student> teachingAssistants, List<Student> students,
            List<Assessment> assessments
    ){
        this.code = code;
        this.year = year;

        this.coordinator = coordinator;
        this.lecturers = lecturers;

        this.teachingAssistants = teachingAssistants;
        this.registrationStatus = new HashMap<Student, Boolean>();

        this.assessments = assessments;

        for (Student stu : students) {
            registrationStatus.put(stu, Boolean.TRUE);
        }
    }
    public Course(
            String code, int year,
            Lecturer coordinator, List<Lecturer> lecturers,
            List<Student> teachingAssistants,
            Map<Student, Boolean> registrationStatus,
            Map<String, Assessment> assessments
    ){
        this.code = code;
        this.year = year;

        this.coordinator = coordinator;
        this.lecturers = lecturers;

        this.teachingAssistants = teachingAssistants;
        //this.students = students;
        this.registrationStatus = registrationStatus;
    }

    public Map<Assessment, Boolean> getPrevVisibleColumns(AdminStaff u) {
        HashMap<Assessment, Boolean> vis = new HashMap<Assessment, Boolean>();
        for(Assessment a : assessments) {
            vis.put(a, Boolean.TRUE);
        }
        return vis;
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
    public Assessment getAssessment(int id) {
        return assessments.get(id);
    }

    public int createNewRawAssessment(String name, int markCap, Map<String,Integer> markTable) {
        assessments.add(new RawAssessment(assessments.size(), name, markCap, markTable));
        return assessments.size() - 1;
    }

    public int createNewCalculatedAssessment(String name, List<Integer> sourceAssIds, List<Integer> weights) {
        LinkedList<Assessment> as = new LinkedList<Assessment>();
        for (Integer id : sourceAssIds) {
            as.add(assessments.get(id));
        }
        Assessment a = new CalculatedAssessment(assessments.size(), name, as, weights);
        assessments.add(a);
        return assessments.size() - 1;
    }

    public int createNewCalculatedAssessment(String name, List<Integer> sourceAssIds, List<Boolean> useUncapped, List<Integer> weights, int markCap) {
        LinkedList<Assessment> as = new LinkedList<Assessment>();
        for (Integer id : sourceAssIds) {
            as.add(assessments.get(id));
        }
        Assessment a = new CalculatedAssessment(assessments.size(), name, false, false, as, useUncapped, weights, markCap);
        assessments.add(a);
        return assessments.size()-1;
    }

    public List<Assessment> getAssessments(){
        return Collections.unmodifiableList(this.assessments);
    }

    public boolean isRegistered(Student stu) {
        if (registrationStatus.get(stu) != null) {
            return registrationStatus.get(stu).booleanValue();
        } else {
            return false;
        }
    }

    public void deregisterStudent(Student stu) {
        if (registrationStatus.get(stu) != null) {
            registrationStatus.put(stu, Boolean.FALSE);
        } else {
            System.err.println("Attempted to dereg student " + stu.getId() + " from course " + code + year + ", but stu isnt reg'd in 1st place.");
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
