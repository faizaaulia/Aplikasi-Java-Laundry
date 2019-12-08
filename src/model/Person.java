package model;

/** @author faizaaulia */

public class Person {
    private String nama,alamat,noTelp,jenisKelamin;

    public Person(String nama,String alamat,String noTelp,String jenisKelamin) {
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.jenisKelamin = jenisKelamin;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
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
}