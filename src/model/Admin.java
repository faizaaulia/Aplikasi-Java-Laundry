package model;

import java.util.ArrayList;

/** @author faizaaulia */

public class Admin extends Person {

    private String username,password,role;

    public Admin(String nama,String alamat,String noTelp,String jenisKelamin,
            String username,String password,String role) {
        super(nama,alamat,noTelp,jenisKelamin);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
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
