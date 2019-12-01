package model;

/** @author faizaaulia */

public class Admin extends Person {

    private String username,password;

    public Admin(String username, String password, String nama, String alamat, int noTelp) {
        super(nama, alamat, noTelp);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
