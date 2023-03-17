package com.example.lemondictionary.Retrofit;

import com.example.lemondictionary.API.APICALL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static RetrofitInstance instance = null;
    private APICALL myApi;
    public static APICALL Init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5500/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       APICALL myApi = retrofit.create(APICALL.class);
       return myApi;
    }

}
