package stumasys;


import java.util.ArrayList;

/*
    Courses class


*/


public class Courses {       // check public, private and protected status for each method and class

    private String name;
    private int year;
    private int[] marks = new int[4];    // assignments, tests, final exam. Could think of a better name and will have to change from a int[] to some other array
    private ArrayList<User> partic = new ArrayList<User>();

    public Courses(String name, int year, int[] mark){
        this.name = name;
        this.year = year;
        this.marks = mark;  // change due to OO programming and how it deals with objects and references
    }

    public Courses(String name, String year){
        this.name = name;
        this.year = Integer.parseInt(year);


    }

    public void addUser(User user){
        partic.add(user);
    }
    public ArrayList<User> getParticipants(){
        return this.partic;
    }
    public User getParticipant(String id){
        for (int i = 0; i < partic.size(); i++){
            if(partic.get(i).getID().equals(id)){
                return partic.get(i);
            }
        }
        return null;
    }

    public String getName(){
        return this.name;
    }
    public String getYear(){
        return Integer.toString(year);
    }
    public String getMark(){
        return "420 smoke weed ever day";   // must remember to change this before demo
    }
    public String toString(){
        return name + " " + year;
    }
}
