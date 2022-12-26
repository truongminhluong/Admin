package com.example.du_an_1_nhom9.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SanPham {
    private String id;
    private String tensp;
    private long giasp;
    private String hinhanh;

    public SanPham() {
    }

    public SanPham(String id, String tensp, long giasp, String hinhanh) {
        this.id = id;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanh = hinhanh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }


    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("tensp", tensp);
        result.put("giasp", giasp);
        result.put("hinhanh", hinhanh);

        return result;
    }
}
