package com.example.appqr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DanhMucActivuty extends AppCompatActivity {
    ImageButton scan,history;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhmuc);
        scan=(ImageButton) findViewById(R.id.quetqr);
        history=(ImageButton) findViewById(R.id.his);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DanhMucActivuty.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
