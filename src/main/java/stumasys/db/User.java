package stumasys.db;

import java.util.List;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    private String id;
    private byte[] pwSaltedDblHash; // Note: at no point is the user's plaintext
    private byte[] pwHashSalt;      // password in memory on the server this
                                    // application resides on.

    //public User(String id, byte[] pwDHash, byte[] pwHashSalt, Database db) {
    public User(String id, byte[] pwDHash, byte[] pwHashSalt) {
        this.id = id;
        this.pwHashSalt = pwHashSalt;
        this.pwSaltedDblHash = pwDHash;
        //this.db = db;
    }

    public String getId(){
        return this.id;
    }

    public byte[] getSaltedDHash() {
        return pwSaltedDblHash;
    }

    public byte[] getHashSalt() {
        return pwHashSalt;
    }

    public boolean checkPassword(byte[] pwHash) {
        MessageDigest sha512;
        try {
        sha512 = MessageDigest.getInstance("SHA-512");
        } catch (Exception e) {
            System.err.println("Couldn't instantiate SHA-512, which is part of the Java standard library, so why in the name of sweet baby jesus did this not work?");
            System.exit(1);
            return false;
        }

        sha512.reset(); // is this necessary? probably not. wont do any harm tho
        sha512.update(pwHashSalt);
        sha512.update(pwHash);
        byte[] result = sha512.digest();

        return MessageDigest.isEqual(result, pwSaltedDblHash);
    }

    protected void setPassword(byte[] pwHash) {
        //db.setPassword(id, pwHash);
    }
}
