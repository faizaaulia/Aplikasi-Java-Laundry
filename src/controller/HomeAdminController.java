/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HomeAdmin;
import model.Transaksi;
import view.HomeAdminView;
import view.UpdateStatusTransaksiView;

/**
 *
 * @author faizaaulia
 */
public class HomeAdminController extends MouseAdapter implements ActionListener {
    HomeAdminView view;
    HomeAdmin model;
    UpdateStatusTransaksiView transaksiView;
    Transaksi b;
    private String no;
    
    public HomeAdminController(String nama) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYY");
        Date date = new Date();
        String tgl = dateFormat.format(date);
        view = new HomeAdminView();
        model = new HomeAdmin();
        view.addActionListener(this);
        view.setVisible(true);
        view.setLabelHi(nama);
        view.setLabelTanggal(tgl);
        showDataTransaksi();
    }

    public Transaksi cek(Transaksi b) {
        String nama = view.getTfNama();
        String alamat = view.getTfAlamat();
        String noTelp = view.getTfNoHp();
        boolean bgNotNull = view.getRadioLk().isSelected() || view.getRadioPr().isSelected();
        String berat = view.getTfBerat();
        double x = Double.parseDouble(berat);
        String layanan = view.getCbLayanan().getSelectedItem().toString();
        String pembayaran = view.getCbBayar().getSelectedItem().toString();
        double total=0;
        int inLayanan = view.getCbLayanan().getSelectedIndex();
        switch (inLayanan){
            case 0 : total = x*6000; break;
            case 1 : total = x*8000; break;
            case 2 : total = x*10000; break;
            case 3 : total = x*12000; break;
        }
        view.setTfTotal(Double.toString(total));
        if (nama.equals("") || alamat.equals("") || noTelp.equals("") || berat.equals("") || !bgNotNull) {
            JOptionPane.showMessageDialog(view, "Lengkapi data", "Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        else {
            if (nama.length() < 3) {
                JOptionPane.showMessageDialog(view, "Nama invalid!", 
                    "Error", JOptionPane.WARNING_MESSAGE); 
                return null;
            }
            else if (alamat.length() < 3) {
                JOptionPane.showMessageDialog(view, "Alamat invalid!",
                    "Error", JOptionPane.WARNING_MESSAGE); 
                return null;
            }
            else if (noTelp.length() < 12 && noTelp.length() > 12) {
                JOptionPane.showMessageDialog(view, "No. Telp. invalid!", 
                    "Error", JOptionPane.WARNING_MESSAGE); 
                return null;
            }
            else {
                String jk = view.getBgJK().getSelection().getActionCommand();
                String noTransaksi = "TR00" + (model.getLastID()+1);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                String tglOrder = dateFormat.format(date);
                return b = new Transaksi(noTransaksi,nama,alamat,noTelp,jk,layanan,pembayaran,tglOrder,x,total);
            }
        }
    }
    
    public void showDataTransaksi() {
        ArrayList<Transaksi> dafTransaksi = model.loadDataTransaksi();
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
        view.getTableTransaksi().setModel(dtm);
    }
    
    public void loadTransaksi(String no) {
        try {
            String find = view.getTfCari();
            ResultSet rs = model.getTransaksi(find);
            if (rs.next()) {
                transaksiView = new UpdateStatusTransaksiView();
                transaksiView.setVisible(true);
                transaksiView.setLabelNamanya(rs.getString(2));
                transaksiView.setLabelAlamatnya(rs.getString(3));
                transaksiView.setLabelTelpnya(rs.getString(4));
                transaksiView.setLabelKelaminnya(rs.getString(5));
                transaksiView.setLabelLayanannya(rs.getString(6));
                transaksiView.setLabelStatusnya(rs.getString(7));
                transaksiView.setLabelTanggalnya(rs.getString(8));
                transaksiView.setLabelBeratnya(rs.getString(9));
                transaksiView.setLabelTotalnya(rs.getString(10));
                if (rs.getString(7).equals("Belum Lunas")) {
                    transaksiView.getTfUpdate().setVisible(true);
                    transaksiView.getButtonUpdate().setVisible(true);
                    transaksiView.addActionListener(this);
                    no = rs.getString(1);
                }
            } else
                JOptionPane.showMessageDialog(view, "No. Transaksi tidak ada!",
                    "Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source.equals(view.getBtnHitung())) {
            cek(b);
        } else if (source.equals(view.getBtnTambah())) {
            if (cek(b) != null) {
                model.insertTransaksi(cek(b));
                JOptionPane.showMessageDialog(view, "Berhasil menambahkan transaksi baru", 
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                showDataTransaksi();
            }
        } else if (source.equals(view.getBtnLogout())) {
            view.dispose();
            new LoginController();
        } else if (source.equals(view.getBtnCari())) {
            loadTransaksi(no);
        } else if (source.equals(transaksiView.getButtonUpdate())) {
            model.updateTransaksi(no);
            JOptionPane.showMessageDialog(view, "Berhasil mengubah status", 
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            transaksiView.dispose();
            loadTransaksi(no);
            showDataTransaksi();
        }
    }
}
