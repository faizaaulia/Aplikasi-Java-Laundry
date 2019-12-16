package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author faizaaulia */

public class LoginModel {
    private Connection con;
    Koneksi conn;
    
    public LoginModel() {
        conn = new Koneksi();
        conn.connect();
    }
    
    public ResultSet login(String username,String password) {
        con = conn.getKoneksi();
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM tb_admin "
                    + "WHERE username = '"+username+"' AND password = '"+password+"'";
            Statement s = con.createStatement();
            rs = s.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            return rs;
        }
    }
}
