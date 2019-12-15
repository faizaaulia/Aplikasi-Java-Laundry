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
import model.Person;
import model.Transaksi;
import view.CariPelangganView;
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
    CariPelangganView cariPelangganView;
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
        String layanan = view.getCbLayanan().getSelectedItem().toString();
        String pembayaran = view.getCbBayar().getSelectedItem().toString();
        double total=0;
        int inLayanan = view.getCbLayanan().getSelectedIndex();
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
                double x = Double.parseDouble(berat);
                switch (inLayanan){
                    case 0 : total = x*6000; break;
                    case 1 : total = x*8000; break;
                    case 2 : total = x*10000; break;
                    case 3 : total = x*12000; break;
                }
                view.setTfTotal(Double.toString(total));
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
        String kolom[] = {"No Transaksi", "Nama", "Alamat", "No Telp", 
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
            String Berat = Double.toString(dafTransaksi.get(i).getBerat()) + " Kg";
            String Total = Double.toString(dafTransaksi.get(i).getTotal());
            
            String data[] = {noTransaksi,nama,alamat,noTelp,JenisKelamin,
                Layanan,Status,Tanggal,Berat,Total};
            dtm.addRow(data);
        }
        view.getTableTransaksi().setModel(dtm);
    }
    
    public void loadTransaksi() {
        String find = view.getTfCari();
        try {
            ResultSet rs = model.getTransaksi(find);
            if (rs.next()) {
                transaksiView = new UpdateStatusTransaksiView();
                transaksiView.addActionListener(this);
                transaksiView.setVisible(true);
                transaksiView.setLabelNamanya(rs.getString(3));
                transaksiView.setLabelAlamatnya(rs.getString(4));
                transaksiView.setLabelTelpnya(rs.getString(5));
                transaksiView.setLabelKelaminnya(rs.getString(6));
                transaksiView.setLabelLayanannya(rs.getString(7));
                transaksiView.setLabelStatusnya(rs.getString(8));
                transaksiView.setLabelTanggalnya(rs.getString(9));
                transaksiView.setLabelBeratnya(rs.getString(10));
                transaksiView.setLabelTotalnya(rs.getString(11));
                if (rs.getString(8).equals("Belum Lunas")) {
                    transaksiView.getBtnUpdate().setVisible(true);
                    no = find;
                }
            } else
                JOptionPane.showMessageDialog(view, "No. Transaksi tidak ada!",
                    "Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchCust() {
        String searchBy = view.gettfCariPelanggan();
        int idx = view.getComboCari().getSelectedIndex();
        switch (idx) {
            case 0 : {
                if (searchBy.equals(""))
                    JOptionPane.showMessageDialog(view, "Masukan invalid!",
                        "Error", JOptionPane.WARNING_MESSAGE);
                else {
                    ArrayList<String> idCust = new ArrayList();
                    ArrayList<Person> dafPelanngan = model.loadDataPelanggan(searchBy,idCust);
                    if (dafPelanngan.size() == 0) {
                        JOptionPane.showMessageDialog(view, "Pelanggan " + searchBy + " tidak ada!",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        cariPelangganView = new CariPelangganView();
                        cariPelangganView.setVisible(true);
                        cariPelangganView.addActionListenter(this);
                        String kolom[] = {"No.","ID Cust", "Nama", "Alamat", "No Telp", "Jenis Kelamin"};
                        DefaultTableModel dtm = new DefaultTableModel(null, kolom) {
                            @Override
                            public boolean isCellEditable(int rowIndex, int mColIndex) {
                                return false;
                              }
                        };
                        for (int i = 0; i < dafPelanngan.size(); i++) {
                            String no = Integer.toString((i+1));
                            String id = idCust.get(i);
                            String nama = dafPelanngan.get(i).getNama();
                            String alamat = dafPelanngan.get(i).getAlamat();
                            String noTelp = dafPelanngan.get(i).getNoTelp();
                            String JenisKelamin = dafPelanngan.get(i).getJenisKelamin();

                            String data[] = {no,id,nama,alamat,noTelp,JenisKelamin};
                            dtm.addRow(data);
                        }
                        cariPelangganView.getTableCariPelanggan().setModel(dtm);
                    }
                }
                break;
            }
            case 1 : {
                if (searchBy.equals(""))
                    JOptionPane.showMessageDialog(view, "Masukan invalid!",
                        "Error", JOptionPane.WARNING_MESSAGE);
                else {
                    ResultSet rs = model.serachByID(searchBy);
                    try {
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(view, "Menambahkan pelanggan ke transaksi", 
                                "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            view.setLabelIdCust(rs.getString(2));
                            view.setTfNama(rs.getString(3));
                            view.setTfAlamat(rs.getString(4));
                            view.setTfNoHp(rs.getString(5));
                            if (rs.getString(6).equals("Laki - Laki"))
                                view.getRadioLk().setSelected(true);
                            else
                                view.getRadioPr().setSelected(true);
                        } else
                            JOptionPane.showMessageDialog(view, "Pelanggan ID " + searchBy + " tidak ada!",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeAdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source.equals(view.getBtnHitung())) {
            cek(b);
        } else if (source.equals(view.getBtnTambah())) {
            if (cek(b) != null) {
                if (view.getLabelIdCust().equals("null")) {
                    String nama = view.getTfNama();
                    String alamat = view.getTfAlamat();
                    String no = view.getTfNoHp();
                    String jenisKelamin = view.getBgJK().getSelection().getActionCommand();
                    Person p = new Person(nama,alamat,no,jenisKelamin);
                    model.insertCust(p);
                }
                view.setLabelIdCust("null");
                model.insertTransaksi(cek(b));
                JOptionPane.showMessageDialog(view, "Berhasil menambahkan transaksi baru", 
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                showDataTransaksi();
            }
        } else if (source.equals(view.getBtnLogout())) {
            view.dispose();
            new LoginController();
        } else if (source.equals(view.getBtnCari())) {
            loadTransaksi();
        } else if (source.equals(view.getBtnCariPelanggan())) {
            searchCust();
        } else if (source.equals(view.getBtnReset())) {
            view.setTfNama("");
            view.setTfAlamat("");
            view.setTfNoHp("");
            view.getBgJK().clearSelection();
            view.setTfBerat("");
            view.getCbLayanan().setSelectedIndex(0);
            view.setTfTotal("");
            view.getCbBayar().setSelectedIndex(0);
        } else if (source.equals(cariPelangganView.getBtnBackCariPelanggan())) {
            cariPelangganView.dispose();
        } else if (source.equals(cariPelangganView.getBtnAdd())) {
            int baris = cariPelangganView.getTableCariPelanggan().getSelectedRow();
            if (baris == -1) {
                JOptionPane.showMessageDialog(cariPelangganView, "Pilih pelanggan terlebih dahulu!",
                        "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                view.setLabelIdCust(cariPelangganView.getTableCariPelanggan().
                        getValueAt(baris, 1).toString());
                view.setTfNama(cariPelangganView.getTableCariPelanggan().
                        getValueAt(baris, 2).toString());
                view.setTfAlamat(cariPelangganView.getTableCariPelanggan().
                        getValueAt(baris, 3).toString());
                view.setTfNoHp(cariPelangganView.getTableCariPelanggan().
                        getValueAt(baris, 4).toString());
                if (cariPelangganView.getTableCariPelanggan().getValueAt(baris, 5).
                        toString().equals("Laki - Laki"))
                    view.getRadioLk().setSelected(true);
                else
                    view.getRadioPr().setSelected(true);
                cariPelangganView.dispose();
            }
        } else if (source.equals(transaksiView.getBtnUpdate())) {
            model.updateTransaksi(no);
            JOptionPane.showMessageDialog(view, "Berhasil mengubah status", 
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            transaksiView.dispose();
            loadTransaksi();
            showDataTransaksi();
        } else if (source.equals(transaksiView.getBtnBack())) {
            transaksiView.dispose();
        }
    }
}