package com.example.du_an_1_nhom9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.du_an_1_nhom9.model.TaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Activity extends AppCompatActivity {
    EditText edt_name, edt_email, edt_pass, edt_repass;
    AppCompatButton btn_sigup;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

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
        dialog = new ProgressDialog(this);
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
                }

                if (pass.equals(repass)){
                    onClickSinup();
                }else {
                    Toast.makeText(Signup_Activity.this, "Password không khớp", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void onClickSinup() {
        String email = edt_email.getText().toString().trim();
        String password = edt_pass.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Signup_Activity.this,Login_Activity.class);
                            startActivity(intent);
                            Toast.makeText(Signup_Activity.this, "Đăng ký thành công",
                                    Toast.LENGTH_SHORT).show();
                            finishAffinity();

                        } else {
                            Toast.makeText(Signup_Activity.this, "Đăng ký thất bại!!!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}