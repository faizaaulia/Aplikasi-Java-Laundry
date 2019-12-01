package model;

/** @author faizaaulia */

public class Person {
    private String nama,alamat;
    private int noTelp;

    public Person(String nama, String alamat, int noTelp) {
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
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

    public int getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(int noTelp) {
        this.noTelp = noTelp;
    }
}