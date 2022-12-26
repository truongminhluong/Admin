package com.example.du_an_1_nhom9.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1_nhom9.R;
import com.example.du_an_1_nhom9.model.SanPham;
import com.example.du_an_1_nhom9.utils.AppUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private List<SanPham> list;
    private Context context;


    SanPhamAdapterListener listener;
    XoaSanPham xoaSanPham;

    public SanPhamAdapter(List<SanPham> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);

        if (sanPham == null){
            return;
        }

        //chuyển đổi chuỗi Base64 thành hình ảnh Bitmap
        String hinhanh = sanPham.getHinhanh();
        byte[] anh = Base64.decode(hinhanh, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(anh,0,anh.length);

        holder.imgHinhAnh.setImageBitmap(bitmap);
        holder.txtTenSp.setText(sanPham.getTensp());
        String txtPrice = AppUtils.formatNumber(sanPham.getGiasp()) + " " + AppUtils.getCurrencySymbol();
        holder.txtGiaSp.setText(txtPrice);

        //sửa sản phẩm
        holder.btnUpdate.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClicked(sanPham);
            }
        });

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogDeleteSanPham(sanPham);
            }
        });
    }

    private void showDiaLogDeleteSanPham(SanPham sanPham){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("RealTime DataBase").setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không");
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SanPham");
                reference.child(sanPham.getId()).removeValue();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setListener(SanPhamAdapterListener listener) {
        this.listener = listener;
    }

    public void setXoaSanPham(XoaSanPham xoaSanPham) {
        this.xoaSanPham = xoaSanPham;
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSp, txtGiaSp;
        ImageView imgHinhAnh;
        Button btnUpdate, btnDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnh = (ImageView) itemView.findViewById(R.id.item_Anh);
            txtTenSp = (TextView) itemView.findViewById(R.id.item_Tensp);
            txtGiaSp = (TextView) itemView.findViewById(R.id.item_Giasp);
            btnUpdate = (Button) itemView.findViewById(R.id.item_btnUpdate);
            btnDel = (Button) itemView.findViewById(R.id.item_btnDel);
        }
    }

}
