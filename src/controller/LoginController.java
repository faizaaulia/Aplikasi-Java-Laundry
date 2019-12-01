package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import model.Login;
import view.LoginView;

/** @author faizaaulia */

public class LoginController extends MouseAdapter implements ActionListener {
    LoginView view;
    Login model;

    public LoginController() {
        view = new LoginView();
        model = new Login();
        view.addActionListener(this);
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if (source.equals(view.getBtnLogin())) {
            try {
                String username = view.getUsername();
                String password = view.getPassword();
                if (!username.equals("") && !password.equals("")) {
                    ResultSet rs = model.login(username, password);
                    if (rs.next()) {
                        view.dispose();
                        String nama = rs.getString(2);
                        new HomeController(nama);
                    } else {
                        JOptionPane.showMessageDialog(view, "User tidak ditemukan", 
                            "Error", JOptionPane.WARNING_MESSAGE);
                        view.resetForm();
                    }
                } else
                    JOptionPane.showMessageDialog(view, "Username atau Password kosong!", 
                            "Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (source.equals(view.getBtnExit()))
            view.dispose();
    }
}
