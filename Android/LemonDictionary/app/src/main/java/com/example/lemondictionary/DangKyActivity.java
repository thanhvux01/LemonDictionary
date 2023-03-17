package com.example.lemondictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lemondictionary.API.APICALL;
import com.example.lemondictionary.Model.Res;
import com.example.lemondictionary.Model.User;
import com.example.lemondictionary.Retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {
    Button btnRegister;
    EditText etxtUsername , etxtPassword , etxtPassword2 , etxtEmail ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        etxtUsername = findViewById(R.id.etxtUsername);
        etxtPassword = findViewById(R.id.etxtNumber);
        etxtPassword2 = findViewById(R.id.etxtPassword2);
        etxtEmail = findViewById(R.id.etxtMail);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
        APICALL myApi = RetrofitInstance.Init();

            @Override
            public void onClick(View view) {
                String pw1 = etxtPassword.getText().toString();
                String pw2 = etxtPassword2.getText().toString();
                if(!pw1.equals(pw2)){
                    Toast.makeText(DangKyActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }else {

                    User user = new User();
                    user.setUsername(etxtUsername.getText().toString());
                    user.setEmail(etxtEmail.getText().toString());
                    user.setPassword(pw1);
                    Call<Res> call = myApi.register(user);
                    call.enqueue(new Callback<Res>() {
                        @Override
                        public void onResponse(Call<Res> call, Response<Res> response) {
                            if(response.code() >= 400) {
                                Toast.makeText(DangKyActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//                                Log.w(null,response.body().toString());
                                return;
                            }
                            Toast.makeText(DangKyActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            Log.w(null,response.body().getMessages()+"");
                            Log.w(null,response.body().getStatus()+"");
                            Log.w(null,response.body().getSuccess()+"");

                        }

                        @Override
                        public void onFailure(Call<Res> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });




                }
            }
        });

    }
}