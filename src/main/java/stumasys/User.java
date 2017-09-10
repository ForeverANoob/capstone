package stumasys;

import java.util.List;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    private String id;
    private byte[] pwSaltedDblHash; // Note: at no point is the user's plaintext
    private byte[] pwHashSalt;      // password in memory on the server this
                                    // application resides on.

    public User(String id, AuthLevel al, byte[] pwDHash, byte[] pwHashSalt) {
        this.id = id;
        this.authLvl = al;
        this.pwHashSalt = pwHashSalt;
        this.pwSaltedDblHash = pwDHash;
    }

    public String getId(){
        return this.id;
    }

    public boolean checkPassword(byte[] pwHash) {
        MessageDigest sha512 = MessageDigest.getInstance("SHA-512");

        sha512.reset(); // is this necessary? probably not. wont do any harm tho
        sha512.update(pwHashSalt);
        sha512.update(pwHash);
        byte[] result = sha512.digest();

        return MessageDigest.isEqual(result, pwSaltedDblHash);
    }
}
