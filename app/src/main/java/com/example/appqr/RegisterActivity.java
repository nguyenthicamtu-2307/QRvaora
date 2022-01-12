package com.example.appqr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appqr.model.Account;
import com.example.appqr.service.APIService;
import com.example.appqr.service.Client;
import com.example.appqr.service.GetClient;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText name, pass, sdt;
    public static String unameGlobal ="";
    APIService apiService;
    Account  account;
    private List<Account> khachHangs;
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);


        // variable for shared preferences.

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        name = (EditText) findViewById(R.id.txtuser);
        pass = (EditText) findViewById(R.id.txtpass);

        Button btnRegister = (Button) findViewById(R.id.btnregi);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pasword = pass.getText().toString();


                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.requestFocus();
                    name.setError("Please enter your name");
                    return;
                }


                if (TextUtils.isEmpty(pass.getText().toString())) {
                    pass.requestFocus();
                    pass.setError("Please enter your Passwork");
                    return;
                }
                if (pasword.length() > 10) {
                    pass.requestFocus();
                    pass.setError("Please check your Passwork");
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString()) && TextUtils.isEmpty(pass.getText().toString())) {
                    // this method will call when email and password fields are empty.
                    Toast.makeText(RegisterActivity.this, "Name or Password Requied", Toast.LENGTH_SHORT).show();
                } else {

                    apiService = Client.getAPIService();
                    register();
                }
            }
        });}
            public void register() {
                SharedPreferences sharedpreferences;
                String uname,upass;
                String pasword=pass.getText().toString();
                String username=name.getText().toString();
                unameGlobal = name.getText().toString();

                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                uname = sharedpreferences.getString(EMAIL_KEY, null);
                upass = sharedpreferences.getString(PASSWORD_KEY, null);


                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                Account account = new Account( username, pasword);
                Call<Account> call = apiService.regiter(account);
//
                call.enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        if (username.equals(account.getUsername()) || pasword.equals(account.getPasswork())) {
                            alert.setTitle("registration failed");
                            alert.setMessage("Account or password already exists!Please check again!");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alert.setCancelable(true);
                                }
                            });
                            alert.show();
                        }

                        }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        // below two lines will put values for
                        // email and password in shared preferences.
                        editor.putString(EMAIL_KEY, name.getText().toString());
                        editor.putString(PASSWORD_KEY, pass.getText().toString());
                        // to save our data with key and value.
                        editor.apply();
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, CapNhatActiity.class);
                        startActivity(intent);
                    }
                });
            }

//            public void checkregister(){
//
//            }

}