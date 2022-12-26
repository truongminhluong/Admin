package com.example.du_an_1_nhom9.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GioHang {
    public String id;
    public String hoTen, email, sdt, diaChi;
    //public Date created, updated;
    ArrayList<SanPham> laptopArrayList;

    public GioHang() {
    }

    public GioHang(String id, String hoTen, String email, String sdt, String diaChi, ArrayList<SanPham> laptopArrayList) {
        this.id = id;
        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.laptopArrayList = laptopArrayList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public ArrayList<SanPham> getLaptopArrayList() {
        return laptopArrayList;
    }

    public void setLaptopArrayList(ArrayList<SanPham> laptopArrayList) {
        this.laptopArrayList = laptopArrayList;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("hoTen", hoTen);
        result.put("email", email);
        result.put("sdt", sdt);
        result.put("diaChi", diaChi);

        ArrayList<HashMap<String, Object>> listSp = new ArrayList<>();
        for (SanPham laptop: laptopArrayList){
            HashMap<String, Object> object = new HashMap<>();

            object.put("id", laptop.getId());
            object.put("tenLaptop", laptop.getId());
            object.put("HinhAnh", laptop.getId());
            object.put("giaLaptop", laptop.getId());

            listSp.add(object);
        }
        result.put("Laptop", listSp);
        return result;
    }

}
