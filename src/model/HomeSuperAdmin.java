package model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author faizaaulia */

public class HomeSuperAdmin {
    static ArrayList<Transaksi> dafTransaksi = new ArrayList();
    private Connection con;
    Koneksi conn;
    
    public HomeSuperAdmin() {
        conn = new Koneksi();
        conn.connect();
    }
    
    public ArrayList<Admin> loadDataAdmin() {
        ArrayList<Admin> dafAdmin = new ArrayList();
        con = conn.getKoneksi();
        ResultSet rs;
        try {
            String query = "SELECT * FROM tb_admin ORDER BY id_admin DESC";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString(2);
                String password = rs.getString(3);
                String nama = rs.getString(4);
                String alamat = rs.getString(5);
                String noTelp = rs.getString(6);
                String jenisKelamin = rs.getString(7);
                String role = rs.getString(8);
                Admin a = new Admin(nama,alamat,noTelp,jenisKelamin,
                        username,password,role,dafTransaksi);
                dafAdmin.add(a);
            }
            System.out.println("DATA LOADED");
            return dafAdmin;
        } catch (SQLException ex) {
            Logger.getLogger(HomeSuperAdmin.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void insertAdmin(Admin a) {
        try {
            String query = "INSERT INTO tb_admin VALUES ('0','"+a.getUsername()+"', "
                    + "'"+a.getPassword()+"', '"+a.getNama()+"', '"+a.getAlamat()+"',"
                    + "'"+a.getNoTelp()+"', '"+a.getJenisKelamin()+"', '"+a.getRole()+"')";
            Statement s = con.createStatement();
            s.execute(query);
        } catch(SQLException se) {
            System.out.println(se);
        }
    }
    
    public ResultSet usernameExist(String username) {
        con = conn.getKoneksi();
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM tb_admin "
                    + "WHERE username = '"+username+"'";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return rs;
        }
    }
    
    public ArrayList<Transaksi> loadDataTransaksi() {
        con = conn.getKoneksi();
        ResultSet rs;
        try {
            String query = "SELECT * FROM tb_transaksi ORDER BY no DESC";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                String noTransaksi = rs.getString(2);
                String nama = rs.getString(3);
                String alamat = rs.getString(4);
                String noTelp = rs.getString(5);
                String jenisKelamin = rs.getString(6);
                String layanan = rs.getString(7);
                String status = rs.getString(8);
                String tanggal = rs.getString(9);
                Double berat = rs.getDouble(10);
                Double total = rs.getDouble(11);
                Transaksi a = new Transaksi(noTransaksi,nama,alamat,noTelp,
                        jenisKelamin,layanan,status,tanggal,berat,total);
                dafTransaksi.add(a);
            }
            System.out.println("DATA LOADED");
            return dafTransaksi;
        } catch (SQLException ex) {
            Logger.getLogger(HomeSuperAdmin.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
