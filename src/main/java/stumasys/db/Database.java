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
    private Map<String, Course> courses;
    private Map<String, User> users =new HashMap<String, User>();
    private Map<String, Assessment> assessments;

    public Database() {
        // NEW:TODO: put all these users into the authorisation system!

        // Creating some sample users
        User stu1 = new Student("brrand016", new LinkedList<Course>());
        User stu2 = new Student("grncla007", new LinkedList<Course>());
        User stu3 = new Student("krydan003", new LinkedList<Course>());
        User stu4 = new Student("xyzmlg420", new LinkedList<Course>());

        User lec1 = new Lecturer("100001", "CS", new LinkedList<Course>());
        User lec2 = new Lecturer("100002", "CS", new LinkedList<Course>());
        User lec3 = new Lecturer("100003", "CS", new LinkedList<Course>());
        User lec4 = new Lecturer("100004", "CS", new LinkedList<Course>());

        User adm1 = new AdminStaff("200001", "CS", new LinkedList<Course>());
        User adm2 = new AdminStaff("200002", "CS", new LinkedList<Course>());

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
        List<Student> stulist1 = Arrays.asList({stu1,stu2,stu3});
        List<Student> stulist2 = Arrays.asList({stu1,stu2,stu4});
        List<Student> stulist3 = Arrays.asList({stu1,stu3,stu4});
        List<Student> stulist4 = Arrays.asList({stu2,stu3,stu4});

        List<Lecturer> leclist1 = Arrays.asLists({lec3, lec4});
        List<Lecturer> leclist2 = Arrays.asLists({lec4, lec1});
        List<Lecturer> leclist3 = Arrays.asLists({lec1, lec2});
        List<Lecturer> leclist4 = Arrays.asLists({lec2, lec3});

        // Creating some sample assessments within those courses
        Assessment a1 = new RawAssessment();

        // Creating some sample courses
        Course crs1 = new Course(
                "CSC1015F", 2017,
                lec1, leclist1,
                new LinkedList<Student>(),
                stulist1
            );
        Course crs2 = new Course(
                "CSC1016S", 2017,
                lec2, leclist2,
                new LinkedList<Student>(),
                stulist2
            );
        Course crs3 = new Course(
                "CSC1015F", 2016,
                lec3, leclist3,
                new LinkedList<Student>(),
                stulist3
            );
        Course crs4 = new Course(
                "CSC1016S", 2016,
                lec4, leclist4,
                new LinkedList<Student>(),
                stulist4
            );

        // adding those courses to the courses map
        courses.put("2017_csc1015f", crs1);
        courses.put("2017_csc1016s", crs2);
        courses.put("2016_csc1015f", crs3);
        courses.put("2016_csc1016f", crs4);

        // adding courses to appropriate lists for students, lecturers and admins
        
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
