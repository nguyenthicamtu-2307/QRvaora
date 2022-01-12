package com.example.appqr;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appqr.model.PersonInf;
import com.example.appqr.service.APIService;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapNhatActiity extends AppCompatActivity implements Serializable {
    Button chuyen;
    EditText ten, diachi, ngaysinh, gioitinh, sdt;
    APIService apiService;
    private PersonInf khachhang = new PersonInf();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttcanhan);

        chuyen = (Button) findViewById(R.id.capn);
        ten = (EditText) findViewById(R.id.txtTenKH);
        diachi = (EditText) findViewById(R.id.txtdiachi);
        ngaysinh = (EditText) findViewById(R.id.txtns);
        sdt = (EditText) findViewById(R.id.txtsdt);
        gioitinh = (EditText) findViewById(R.id.txtgioitinh);
        nhanData();
        chuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveInfor(khachhang);
                update();
//                Call<PersonInf> call = apiService.updatethongtin(khachhang.getUsername(), khachhang);
                APIService.api.updatethongtin(khachhang.getUsername(), khachhang).enqueue(new Callback<PersonInf>() {
                    @Override
                    public void onResponse(Call<PersonInf> call, Response<PersonInf> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CapNhatActiity.this, "Thaats bai!!!", Toast.LENGTH_LONG).show();

                        }
                        if(response.isSuccessful()) {

                        }
                    }

                    @Override
                    public void onFailure(Call<PersonInf> call, Throwable t) {
                        Toast.makeText(CapNhatActiity.this, "Update successful!!!Please login now" +
                                " ", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(CapNhatActiity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

//                Bundle bundle = new Bundle();
//                bundle.putSerializable("object_user", (Serializable) khachhang);
//                intent.putExtras(bundle);

            }
        });
    }

    private void update() {
        String username = ten.getText().toString();
        String address = diachi.getText().toString();
        String phone = sdt.getText().toString();
        String date = ngaysinh.getText().toString();
        String gt = gioitinh.getText().toString();
        if (username.isEmpty()) {
            ten.requestFocus();
            ten.setError("Please enter your name");
            return;
        }
        if (address.isEmpty()) {
            diachi.requestFocus();
            ten.setError("Please enter your address");
            return;
        }
        if (phone.isEmpty()) {
            sdt.requestFocus();
            ten.setError("Please enter your number");
            return;
        }
        if (date.isEmpty()) {
            ngaysinh.requestFocus();
            ngaysinh.setError("Please enter your birthday");
            return;
        }
        if (gt.isEmpty()) {
            gioitinh.requestFocus();
            gioitinh.setError("Please enter ");
            return;
        }

    }

    private void saveInfor(PersonInf khachhang) {
        khachhang.setTenkh(ten.getText().toString());
        khachhang.setNgaysinh(ngaysinh.getText().toString());
        khachhang.setSdt(sdt.getText().toString());
        khachhang.setDiachi(diachi.getText().toString());
        khachhang.setGioitinh(gioitinh.getText().toString());
        khachhang.setUsername(RegisterActivity.unameGlobal);

    }

    public void nhanData() {
        Bundle bundleRecevie = getIntent().getExtras();
        if (bundleRecevie != null) {
            khachhang = (PersonInf) bundleRecevie.get("object_user");

            ten.setText(khachhang.getTenkh());
            ngaysinh.setText(khachhang.getNgaysinh());
            sdt.setText(khachhang.getSdt());
            diachi.setText(khachhang.getDiachi());
            gioitinh.setText(khachhang.getGioitinh());
        }
    }
}