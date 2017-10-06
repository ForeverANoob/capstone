package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.security.MessageDigest;

// this class is effectively empty because it is a dummy that is eventually
// supposed to wrap SQL behind an API

public class User {
    private String id;

    public User(String id) {
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
}
