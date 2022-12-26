package com.example.du_an_1_nhom9.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TaiKhoan {
    private String id;
    private String name;
    private String email;
    private String pass;
    private String repass;

    public TaiKhoan() {
    }

    public TaiKhoan(String id, String name, String email, String pass, String repass) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.repass = repass;
    }

    public TaiKhoan(String id, String name, String email, String pass) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }
    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("pass", pass);


        return result;
    }
}
