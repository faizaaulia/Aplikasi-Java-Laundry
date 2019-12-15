package model;

/** @author faizaaulia */

public class Transaksi {

    private String noTransaksi, idCust, layanan, status, tanggal;
    private double berat, total;

    public Transaksi(String noTransaksi, String idCust, String layanan, String status, 
            String tanggal, double berat, double total) {
        this.noTransaksi = noTransaksi;
        this.layanan = layanan;
        this.status = status;
        this.tanggal = tanggal;
        this.berat = berat;
        this.total = total;
        this.idCust = idCust;
    }

    public String getNoTransaksi() {
        return noTransaksi;
    }

    public void setNoTransaksi(String noTransaksi) {
        this.noTransaksi = noTransaksi;
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

    public String getIdCust() {
        return idCust;
    }

    public void setIdCust(String idCust) {
        this.idCust = idCust;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}