package com.example.appqr;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appqr.model.HistoryQR;
import com.example.appqr.model.PersonInf;
import com.example.appqr.service.APIService;
import com.google.android.material.textfield.TextInputEditText;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateActivity extends AppCompatActivity {
    private TextView qrCodeTV,tenkh,sdt,user,note;
    private ImageView qrCodeIMG;
    private TextInputEditText dataEDT;
    private Button generateQRbtn,saveqr;
    private QRGEncoder qrgEncoder;

    private Bitmap bitmap;
    private HistoryQR data = new HistoryQR();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
        nhanData();
        qrCodeTV = findViewById(R.id.tv_QR);
        qrCodeIMG = findViewById(R.id.img_QR);
        dataEDT = findViewById(R.id.edt_Data);
        tenkh=(TextView)findViewById(R.id.tennd);

        sdt=(TextView)findViewById(R.id.sdt);
        generateQRbtn = findViewById(R.id.btn_generate);
        generateQRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = dataEDT.getText().toString();
                if(data.isEmpty()){
                    Toast.makeText(GenerateActivity.this, "Please enter some data to generate QR code...", Toast.LENGTH_SHORT).show();
                }else{
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = width<height ? width:height;
                    dimen = dimen * 3/4;

                    qrgEncoder = new QRGEncoder(dataEDT.getText().toString(),null, QRGContents.Type.TEXT,dimen);
                    try {
                        bitmap = qrgEncoder.getBitmap();
                        qrCodeTV.setVisibility(View.GONE);
                        qrCodeIMG.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
        saveqr=(Button) findViewById(R.id.save);

        saveqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfor(data);
                APIService.api.insertdata(data).enqueue(new Callback<HistoryQR>() {
                    @Override
                    public void onResponse(Call<HistoryQR> call, Response<HistoryQR> response) {

                            Toast.makeText(GenerateActivity.this, "Add Fail!Please check again!!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<HistoryQR> call, Throwable t) {
                        Toast.makeText(GenerateActivity.this, "Add data successfull !!!", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
    private void saveInfor(HistoryQR khachhang) {
        khachhang.setUsername(LoginActivity.unameGlobal);
        khachhang.setTenkh(tenkh.getText().toString());
        khachhang.setSdt(sdt.getText().toString());
        khachhang.setNoidung(dataEDT.getText().toString());

    }
    public void nhanData() {
        Bundle bundleRecevie = getIntent().getExtras();
        if (bundleRecevie != null) {
            data = (HistoryQR) bundleRecevie.get("object_user");

            tenkh.setText(data.getTenkh());

            sdt.setText(data.getSdt());
            note.setText(data.getNoidung());
            user.setText(data.getUsername());

        }
    }
    }

