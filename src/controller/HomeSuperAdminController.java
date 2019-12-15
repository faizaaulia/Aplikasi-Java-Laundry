package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Admin;
import model.HomeSuperAdmin;
import model.Transaksi;
import view.DataAdminView;
import view.HomeSuperAdminView;
import view.SuperAdminDataTransaksi;

/** @author faizaaulia */

public class HomeSuperAdminController extends MouseAdapter implements ActionListener {
    HomeSuperAdminView superView;
    HomeSuperAdmin model;
    DataAdminView dataAdminView = new DataAdminView();;
    SuperAdminDataTransaksi transaksiView = new SuperAdminDataTransaksi();
    ArrayList<Transaksi> dafTransaksi = new ArrayList();
    
    public HomeSuperAdminController(String nama) {
        superView = new HomeSuperAdminView();
        model = new HomeSuperAdmin();
        superView.addActionListener(this);
        superView.setVisible(true);
        superView.setLabelHi(nama);
    }
    
    public void HomeDataAdmin() {
        //dataAdminView = new DataAdminView();
        model = new HomeSuperAdmin();
        dataAdminView.addActionListener(this);
        dataAdminView.setVisible(true);
    }
    
    public void DataTransaksi() {
        //transaksiView = new SuperAdminDataTransaksi();
        model = new HomeSuperAdmin();
        transaksiView.addActionListenet(this);
        transaksiView.setVisible(true);
    }
    
    public void showDataAdmin() {
        ArrayList<Admin> dafAdmin = model.loadDataAdmin();
        String kolom[] = {"No.", "Username", "Nama", "Alamat", 
            "No. Telp", "Jenis Kelamin", "Role"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
              }
        };
        for (int i = 0; i < dafAdmin.size(); i++) {
            String no = Integer.toString((i+1));
            String username = dafAdmin.get(i).getUsername();
            String nama = dafAdmin.get(i).getNama();
            String alamat = dafAdmin.get(i).getAlamat();
            String noTelp = dafAdmin.get(i).getNoTelp();
            String jk = dafAdmin.get(i).getJenisKelamin();
            String role;
            if (dafAdmin.get(i).getRole().equals("1"))
                role = "Super Admin";
            else
                role = "Admin";
            String data[] = {no,username,nama,alamat,noTelp,jk,role};
            dtm.addRow(data);
        }
        dataAdminView.getTableAdmin().setModel(dtm);
    }
    
    public void showDataTransaksi() {
        dafTransaksi = model.loadDataTransaksi();
        String kolom[] = {"noTransaksi", "Nama", "Alamat", "No Telp", 
            "Jenis Kelamin", "Layanan", "Status", "Tanggal", "Berat", "Total"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
              }
        };
        for (int i = 0; i < dafTransaksi.size(); i++) {
            String no = Integer.toString((i+1));
            String noTransaksi = dafTransaksi.get(i).getNoTransaksi();
            String nama = dafTransaksi.get(i).getNama();
            String alamat = dafTransaksi.get(i).getAlamat();
            String noTelp = dafTransaksi.get(i).getNoTelp();
            String JenisKelamin = dafTransaksi.get(i).getJenisKelamin();
            String Layanan = dafTransaksi.get(i).getLayanan();
            String Status = dafTransaksi.get(i).getStatus();
            String Tanggal = dafTransaksi.get(i).getTanggal();
            String Berat = Double.toString(dafTransaksi.get(i).getBerat());
            String Total = Double.toString(dafTransaksi.get(i).getTotal());
            
            String data[] = {noTransaksi,nama,alamat,noTelp,JenisKelamin,
                Layanan,Status,Tanggal,Berat,Total};
            dtm.addRow(data);
        }
        transaksiView.getTableTransaksi().setModel(dtm);
    }

    public void resetForm() {
        dataAdminView.getBgJK().clearSelection();
        dataAdminView.getCbRole().setSelectedIndex(0);
        dataAdminView.setTfNama("");
        dataAdminView.setTfAlamat("");
        dataAdminView.setTfNoTelp("");
        dataAdminView.setTfUsername("");
        dataAdminView.setTfPassword("");
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if (source.equals(superView.getBtnDataAdmin())) {
            superView.dispose();
            this.HomeDataAdmin();
            this.showDataAdmin();
        } else if (source.equals(superView.getBtnDataTransaksi())) {
            superView.dispose();
            this.DataTransaksi();
            this.showDataTransaksi();
        } else if (source.equals(dataAdminView.getBtnBack())) {
            dataAdminView.dispose();
            superView.setVisible(true);
        } else if(source.equals(transaksiView.getBtnBack())) {
            transaksiView.dispose();
            superView.setVisible(true);
        } else if (source.equals(dataAdminView.getBtnSimpan())) {
            String username = dataAdminView.getTfUsername();
            String password = dataAdminView.getTfPassword();
            String role = Integer.toString(dataAdminView.getCbRole().getSelectedIndex());
            String nama = dataAdminView.getTfNama();
            String alamat = dataAdminView.getTfAlamat();
            String noTelp = dataAdminView.getTfNoTelp();
            boolean bgNotNull = dataAdminView.getRadioLk().isSelected() || 
                    dataAdminView.getRadioPr().isSelected();
            boolean userExist = false;
            try {
                if (model.usernameExist(username).next())
                    userExist = true;
                else
                    userExist = false;
            } catch (SQLException ex) {
                Logger.getLogger(HomeSuperAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (username.equals("") || password.equals("") || nama.equals("")
                    || alamat.equals("") || noTelp.equals("") || !bgNotNull)
                JOptionPane.showMessageDialog(superView, "Lengkapi data", 
                            "Error", JOptionPane.WARNING_MESSAGE);
            else {
                if (username.length() < 5 || password.length() < 5)
                    JOptionPane.showMessageDialog(superView, "Username atau password invalid! (min. 5)", 
                            "Error", JOptionPane.WARNING_MESSAGE);
                else if (nama.length() < 3)
                    JOptionPane.showMessageDialog(superView, "Nama invalid!", 
                            "Error", JOptionPane.WARNING_MESSAGE);
                else if (alamat.length() < 3)
                    JOptionPane.showMessageDialog(superView, "Alamat invalid!", 
                            "Error", JOptionPane.WARNING_MESSAGE);
                else if (noTelp.length() < 12)
                    JOptionPane.showMessageDialog(superView, "No. Telp. invalid!", 
                            "Error", JOptionPane.WARNING_MESSAGE);
                else {
                    if (userExist)
                        JOptionPane.showMessageDialog(superView, "Username sudah ada!", 
                            "Error", JOptionPane.WARNING_MESSAGE);
                    else {
                        String jk = dataAdminView.getJK();
                        Admin a = new Admin(nama,alamat,noTelp,jk,username,password,role,dafTransaksi);
                        model.insertAdmin(a);
                        JOptionPane.showMessageDialog(superView, "Berhasil menambahkan " + nama, 
                                "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        resetForm();
                        showDataAdmin();
                    }
                }
            }
        } else if (source.equals(dataAdminView.getBtnReset())) 
            resetForm();
        else if (source.equals(superView.getBtnLogout())) {
            superView.dispose();
            new LoginController();
        }
    }
}