/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HomeAdmin;
import model.Transaksi;
import view.HomeAdminView;

/**
 *
 * @author faizaaulia
 */
public class HomeAdminController extends MouseAdapter implements ActionListener {
    HomeAdminView view;
    HomeAdmin model;
    Transaksi b;
    
    public HomeAdminController(String nama) {
        view = new HomeAdminView();
        model = new HomeAdmin();
        view.addActionListener(this);
        view.setVisible(true);
        view.setLabelHi(nama);
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
        String kolom[] = {"noTransaksi", "Nama", "Alamat", "No Telp", "Jenis Kelamin", "Layanan", "Status", "Tanggal", "Berat", "Total"};
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
            
            String data[] = {noTransaksi,nama,alamat,noTelp,JenisKelamin,Layanan,Status,Tanggal,Berat,Total};
            dtm.addRow(data);
        }
        view.getTableTransaksi().setModel(dtm);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source.equals(view.getBtnHitung())) {
            cek(b);
        } else if (source.equals(view.getBtnTambah())) {
            if (cek(b) != null)
                model.insertTransaksi(cek(b));
            JOptionPane.showMessageDialog(view, "Berhasil menambahkan transaksi baru", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            showDataTransaksi();
        } else if (source.equals(view.getBtnLogout())) {
            view.dispose();
            new LoginController();
        }
    }
}
