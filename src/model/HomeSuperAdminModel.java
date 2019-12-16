package model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author faizaaulia */

public class HomeSuperAdminModel {
    static ArrayList<Transaksi> dafTransaksi = new ArrayList();
    private Connection con;
    Koneksi conn;
    
    public HomeSuperAdminModel() {
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
            Logger.getLogger(HomeSuperAdminModel.class.getName()).
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
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            return rs;
        }
    }
    
    public ArrayList loadDataTransaksi() {
        ArrayList result = new ArrayList();
        ArrayList<Transaksi> dafTransaksi = new ArrayList();
        ArrayList<Person> dafPelanggan = new ArrayList();
        con = conn.getKoneksi();
        ResultSet rs;
        try {
            String query = "SELECT no_transaksi,id_cust,nama_cust,alamat_cust,no_telp_cust,"
                    + "jenisKel_cust,layanan,status,tanggal_transaksi,berat,"
                    + "total FROM tb_customer JOIN tb_transaksi USING (id_cust) "
                    + "ORDER BY tb_transaksi.no desc";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                String noTransaksi = rs.getString(1);
                String idCust = rs.getString(2);
                String nama = rs.getString(3);
                String alamat = rs.getString(4);
                String noTelp = rs.getString(5);
                String jenisKelamin = rs.getString(6);
                String layanan = rs.getString(7);
                String status = rs.getString(8);
                String tanggal = rs.getString(9);
                Double berat = rs.getDouble(10);
                Double total = rs.getDouble(11);
                Transaksi a = new Transaksi(noTransaksi,idCust,layanan,status,tanggal,berat,total);
                dafTransaksi.add(a);
                Person p = new Person(nama,alamat,noTelp,jenisKelamin);
                dafPelanggan.add(p);
            }
            result.add(dafTransaksi);
            result.add(dafPelanggan);
            System.out.println("DATA LOADED");
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(HomeSuperAdminModel.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet loadDataPelanggan() {
        con = conn.getKoneksi();
        ResultSet rs;
        try {
            String query = "SELECT nama_cust,alamat_cust,no_telp_cust,"
                    + "jenisKel_cust,SUM(total) AS 'Total Transaksi' "
                    + "FROM tb_customer JOIN tb_transaksi USING (id_cust) "
                    + "GROUP BY nama_cust,alamat_cust,no_telp_cust,jenisKel_cust";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            System.out.println("DATA LOADED");
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(HomeSuperAdminModel.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
