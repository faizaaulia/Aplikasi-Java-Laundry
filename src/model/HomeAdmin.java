package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author faizaaulia */

public class HomeAdmin {
    
    private Connection con;
    Koneksi conn;
    
    public HomeAdmin() {
        conn = new Koneksi();
        conn.connect();
    }
    
    public int getLastID() {
        con = conn.getKoneksi();
        ResultSet rs;
        try {
            String query = "SELECT COUNT(no_transaksi) AS lastID FROM tb_transaksi";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            if (rs.next())
                return rs.getInt("lastID");
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdmin.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }
    
    public void insertTransaksi(Transaksi a){
        try {
            String query = "INSERT INTO tb_transaksi VALUES ('"+a.getNoTransaksi()+"',"
                    + "'"+a.getNama()+"','"+a.getAlamat()+"','"+a.getNoTelp()+"',"
                    + "'"+a.getJenisKelamin()+"','"+a.getLayanan()+"','"+a.getStatus()+"',"
                    + "'"+a.getTanggal()+"','"+a.getBerat()+"','"+a.getTotal()+"')";
            Statement s = con.createStatement();
            s.execute(query);
        } catch(SQLException se){
            System.out.println(se);
        }
    }
    
    public ArrayList<Transaksi> loadDataTransaksi() {
        ArrayList<Transaksi> dafTransaksi = new ArrayList();
        con = conn.getKoneksi();
        ResultSet rs;
        try {
            String query = "SELECT * FROM tb_transaksi ORDER BY no_transaksi DESC";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                String noTransaksi = rs.getString(1);
                String nama = rs.getString(2);
                String alamat = rs.getString(3);
                String noTelp = rs.getString(4);
                String jenisKelamin = rs.getString(5);
                String layanan = rs.getString(6);
                String status = rs.getString(7);
                String tanggal = rs.getString(8);
                Double berat = rs.getDouble(9);
                Double total = rs.getDouble(10);
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
    
    public ResultSet getTransaksi(String no) {
        con = conn.getKoneksi();
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM tb_transaksi WHERE no_transaksi = '"+no+"'";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdmin.class.getName()).log(Level.SEVERE, null, ex);
            return rs;
        }
    }
    
    public void updateTransaksi(String no) {
        try {
            String query = "UPDATE tb_transaksi SET status = 'Lunas' "
                    + "WHERE no_transaksi = '"+no+"'";
            System.out.println(query);
            Statement s = con.createStatement();
            s.execute(query);
        } catch(SQLException se){
            System.out.println(se);
        }
    }
}
