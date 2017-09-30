package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
//import java.security.MessageDigest;

public class User {
    private String id;
    private Connection con;

    public User(String id, Connection con) {
        this.id = id;
        this.con = con;
    }

    public String getId(){
        return this.id;
    }
}
