package com.example.appqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
ImageButton QR,quanly,use;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        QR=(ImageButton) findViewById(R.id.quetqr);
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ScanQRActivity.class);
                startActivity(intent);
            }
        });
        quanly=(ImageButton) findViewById(R.id.nutquanly);
        quanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GenerateActivity.class);
                startActivity(intent);
            }
        });
        use=(ImageButton) findViewById(R.id.user1);
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DanhMucActivuty.class);
                startActivity(intent);
            }
        });
    }
}