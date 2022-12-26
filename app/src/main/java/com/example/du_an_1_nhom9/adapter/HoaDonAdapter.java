package com.example.du_an_1_nhom9.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1_nhom9.R;
import com.example.du_an_1_nhom9.model.GioHang;

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private List<GioHang> list;

    public HoaDonAdapter(List<GioHang> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = list.get(position);
        holder.txt_hoTen.setText("Họ tên:"+gioHang.getHoTen());
        holder.txt_email.setText("Email:"+gioHang.getEmail());
        holder.txt_sdt.setText("Số điện thoại:"+gioHang.getSdt());
        holder.txt_diaChi.setText("Địa chỉ:"+gioHang.getDiaChi());
        holder.txt_tenLaptop.setText("Tên Laptop:"+gioHang.getLaptopArrayList());
        holder.txt_gia.setText("Giá:");
        holder.txt_soLuong.setText("Số lượng:");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_hoTen, txt_email, txt_sdt, txt_diaChi, txt_tenLaptop, txt_gia, txt_soLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_hoTen = itemView.findViewById(R.id.txt_hoTen);
            txt_email = itemView.findViewById(R.id.txt_email);
            txt_sdt = itemView.findViewById(R.id.txt_sdt);
            txt_diaChi = itemView.findViewById(R.id.txt_diaChi);
            txt_tenLaptop = itemView.findViewById(R.id.txt_tenLaptop);
            txt_gia = itemView.findViewById(R.id.txt_gia);
            txt_soLuong = itemView.findViewById(R.id.txt_soLuong);
        }
    }
}
