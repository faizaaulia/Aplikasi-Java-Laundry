package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author faizaaulia */

public class Login {
    private Connection con;
    Koneksi conn;
    
    public Login() {
        conn = new Koneksi();
        conn.connect();
    }
    
    public ResultSet login(String username,String password) {
        con = conn.getKoneksi();
        ResultSet rs = null;
        try {
            String query = "SELECT username, password FROM tb_admin "
                    + "WHERE username = '"+username+"' AND password = '"+password+"'";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return rs;
        }
    }
    
    
}
