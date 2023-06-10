import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
    private String username;
    private int password;
    private int charge;

    ////////////Empty Constructor////////////
    public User() throws FileNotFoundException {}
    //////////Username & password Constructor/////////
    public User(String username, int password, int charge) {
        this.username = username;
        this.password = password;
        this.charge = charge;
    }

    ////////////Username getter & setter//////////////
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {this.username = username;}
    ///////////Password getter & setter/////////////
    public int getPassword() {
        return password;
    }
    public void setPassword(int password) {
        this.password = password;
    }
    ////////////////////////////////////////////////
    @Override
    public String toString() {
        return "User▪►" +
                "username='" + username + '\'' +
                "&"+" "+"password=" + password + '\'' +
                "&"+" "+"charge=" + charge;
    }
}
