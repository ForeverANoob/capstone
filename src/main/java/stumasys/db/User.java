package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.security.MessageDigest;

// this class is effectively empty because it is a dummy that is eventually
// supposed to wrap SQL behind an API

public class User {
    String id;
    Connection con;

    public User(String id, Connection con) {
        this.id = id;
        this.con = con;
    }

    public String getId(){
        return this.id;
    }
    public String findRole(){       // assumes that the user already exists
        try{
            Statement st = con.createStatement();
            String sql = "SELECT role FROM users.user_info WHERE id = '" + this.id + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getString("role");
            }
            else { System.out.println("Could not find user role"); return "No role"; }
        }catch(SQLException e){ System.out.println("Error: " + e); return "null"; }
    }

    //public String getString(){
    //    return id + " " + name + " "+ courses + " " + role;
    //}
}
