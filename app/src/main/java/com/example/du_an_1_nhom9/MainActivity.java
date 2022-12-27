package com.example.du_an_1_nhom9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.du_an_1_nhom9.fragment.HoaDonFragment;
import com.example.du_an_1_nhom9.fragment.QLSanPhamFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  {
    private DrawerLayout drawerLayout;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    HoaDonFragment hoaDonFragment = new HoaDonFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.mQLSanPham:
                    displayProducts();
                    break;
                case R.id.mQLThanhVien:
                    showDialog("Quản lý thành viên");
                case R.id.mQLHoaDon:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,hoaDonFragment).commit();
                    break;
//                    showDialog("Quản lý hoá đơn");
                case R.id.mTop10:
                    showDialog("Top Laptop bán chạy");
                case R.id.mDoanhThu:
                    showDialog("Doanh thu");
                case R.id.mDoiMatKhau:
                    showDialog("Đổi mật khẩu");
                case R.id.mThoat:
//                    showDialog("Thoát");
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(this,Login_Activity.class);
                    startActivity(intent);
                    finishAffinity();
                default:
                    break;
            }
            drawerLayout.close();
            return false;
        });
        displayProducts();
    }

    private void showDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void displayProducts(){
        QLSanPhamFragment fragment = new QLSanPhamFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

}