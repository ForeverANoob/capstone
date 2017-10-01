package stumasys.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Database {
    private Map<String, Course> courses = new HashMap<String, Course>();
    private Map<String, User> users = new HashMap<String, User>();

    public Database() {
        // Creating some sample users
        Student stu1 = new Student("brrand016", new LinkedList<Course>());
        Student stu2 = new Student("grncla007", new LinkedList<Course>());
        Student stu3 = new Student("krydan003", new LinkedList<Course>());
        Student stu4 = new Student("xyzmlg420", new LinkedList<Course>());

        Lecturer lec1 = new Lecturer("100001", "CS", new LinkedList<Course>());
        Lecturer lec2 = new Lecturer("100002", "CS", new LinkedList<Course>());
        Lecturer lec3 = new Lecturer("100003", "CS", new LinkedList<Course>());
        Lecturer lec4 = new Lecturer("100004", "CS", new LinkedList<Course>());

        AdminStaff adm1 = new AdminStaff("200001", "CS", new LinkedList<Course>());
        AdminStaff adm2 = new AdminStaff("200002", "CS", new LinkedList<Course>());

        // Adding users to the users map
        users.put("brrand016", stu1);
        users.put("grncla007", stu2);
        users.put("krydan003", stu3);
        users.put("xyzmlg420", stu4);

        users.put("100001", lec1);
        users.put("100002", lec2);
        users.put("100003", lec3);
        users.put("100004", lec4);

        users.put("200001", adm1);
        users.put("200002", adm2);

        // Creating lists of students/lecturers to be added to courses in a sec
        List<Student> stulist1 = Arrays.asList(stu1,stu2,stu3);
        List<Student> stulist2 = Arrays.asList(stu2,stu3,stu4);
        List<Student> stulist3 = Arrays.asList(stu3,stu4,stu1);
        List<Student> stulist4 = Arrays.asList(stu4,stu1,stu2);

        List<Lecturer> leclist1 = Arrays.asList(lec3, lec4);
        List<Lecturer> leclist2 = Arrays.asList(lec4, lec1);
        List<Lecturer> leclist3 = Arrays.asList(lec1, lec2);
        List<Lecturer> leclist4 = Arrays.asList(lec2, lec3);


        // creating marktables for assessments
        // NEW:TODO: add a test case for when people miss an assessment?
        HashMap<String,Integer> mt1 = new HashMap<String,Integer>();
            mt1.put("brrand016", 100);
            mt1.put("grncla007", 100);
            mt1.put("krydan003", 100);
        HashMap<String,Integer> mt2 = new HashMap<String,Integer>();
            mt2.put("grncla007", 100);
            mt2.put("krydan003", 100);
            mt2.put("xyzmlg420", 100);
        HashMap<String,Integer> mt3 = new HashMap<String,Integer>();
            mt3.put("krydan003", 100);
            mt3.put("xyzmlg420", 100);
            mt3.put("brrand016", 100);
        HashMap<String,Integer> mt4 = new HashMap<String,Integer>();
            mt4.put("xyzmlg420", 100);
            mt4.put("brrand016", 100);
            mt4.put("grncla007", 100);

        HashMap<String,Integer> mt5 = new HashMap<String,Integer>(); 
            mt5.put("brrand016", 50);
            mt5.put("grncla007", 50);
            mt5.put("krydan003", 50);
        HashMap<String,Integer> mt6 = new HashMap<String,Integer>();
            mt6.put("grncla007", 50);
            mt6.put("krydan003", 50);
            mt6.put("xyzmlg420", 50);
        HashMap<String,Integer> mt7 = new HashMap<String,Integer>();
            mt7.put("krydan003", 50);
            mt7.put("xyzmlg420", 50);
            mt7.put("brrand016", 50);
        HashMap<String,Integer> mt8 = new HashMap<String,Integer>();
            mt8.put("xyzmlg420", 50);
            mt8.put("brrand016", 50);
            mt8.put("grncla007", 50);

        // creating assessments, putting in courses
        Assessment ra1 = new RawAssessment(1, "Test", 100, mt1);
        Assessment ra2 = new RawAssessment(2, "Prac", 100, mt5);
        Assessment ra3 = new RawAssessment(1, "Test", 100, mt2);
        Assessment ra4 = new RawAssessment(2, "Prac", 100, mt6);
        Assessment ra5 = new RawAssessment(1, "Test", 100, mt3);
        Assessment ra6 = new RawAssessment(2, "Prac", 100, mt7);
        Assessment ra7 = new RawAssessment(1, "Test", 100, mt4);
        Assessment ra8 = new RawAssessment(2, "Prac", 100, mt8);


        Assessment ca1 = new CalculatedAssessment(3, "Final mark", Arrays.asList(ra1, ra2), Arrays.asList(75, 25));
        Assessment ca2 = new CalculatedAssessment(3, "Final mark", Arrays.asList(ra3, ra4), Arrays.asList(75, 25));
        Assessment ca3 = new CalculatedAssessment(3, "Final mark", Arrays.asList(ra5, ra6), Arrays.asList(75, 25));
        Assessment ca4 = new CalculatedAssessment(3, "Final mark", Arrays.asList(ra7, ra8), Arrays.asList(75, 25));


        // Creating some sample courses
        Course crs1 = new Course(
                "CSC1015F", 2017,
                lec1, leclist1,
                new LinkedList<Student>(),
                stulist1,
                new LinkedList<Assessment>(Arrays.asList(ra1, ra2, ca1))
            );
        lec1.addCourse(crs1); lec3.addCourse(crs1); lec4.addCourse(crs1); stu1.addCourse(crs1); stu2.addCourse(crs1); stu3.addCourse(crs1);
        Course crs2 = new Course(
                "CSC1016S", 2017,
                lec2, leclist2,
                new LinkedList<Student>(),
                stulist2,
                new LinkedList<Assessment>(Arrays.asList(ra3, ra4, ca2))
            );
        lec2.addCourse(crs2); lec4.addCourse(crs2); lec1.addCourse(crs2); stu2.addCourse(crs2); stu3.addCourse(crs2); stu4.addCourse(crs2);
        Course crs3 = new Course(
                "CSC1015F", 2016,
                lec3, leclist3,
                new LinkedList<Student>(),
                stulist3,
                new LinkedList<Assessment>(Arrays.asList(ra5, ra6, ca3))
            );
        lec3.addCourse(crs3); lec1.addCourse(crs3); lec2.addCourse(crs3); stu3.addCourse(crs3); stu4.addCourse(crs3); stu1.addCourse(crs3);
        Course crs4 = new Course(
                "CSC1016S", 2016,
                lec4, leclist4,
                new LinkedList<Student>(),
                stulist4,
                new LinkedList<Assessment>(Arrays.asList(ra7, ra8, ca4))
            );
        lec4.addCourse(crs4); lec2.addCourse(crs4); lec3.addCourse(crs4); stu4.addCourse(crs4); stu1.addCourse(crs4); stu2.addCourse(crs4);

        // adding those courses to the courses map
        courses.put("2017_csc1015f", crs1);
        courses.put("2017_csc1016s", crs2);
        courses.put("2016_csc1015f", crs3);
        courses.put("2016_csc1016f", crs4);
        
    }

    public List<Course> getLikeCourse(String name){
        return null;            // TODO: implement SQL search query for courses with similar name
    }
    public List<Course> getLikeYear(String year){
        return null;            // TODO: implement SQL search for courses in this year
    }

    public Course getCourse(String code, int year) {
        return courses.get(Integer.toString(year) +"_"+ code.toLowerCase());
    }

    public User getUser(String id) {
        return users.get(id);
    }
}
