package com.example.appqr.model;

public class HistoryQR {
    private int id;
    private String username;
    private  String tenkh;
    private String sdt;
    private String noidung;

    public HistoryQR(String username, String tenkh, String sdt, String noidung) {
        this.username = username;
        this.tenkh = tenkh;
        this.sdt = sdt;
        this.noidung = noidung;
    }

    public HistoryQR() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
