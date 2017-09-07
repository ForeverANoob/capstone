package database;

/*
    Courses class


*/


public class Courses{       // check public, private and protected status for each method and class

    private String name;
    private int year;
    private int[] marks;    // assignments, tests, final exam. Could think of a better name and will have to change from a int[] to some other array
    private ArrayList<Users> partic = new ArrayList<Users>();

    public Courses(String name, int year, int[] mark){
        this.name = name;
        this.year = year;
        this.matks = mark;  // change due to OO programming and how it deals with objects and references
    }

    public void addUser(User){
        partic.add(user);
    }
    public Arraylist<User> getParticipants(){
        return this.partic;
    }
    public String toString(){
        return name + " " + year;
    }
}
