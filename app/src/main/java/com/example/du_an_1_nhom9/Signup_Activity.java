package com.example.du_an_1_nhom9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.du_an_1_nhom9.model.TaiKhoan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Activity extends AppCompatActivity {
    EditText edt_name, edt_email, edt_pass, edt_repass;
    AppCompatButton btn_sigup;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        setupUI();
        onListener();
    }
    private void setupUI(){
        edt_name = findViewById(R.id.edt_sigup_name);
        edt_email = findViewById(R.id.edt_sigup_email);
        edt_pass = findViewById(R.id.edt_sigup_pass);
        edt_repass = findViewById(R.id.edt_sigup_repass);
        btn_sigup = findViewById(R.id.btn_sigup);
    }
    private void onListener(){
        btn_sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edt_pass.getText().toString();
                String repass = edt_repass.getText().toString();
                String name = edt_name.getText().toString();
                String email = edt_email.getText().toString();
                if (name.equals("")){
                    Toast.makeText(Signup_Activity.this, "Bạn chưa nhập Name", Toast.LENGTH_SHORT).show();
                }else if (email.equals("")){
                    Toast.makeText(Signup_Activity.this, "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")){
                    Toast.makeText(Signup_Activity.this, "Bạn chưa nhập Password", Toast.LENGTH_SHORT).show();
                } else if (repass.equals("")){
                    Toast.makeText(Signup_Activity.this, "Bạn chưa nhập lại Password", Toast.LENGTH_SHORT).show();
                } else if (pass.equals(repass)){
                    addAccount(name,email,pass);
                    Intent intent = new Intent(Signup_Activity.this, Login_Activity.class);
                    startActivity(intent);
                    Toast.makeText(Signup_Activity.this, "Tạo tài khoản thành công!!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Signup_Activity.this, "Password không khớp", Toast.LENGTH_SHORT).show();
                }






            }
        });
    }
    private void addAccount(String name, String email, String pass){
        mDatabase = FirebaseDatabase.getInstance().getReference("TaiKhoanAdmin");
        String id = mDatabase.push().getKey();
        TaiKhoan taiKhoan = new TaiKhoan(id,name, email, pass);
        mDatabase.child(id).setValue(taiKhoan);
    }
}