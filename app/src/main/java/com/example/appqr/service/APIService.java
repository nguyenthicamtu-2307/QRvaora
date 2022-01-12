package com.example.appqr.service;

import com.example.appqr.model.Account;
import com.example.appqr.model.HistoryQR;
import com.example.appqr.model.PersonInf;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
//    Gson gson=new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd HH:mm:ss")
//            .create();
    APIService api =new Retrofit.Builder().baseUrl("http://192.168.1.126:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService.class);
    @GET("account/listar")
    Call<List<Account>> getsanpham();

    @POST("account/add")
    Call<Account>regiter(@Body Account account);
//   @FormUrlEncoded
//    @POST("khachang/capnhat{username}")
//    Call<PersonInf>update(
//            @Field("tenkh") String tenkh,
//            @Field("ngaysinh") String ngaysinh,
//            @Field("diachi") String diachi,
//            @Field("sdt") String sdt,
//            @Field("gioitinh") String gioitinh
//    );
   @POST("khachhang/capnhat/{username}")
    Call<PersonInf> updatethongtin(
            @Path("username") String username,
            @Body PersonInf personInf

   );
    @GET("lichsuqr/getall/{username}")
    Call<List<HistoryQR>> getQR(
            @Path("username") String user
           // @Body HistoryQR QR

    );
    @POST("lichsuqr/insert")
    Call<HistoryQR>insertdata(
        @Body HistoryQR historyQR
    );
}
