package com.example.appqr.model;

import com.google.gson.annotations.SerializedName;

public class Account {
    private int IdKH;


    private String username;

    private String passwork;

    public Account(String username, String passwork) {
        this.username = username;
        this.passwork = passwork;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswork() {
        return passwork;
    }

    public void setPasswork(String passwork) {
        this.passwork = passwork;
    }
}

