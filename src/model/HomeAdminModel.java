package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author faizaaulia */

public class HomeAdminModel {
    
    private Connection con;
    Koneksi conn;
    
    public HomeAdminModel() {
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
            Logger.getLogger(HomeAdminModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }
    
    public void insertTransaksi(Transaksi a){
        int no = getLastID()+1;
        try {
            String query = "INSERT INTO tb_transaksi VALUES ('"+no+"',"
                    + "'"+a.getNoTransaksi()+"','"+a.getIdCust()+"','"+a.getLayanan()+"'"
                    + ",'"+a.getStatus()+"','"+a.getTanggal()+"','"+a.getBerat()+"','"+a.getTotal()+"')";
            Statement s = con.createStatement();
            s.execute(query);
        } catch(SQLException se){
            System.out.println(se);
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
                Person p = new Person(nama,alamat,noTelp,jenisKelamin);
                dafPelanggan.add(p);
                dafTransaksi.add(a);
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
    
    public ResultSet getTransaksi(String no) {
        con = conn.getKoneksi();
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM tb_transaksi WHERE no_transaksi = '"+no+"'";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdminModel.class.getName()).log(Level.SEVERE, null, ex);
            return rs;
        }
    }
    
    public void updateTransaksi(String no) {
        try {
            String query = "UPDATE tb_transaksi SET status = 'Lunas' WHERE no_transaksi = '"+no+"'";
            Statement s = con.createStatement();
            s.execute(query);
        } catch(SQLException se){
            System.out.println(se);
        }
    }
    
    public ResultSet serachByID(String find) {
        con = conn.getKoneksi();
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM tb_customer WHERE id_cust = '"+find+"'";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            return rs;
        }
    }
    
    public ArrayList<Customer> loadDataPelanggan(String find) {
        ArrayList<Customer> dafPelanggan = new ArrayList();
        con = conn.getKoneksi();
        ResultSet rs;
        try {
            String query = "SELECT * FROM tb_customer WHERE nama_cust = '"+find+"' ORDER BY no DESC";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString(2);
                String nama = rs.getString(3);
                String alamat = rs.getString(4);
                String noTelp = rs.getString(5);
                String jenisKelamin = rs.getString(6);
                System.out.println(id);
                Customer c = new Customer(nama,alamat,noTelp,jenisKelamin,id);
                dafPelanggan.add(c);
            }
            System.out.println("DATA LOADED");
            return dafPelanggan;
        } catch (SQLException ex) {
            Logger.getLogger(HomeSuperAdminModel.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public int getLastIdCust() {
        con = conn.getKoneksi();
        ResultSet rs;
        try {
            String query = "SELECT COUNT(id_cust) AS lastID FROM tb_customer";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            if (rs.next())
                return rs.getInt("lastID");
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdminModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }
    
    public void insertCust(Customer c) {
        int no = getLastIdCust()+1;
        try {
            String query = "INSERT INTO tb_customer VALUES ('"+no+"','"+c.getIdCust()+"',"
                    + "'"+c.getNama()+"','"+c.getAlamat()+"','"+c.getNoTelp()+"',"
                    + "'"+c.getJenisKelamin()+"')";
            Statement s = con.createStatement();
            s.execute(query);
        } catch(SQLException se){
            System.out.println(se);
        }
    }
}
