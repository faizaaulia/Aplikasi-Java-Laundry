package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author faizaaulia */

public class Koneksi {
    
    private Connection con;
    private final String url = "jdbc:mysql://localhost:3306/db_tubes_pbo";
    private final String username = "root";
    private final String password = "";
    
    public void connect() {
        try{
            con = DriverManager.getConnection(url,username,password);
            System.out.println("Connected to database");
        } catch(SQLException se) {
            System.out.println("Connection to database error! "+se);
        }
    }
    
    public Connection getKoneksi() {
        con = null;
        try {
            return con = DriverManager.getConnection(url,username,password);
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
            return con;
        }
    }
}
