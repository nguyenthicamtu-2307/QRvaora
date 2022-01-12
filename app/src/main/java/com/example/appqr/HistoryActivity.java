package com.example.appqr;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appqr.model.HistoryQR;
import com.example.appqr.model.HistoryQrAdapter;
import com.example.appqr.model.PersonInf;
import com.example.appqr.service.APIService;
import com.example.appqr.service.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView lvqr;
   // HistoryQR qrcode;
    EditText txtSearch;
    APIService apiService;
    HistoryQrAdapter adapter;
    private HistoryQR qrcode = new HistoryQR();
    List<HistoryQR> list=new ArrayList<HistoryQR>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historyqr);
        Bundle bundleRecevie = getIntent().getExtras();
        if(bundleRecevie!=null){
            HistoryQR mon = (HistoryQR) bundleRecevie.get("object_user");
        }
        lvqr=(RecyclerView) findViewById(R.id.lv);
        txtSearch=(EditText)findViewById(R.id.search);
        apiService= Client.getAPIService();
        listar();
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });

    }

    private void filter(String text){
        ArrayList<HistoryQR> filteredList = new ArrayList<>();
        for (HistoryQR item : list){
            if(item.getNoidung().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }


    public void listar(){
        saveInfor(qrcode);
        APIService.api.getQR(qrcode.getUsername()).enqueue(new Callback<List<HistoryQR>>() {
            @Override
            public void onResponse(Call<List<HistoryQR>> call, Response<List<HistoryQR>> response) {
                if(response.code()!=200){
                    return;
                }
                List<HistoryQR> data= response.body();
                for (HistoryQR mon:data){
                    list.add(mon);
                }
                Putdata(data);
            }


            @Override
            public void onFailure(Call<List<HistoryQR>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
//        Call<List<HistoryQR>> call=apiService.getQR();
//        call.enqueue(new Callback<List<HistoryQR>>() {
//            @Override
//            public void onResponse(Call<List<HistoryQR>> call, Response<List<HistoryQR>> response) {
//                if(response.code()!=200){
//                    return;
//                }
//                List<HistoryQR>data= response.body();
//                for (HistoryQR mon:data){
//                    list.add(mon);
//                }
//                Putdata(data);
//            }
//            @Override
//            public void onFailure(Call<List<HistoryQR>> call, Throwable t) {
//                Log.e("Error",t.getMessage());
//            }
//        });
   }
    private  void Putdata(List<HistoryQR> qr){
        adapter=new HistoryQrAdapter( this,qr);
        lvqr.setLayoutManager(new LinearLayoutManager(this));
        lvqr.setAdapter(adapter);
    }
    private void saveInfor(HistoryQR khachhang) {
        khachhang.setUsername(LoginActivity.unameGlobal);

    }
}
