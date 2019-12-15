package model;

/** @author faizaaulia */

public class Customer extends Person {
    
    private String idCust;
    
    public Customer(String nama, String alamat, String noTelp, String jenisKelamin,String idCust) {
        super(nama, alamat, noTelp, jenisKelamin);
        this.idCust = idCust;
    }

    public String getIdCust() {
        return idCust;
    }

    public void setIdCust(String idCust) {
        this.idCust = idCust;
    }
}