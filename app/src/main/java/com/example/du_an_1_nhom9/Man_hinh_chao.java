package com.example.du_an_1_nhom9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Man_hinh_chao extends AppCompatActivity {
    Button btnLogin, btnGignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        btnLogin = findViewById(R.id.button);
        btnGignup = findViewById(R.id.button2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Man_hinh_chao.this, Login_Activity.class));
            }
        });
        btnGignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Man_hinh_chao.this, Signup_Activity.class));
            }
        });
    }

}