package stumasys.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;


public class test{

    public static void create(Connection con, Database db) throws SQLException{

        db.createCourse("csc1015f", 2017);
        db.createCourse("csc1016s", 2017);

        db.addUser("brrand016", "Andre", "student", "Bsc", "", "qwe");
            db.addCourseToUser(2017, "CSC1015F", "brrand016");
            db.addUserToCourse(2016, "CSC1015F", "brrand016");
            db.addCourseToUser(2017, "CSC1016S", "brrand016");
            db.addUserToCourse(2016, "CSC1016S", "brrand016");

        db.addUser("grncla007", "Claudia", "student", "Bsc", "", "qwe");
            db.addCourseToUser(2017, "CSC1015F", "grncla007");
            db.addUserToCourse(2016, "CSC1015F", "grncla007");
            db.addCourseToUser(2017, "CSC1016S", "grncla007");
            db.addUserToCourse(2016, "CSC1016S", "grncla007");

        db.addUser("krydan003", "Danny", "student", "Bsc", "", "qwe");
            db.addCourseToUser(2017, "CSC1015F", "krydan003");
            db.addUserToCourse(2016, "CSC1015F", "krydan003");
            db.addCourseToUser(2017, "CSC1016S", "krydan003");
            db.addUserToCourse(2016, "CSC1016S", "krydan003");

        db.addUser("xyzmlg420", "Janos, Hugh", "student", "BA", "", "qwe");
            db.addCourseToUser(2017, "CSC1015F", "xyzmlg420");
            db.addUserToCourse(2016, "CSC1015F", "xyzmlg420");
            db.addCourseToUser(2017, "CSC1016S", "xyzmlg420");
            db.addUserToCourse(2016, "CSC1016S", "xyzmlg420");

        HashMap<String, String> reg = new HashMap<String,String>();
            reg.put("brrand016", "GIP");
            reg.put("grncla007", "GIP");
            reg.put("krydan003", "GIP");
            reg.put("xyzmlg420", "GIP");

        db.getCourse("csc1015f", 2017).batchUpdateRegistrationStatus(reg);

        db.addUser("200001", "phil, mahooters", "admin", "", "cs", "qwe");
        db.addUser("200002", "Hugh, Janos", "admin", "", "cs", "qwe");

        db.addUser("200001", "phil, mahooters", "lecturer", "", "cs", "qwe");
        db.addUser("200002", "Hugh, Janos", "lecturer", "", "cs", "qwe");

        AdminStaff as = (AdminStaff) db.getUser("200001");
        as.updateRecentlyViewedCourses(db.getCourse("csc1015f", 2017));

        HashMap<String, Integer> marktable = new HashMap<String,Integer>();
            marktable.put("brrand016", 10);
            marktable.put("grncla007", 10);
            marktable.put("krydan003", 10);
            marktable.put("xyzmlg420", 10);

        Course c = db.getCourse("csc1015f", 2017);
        int id = c.createNewRawAssessment("test", 80, marktable);

        System.out.println(db.checkAssessment(0, "csc1015f", 2017));
        int idc = c.createNewCalculatedAssessment("cal", 75, "[0] + [0]");
        System.out.println(db.checkAssessment(0, "csc1015f", 2017));
        System.out.println(c.getAssessment(0));

        System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,fdf,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");

/*
        // Creating some sample users
        System.out.println(db.checkUser("brrand016"));

        db.addUser("brrand016", "Andre", "student", "Bsc", "", "qwe");
        db.addUser("grncla007", "Claudia", "student", "Bsc", "", "qwe");
        db.addUser("krydan003", "Danny", "student", "Bsc", "", "qwe");
        db.addUser("xyzmlg420", "Hugh, Janos", "student", "BA", "", "qwe");

        db.addUser("100001", "Violet, Janos", "lecturer", "", "Gender", "qwe");
        db.addUser("100002", "Sans, Janos", "lecturer", "", "cs", "qwe");
        db.addUser("100003", "Vict, Janos", "lecturer", "", "cs", "qwe");
        db.addUser("100004", "Leo, Janos", "lecturer", "", "swiss cheese", "qwe");

        db.addUser("200001", "phil, mahooters", "admin", "", "cs", "qwe");
        db.addUser("200002", "Hugh, Janos", "admin", "", "cs", "qwe");

        System.out.println(db.checkUser("brrand016"));
        System.out.println(db.getUser("brrand016").getId());


        String[] s = new String[7];
        s[0] = "id"; s[1] = "ass1"; s[2] = "ass2"; s[3] = "test1"; s[4] = "test2"; s[5] = "classmark"; s[6] = "final";

        String[] t = new String[6];
        t[0] = "id"; t[1] = "ass1"; t[2] = "test1"; t[3] = "test2"; t[4] = "classmark"; t[5] = "final";

        String[] u = new String[7];
        u[0] = "id"; u[1] = "ass1"; u[2] = "ass2"; u[3] = "test1"; u[4] = "test2"; u[5] = "classmark"; u[6] = "final";

        String[] v = new String[7];
        v[0] = "id"; v[1] = "ass1"; v[2] = "ass2"; v[3] = "test1"; v[4] = "test2"; v[5] = "classmark"; v[6] = "final";

        if (!db.checkCourse("CSC1015F", 2017))
            db.addCourse("2017_CSC1015F", s);
        if (!db.checkCourse("CSC1015F", 2016))
            db.addCourse("2016_CSC1015F", t);
        if (!db.checkCourse("CSC1016S", 2017))
            db.addCourse("2017_CSC1016S", u);
        if (!db.checkCourse("CSC1016S", 2016))
            db.addCourse("2016_CSC1016S", v);

        // Creating lists of students/lecturers to be added to courses in a sec
        Course c = db.getCourse("CSC1016S", 2017);

        List<User> users = new ArrayList<User>();
        users.add(db.getUser("brrand016"));
        users.add(db.getUser("grncla007"));
        users.add(db.getUser("krydan003"));
        users.add(db.getUser("xyzmlg420"));

        System.out.println("ffew");
/*        c.setParticipants(users);

        for (int i = 0; i < users.size(); i++){
            db.addCourseToUser(c.getYear(), c.getCode(), users.get(i).getId());
        }

        for (int i = 0; i < 23; i++){
            Assessment a = c.getAssessment(i);
            if (a == null){continue;}
            System.out.println(a);
            if (a instanceof CalculatedAssessment) {
                a.setCalculation("12 + 15");
            }
            a.setStudentMark((Student)users.get(0), 50+i);
            if (i%2 == 0){
                a.setCalculation("12 + 15");

            }
            a.setUpload(true);

            a.setMarkCap(60+i);
        }

        User admin = db.getUser("200001");
        AdminStaff ad= (AdminStaff)admin;
        ad.updateRecentlyVeiwedCourses(c);
        ad.updateRecentlyVeiwedCourses(db.getCourse("CSC1015F", 2016));
        */
    }

    public static void delete(Connection con) throws SQLException{
        Statement st = con.createStatement();
        String sql = "DROP TABLE courses.2017_csc1015f";
        ResultSet rs = st.executeQuery(sql);

        st = con.createStatement();
        sql = "DROP TABLE courses.2017_csc1016s";
        rs = st.executeQuery(sql);

        st = con.createStatement();
        sql = "TRUNCATE TABLE courses.courses_info";
        rs = st.executeQuery(sql);

        st = con.createStatement();
        sql = "TRUNCATE TABLE users.user_info";
        rs = st.executeQuery(sql);

        st = con.createStatement();
        sql = "TRUNCATE TABLE users.user_courses";
        rs = st.executeQuery(sql);

        st = con.createStatement();
        sql = "TRUNCATE TABLE assessments.assessments";
        rs = st.executeQuery(sql);
    }
}
