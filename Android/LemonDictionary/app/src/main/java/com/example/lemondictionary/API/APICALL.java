package com.example.lemondictionary.API;


import com.example.lemondictionary.Model.Config;
import com.example.lemondictionary.Model.Res;
import com.example.lemondictionary.Model.User;
import com.example.lemondictionary.Model.Word;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APICALL {
    @POST("api/auth/register")
    Call<Res> register(@Body User user);
    @POST("api/auth/login")
    Call<ResponseBody> Login(@Body User user);
    @GET("api/word/search")
    Call<Word> GetInfo(@Query("word") String word);
    @POST("api/word/add-favorite")
    Call<ResponseBody> AddFavorite(@Header("access_ticket") String ticket, @Body Word word);
    @GET("api/word/list-favorite")
    Call<ArrayList<Word>> GetListFavorite(@Header("access_ticket") String ticket);
    @POST("api/word/revision")
    Call<ArrayList<Word>> GetListQuestion(@Header("access_ticket") String ticket,@Body Config config);
    @GET("api/word/translate")
    Call<ResponseBody> GetTranslatedText(@Query("text") String text,@Query("target") String target,@Query("source") String source);
//    @GET("api/product/find-product")
//    Call<Product> getProduct(@Query("id") String id);
//    @POST("api/product/update-product")
//    Call<Product> updateProduct(@Body Product product );
//    @POST("api/product/delete-product")
//    Call<String> deleteProduct(@Body  JsonObject id);
//    @POST("api/product/create-product")
//    Call<String> newProduct(@Body Product product);

}
