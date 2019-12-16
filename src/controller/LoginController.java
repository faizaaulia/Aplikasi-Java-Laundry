package controller;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.LoginModel;
import view.LoginView;

/** @author faizaaulia */

public class LoginController extends MouseAdapter implements ActionListener {
    LoginView view;
    LoginModel model;

    public LoginController() {
        view = new LoginView();
        model = new LoginModel();
        view.addActionListener(this);
        view.setVisible(true);
        view.setFeel();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if (source.equals(view.getBtnLogin())) {
            try {
                String username = view.getUsername();
                String password = view.getPassword();
                if (!username.equals("") && !password.equals("")) {
                    ResultSet rs = model.login(username,password);
                    if (rs.next()) {
                        view.dispose();
                        String nama = rs.getString(4);
                        String role = rs.getString(8);
                        if (role.equals("1"))
                            new HomeSuperAdminController(nama);
                        else 
                            new HomeAdminController(nama);
                    } else {
                        JOptionPane.showMessageDialog(view, "User tidak ditemukan", 
                            "Error", JOptionPane.WARNING_MESSAGE);
                        view.resetForm();
                    }
                } else
                    JOptionPane.showMessageDialog(view, "Username atau Password kosong!", 
                            "Error", JOptionPane.WARNING_MESSAGE);
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(view, "Tidak terhubung ke database", 
                            "Error", JOptionPane.WARNING_MESSAGE);
                System.out.println(e.getMessage());
            }
        }
        if (source.equals(view.getBtnExit()))
            view.dispose();
    }
}
