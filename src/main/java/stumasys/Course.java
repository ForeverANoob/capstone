package stumasys;
import java.util.List;

public class Course {       // check public, private and protected status for each method and class

    private String name;
    private int year;
    private List<User> participants;

    public Course(String name, int year, List<User> participants) {
        this.name = name;
        this.year = year;
    }

    public Course(String name, String year){
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

    public int getYear(){
        return year;
    }

    public String toString(){
        return name + " " + year;
    }

    // TODO: stage 4: keep track of modifications to the course so we can commit
    // them to the DB later.
}
