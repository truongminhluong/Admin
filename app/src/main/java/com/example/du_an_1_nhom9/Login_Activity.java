package com.example.du_an_1_nhom9;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.du_an_1_nhom9.model.SanPham;
import com.example.du_an_1_nhom9.model.TaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Login_Activity extends AppCompatActivity {

    private EditText edt_email, edt_pass;
    private TextView txt_taotk;
    private AppCompatButton btn_login;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    private DatabaseReference mDataBase;
    private List<TaiKhoan> list;
    private TaiKhoan taiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getListDatabase();
        setupUI();
        onListener();
    }
    private void setupUI(){
        edt_email =  findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        txt_taotk = findViewById(R.id.txt_taotaikhoan);
        btn_login = findViewById(R.id.btn_login);
        dialog = new ProgressDialog(this);
    }
    private void onListener(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString();
                String pass = edt_pass.getText().toString();
                if (email.equals("")){
                    Toast.makeText(Login_Activity.this, "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
                }else if (pass.equals("")){
                    Toast.makeText(Login_Activity.this, "Bạn chưa nhập password", Toast.LENGTH_SHORT).show();
                }else {
                    onClickLogin();
                }



                

            }
        });

        txt_taotk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Signup_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickLogin() {
        String email = edt_email.getText().toString().trim();
        String password = edt_pass.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                            Toast.makeText(Login_Activity.this, "Đănh nhập thành công",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login_Activity.this, "Đănh nhập thất bại!!!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

//    private void getListDatabase(){
//
//
//        mDataBase = FirebaseDatabase.getInstance().getReference("TaiKhoanAdmin");
//        mDataBase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
//                    TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                Toast.makeText(Login_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}