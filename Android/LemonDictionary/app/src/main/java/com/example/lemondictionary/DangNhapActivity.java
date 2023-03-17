package com.example.lemondictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lemondictionary.API.APICALL;
import com.example.lemondictionary.Model.User;
import com.example.lemondictionary.Retrofit.RetrofitInstance;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DangNhapActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    TextView signupText;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        username = findViewById(R.id.etxtUsername);
        password = findViewById(R.id.etxtNumber);
        loginButton = findViewById(R.id.btnGoToHome);
         signupText = findViewById(R.id.signupText);
         SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF",MODE_PRIVATE);
         token = sharedPreferences.getString("Token","nottoken");
         if(token.equals("nottoken")){
             Toast.makeText(DangNhapActivity.this, "plz login", Toast.LENGTH_SHORT).show();
         }else{
             Intent intent = new Intent(DangNhapActivity.this,HomeActivity.class);
             startActivity(intent);
         }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APICALL myApi = RetrofitInstance.Init();
                User user = new User();
                user.setPassword(password.getText().toString());
                user.setUsername(username.getText().toString());
                Call<ResponseBody> call = myApi.Login(user);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()!=200){
                            Toast.makeText(DangNhapActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            String token = response.body().string();
                            SharedPreferences shared = getSharedPreferences("SHARED_PREF",MODE_PRIVATE);
                            SharedPreferences.Editor edit = shared.edit();
                            Log.w(null,token);
                            edit.putString("Token",token);
                            edit.apply();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                             t.printStackTrace();
                    }
                });

            }
        });
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent = new Intent(DangNhapActivity.this,DangKyActivity.class);
             startActivity(intent);
            }
        });
    }
}