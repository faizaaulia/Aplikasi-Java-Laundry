package model;

import java.util.ArrayList;

/** @author faizaaulia */

public class Admin extends Person {

    private String username,password,role;
    private ArrayList<Transaksi> transaksi;
    
    public Admin(String nama,String alamat,String noTelp,String jenisKelamin,
            String username,String password,String role,ArrayList<Transaksi> transaksi) {
        super(nama,alamat,noTelp,jenisKelamin);
        this.username = username;
        this.password = password;
        this.role = role;
        this.transaksi = transaksi;
    }

    public ArrayList<Transaksi> getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(ArrayList<Transaksi> transaksi) {
        this.transaksi = transaksi;
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
