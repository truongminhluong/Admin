package com.example.du_an_1_nhom9.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.du_an_1_nhom9.R;
import com.example.du_an_1_nhom9.adapter.HoaDonAdapter;
import com.example.du_an_1_nhom9.model.GioHang;
import com.example.du_an_1_nhom9.model.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HoaDonFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<GioHang> list;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private HoaDonAdapter hoaDonAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        getListDatabase();
        setupRecyclerView(view);
        return view;
    }
    private void setupRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recyclerHoaDon);
        LinearLayoutManager linearLayout = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(linearLayout);
        list = new ArrayList<>();
        hoaDonAdapter = new HoaDonAdapter(list);
        recyclerView.setAdapter(hoaDonAdapter);
    }
    private void getListDatabase(){
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("GioHang");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    GioHang gioHang = dataSnapshot.getValue(GioHang.class);
                    list.add(gioHang);
                }
                hoaDonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}