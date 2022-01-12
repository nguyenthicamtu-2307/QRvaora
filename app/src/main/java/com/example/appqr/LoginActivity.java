package com.example.appqr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appqr.model.Account;
import com.example.appqr.service.APIService;
import com.example.appqr.service.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    String username, password;
    private Button login;
    private EditText edituse,pass;
    private TextView taotk;
    public static String unameGlobal ="";
    APIService apiService;
    private List<Account> khachHangs;
    Account kh;
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        apiService = Client.getAPIService();
        login = (Button) findViewById(R.id.login);
        edituse = (EditText) findViewById(R.id.edituser);
        pass = (EditText) findViewById(R.id.editpass);
        //apiService=Client.getAPIService();
        login();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edituse.getText().toString()) && TextUtils.isEmpty(pass.getText().toString())) {
                    // this method will call when email and password fields are empty.
                    Toast.makeText(LoginActivity.this, "Name or Password Requied", Toast.LENGTH_SHORT).show();
                } else {
                    checklogin();
                }

            }
        });
        taotk=(TextView) findViewById(R.id.taotaikhoan);
        taotk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public void login() {
        APIService.api.getsanpham().enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                khachHangs=response.body();
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {

            }
        });
//        Call<List<Account>> call = apiService.getsanpham();
//       call.enqueue(new Callback<List<Account>>() {
//           @Override
//           public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
//
//
//           }
//
//           @Override
//           public void onFailure(Call<List<Account>> call, Throwable t) {
//
//           }
//       });
 }
    public void checklogin(){
        unameGlobal = edituse.getText().toString();

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(EMAIL_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);
        String strUsername = edituse.getText().toString().trim();
        String strPassword = pass.getText().toString().trim();

        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("Nhập Thiếu Thông Tin");
        alert.setMessage("Bạn nhập thiếu thông tin. Vui lòng nhập lại");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alert.setCancelable(true);
            }
        });
        if(strUsername.isEmpty()||strPassword.isEmpty()){
            alert.show();
        }
        else {
            if(khachHangs == null || khachHangs.isEmpty()){
                Toast.makeText(getApplicationContext(), "NULL", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean isHasUser = false;
            for(Account khachHang: khachHangs){
                if(strUsername.equals(khachHang.getUsername()) && strPassword.equals(khachHang.getPasswork())){
                    isHasUser = true;
                    kh = khachHang;
                    break;
                }
            }
            if (isHasUser){
                Toast.makeText(getApplicationContext(), "had user", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                    // below two lines will put values for
                    // email and password in shared preferences.
                    editor.putString(EMAIL_KEY, edituse.getText().toString());
                    editor.putString(PASSWORD_KEY, pass.getText().toString());
                    // to save our data with key and value.
                    editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                alert.setTitle("Đăng nhập thất bại");
                alert.setMessage("Bạn nhập sai tên đăng nhập hoặc mật khẩu! Vui lòng kiểm tra lại!");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alert.setCancelable(true);
                    }
                });
                alert.show();
            }
        }
    }
}