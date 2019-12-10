package model;

/** @author faizaaulia */

public class Transaksi {

    private String noTransaksi, nama, alamat, noTelp, JenisKelamin, layanan, status, tanggal;
    private double berat, total;

    public Transaksi(String noTransaksi, String nama, String alamat, 
            String noTelp, String JenisKelamin, String layanan, String status, 
            String tanggal, double berat, double total) {
        this.noTransaksi = noTransaksi;
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.JenisKelamin = JenisKelamin;
        this.layanan = layanan;
        this.status = status;
        this.tanggal = tanggal;
        this.berat = berat;
        this.total = total;
    }

    public String getNoTransaksi() {
        return noTransaksi;
    }

    public void setNoTransaksi(String noTransaksi) {
        this.noTransaksi = noTransaksi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getJenisKelamin() {
        return JenisKelamin;
    }

    public void setJenisKelamin(String JenisKelamin) {
        this.JenisKelamin = JenisKelamin;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}