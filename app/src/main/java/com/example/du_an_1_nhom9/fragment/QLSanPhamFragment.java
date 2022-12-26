package com.example.du_an_1_nhom9.fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1_nhom9.R;
import com.example.du_an_1_nhom9.adapter.SanPhamAdapter;
import com.example.du_an_1_nhom9.adapter.SanPhamAdapterListener;
import com.example.du_an_1_nhom9.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class QLSanPhamFragment extends Fragment {
    int SELECT_PICTURE_CREATE = 200;
    int SELECT_PICTURE_UPDATE = 100;

    ImageView imgPhotoAdd;
    ImageView imgPhotoUpdate;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private SanPhamAdapter sanPhamAdapter;
    private List<SanPham> list;

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(requireContext());
        mProgressDialog.setMessage(getString(R.string.msg_retriveing_data));
        mProgressDialog.setCancelable(false);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_qlsanpham,container,false);
        getListDatabase();
        setupRecyclerView(view);
        onListener(view);

        return view;
    }

    private void onListener(View view) {
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatAdd);
        floatingActionButton.setOnClickListener(v -> showDialogAddSanPham());
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerQLSanPham);
        LinearLayoutManager linearLayout = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayout);
        list = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(list,getContext());
        recyclerView.setAdapter(sanPhamAdapter);
        sanPhamAdapter.setListener(new SanPhamAdapterListener() {
            @Override
            public void onItemClicked(SanPham sp) {
                showDialogUpdateSanPham(sp);
            }
        });

    }

    //lấy dữ liệu từ firebase
    private void getListDatabase(){
        mProgressDialog.show();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("SanPham");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mProgressDialog.dismiss();
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                    list.add(sanPham);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null){
                if(requestCode == SELECT_PICTURE_CREATE){
                    imgPhotoAdd.setImageURI(selectedImageUri);
                }else{
                    imgPhotoUpdate.setImageURI(selectedImageUri);
                }

            }

        }

    }

    private void showDialogAddSanPham(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sanpham,null);
        builder.setView(view);

        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        ImageView imgchonanh = view.findViewById(R.id.img_book_edit);
        imgPhotoAdd = view.findViewById(R.id.imageViewThem);


        //khi ấn vào thêm ảnh sẽ đưa đến thư viện để chọn ảnh
        imgchonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_PICTURE_CREATE);
            }
        });

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database = FirebaseDatabase.getInstance();
                mDatabase = database.getReference();

                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgPhotoAdd.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                themSanPham(edtTenSP.getText().toString(), Long.parseLong(edtGiaSP.getText().toString()),encodedImage);


            }


        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void themSanPham(String tenSp , long giaSp, String hinhanh) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String id = mDatabase.push().getKey();
        SanPham sanPham = new SanPham(id, tenSp, giaSp, hinhanh);
        mDatabase.child("SanPham").child(id).setValue(sanPham);
        list.clear();
        sanPhamAdapter.notifyDataSetChanged();

    }

    private void showDialogUpdateSanPham(SanPham sanPham) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_sanpham,null);
        builder.setView(view);

        EditText edtTen = view.findViewById(R.id.sua_edtTenSP);
        EditText edtGia = view.findViewById(R.id.sua_edtGiaSP);
        Button btnChonAnh = view.findViewById(R.id.sua_btnHinhAnh);
        imgPhotoUpdate = view.findViewById(R.id.sua_imageView);

        edtTen.setText(sanPham.getTensp());
        edtGia.setText(sanPham.getGiasp()+"");

        String hinhanh = sanPham.getHinhanh();
        byte[] anh = Base64.decode(hinhanh, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(anh,0,anh.length);
        imgPhotoUpdate.setImageBitmap(bitmap);

        //đưa đến thư viện ảnh
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_PICTURE_UPDATE);
            }
        });

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                database = FirebaseDatabase.getInstance();
                mDatabase = database.getReference("SanPham");

                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgPhotoUpdate.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                sanPham.setTensp(edtTen.getText().toString());
                sanPham.setGiasp(Long.parseLong(edtGia.getText().toString()));
                sanPham.setHinhanh(encodedImage);

                mDatabase.child(sanPham.getId()).updateChildren(sanPham.toMap());

            }

        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
