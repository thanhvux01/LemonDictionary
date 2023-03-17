package com.example.lemondictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemondictionary.API.APICALL;
import com.example.lemondictionary.Model.Config;
import com.example.lemondictionary.Model.Word;
import com.example.lemondictionary.Retrofit.RetrofitInstance;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamActivity extends AppCompatActivity {
    Spinner dropdown,dropdown2;
    TextView number;
    Button btnGoToExam;
    String[] courses = { "Ngẫu nhiên", "Từ đã lưu","Chủ đề"};
    String[] topic = {"None","Weather","Food"};
    String ticket = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzYTgzZTU1OTVlMmM3NDIzZTI0ODMyOCIsImlzQWRtaW4iOnt9LCJpYXQiOjE2NzIxMjgxNzF9.PGq3SWe59EvkoygedUG_Yo1rw70wHNOwga92PkeSEMA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        dropdown = findViewById(R.id.dropdown);
        dropdown2 = findViewById(R.id.dropdown2);
        number = findViewById(R.id.etxtNumber);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,courses);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,topic);
        dropdown.setAdapter(arrayAdapter);
        dropdown2.setAdapter(arrayAdapter2);
        btnGoToExam = findViewById(R.id.btnGoToHome);
        btnGoToExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config config = new Config();
                config.setType( dropdown.getSelectedItemPosition());
                config.setNumber( Integer.parseInt(number.getText().toString()));
                config.setTopic( dropdown2.getSelectedItem().toString());
                APICALL myApi = RetrofitInstance.Init();
                Call<ArrayList<Word>> call = myApi.GetListQuestion(ticket,config);
                call.enqueue(new Callback<ArrayList<Word>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Word>> call, Response<ArrayList<Word>> response) {
                        if(response.code() != 200){
                            Toast.makeText(ExamActivity.this,"No response",Toast.LENGTH_LONG).show();
                            return;
                        }
                        ArrayList<Word> res = response.body();
                        Intent intent = new Intent(ExamActivity.this,RevisionActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST",(Serializable)res);
                        intent.putExtra("ListQuestion",args);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Word>> call, Throwable t) {
                          t.printStackTrace();
                    }
                });

            }
        });


    }
}