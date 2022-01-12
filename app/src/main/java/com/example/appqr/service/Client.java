package com.example.appqr.service;

public class Client {
    public static final String URl_001="http://192.168.1.126:8080/";
    public static APIService getAPIService(){
        return GetClient.GetClient(URl_001).create(APIService.class);}
}
