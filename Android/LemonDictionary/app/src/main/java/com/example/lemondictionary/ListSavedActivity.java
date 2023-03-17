package com.example.lemondictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lemondictionary.API.APICALL;
import com.example.lemondictionary.Adapter.ListSavedAdapter;
import com.example.lemondictionary.Model.Word;
import com.example.lemondictionary.Retrofit.RetrofitInstance;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSavedActivity extends AppCompatActivity {
    ListView listSavedWord;
    ArrayList<Word> listWord;
    ImageView lbtnVolumn;
    MediaPlayer mediaPlayer;
    String ticket = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzYTgzZTU1OTVlMmM3NDIzZTI0ODMyOCIsImlzQWRtaW4iOnt9LCJpYXQiOjE2NzIxMjgxNzF9.PGq3SWe59EvkoygedUG_Yo1rw70wHNOwga92PkeSEMA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_saved);

        listSavedWord = findViewById(R.id.listSavedWord);
        APICALL myApi= RetrofitInstance.Init();
        Call<ArrayList<Word>> call = myApi.GetListFavorite(ticket);
        call.enqueue(new Callback<ArrayList<Word>>() {
            @Override
            public void onResponse(Call<ArrayList<Word>> call, Response<ArrayList<Word>> response) {
                 if(response.code()!=200){
                     Toast.makeText(ListSavedActivity.this,"No response",Toast.LENGTH_LONG).show();
                     return;
                 }

                 listWord = response.body();
                 listSavedWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                         Intent intent = new Intent(ListSavedActivity.this, DetailSavedActivity.class);
                         Bundle extras = new Bundle();
                         extras.putSerializable("Model",(Serializable)listWord.get(i));
                         intent.putExtra("WordData",extras);
                         startActivity(intent);
                     }
                 });
                 ListSavedAdapter adapter = new ListSavedAdapter(getApplicationContext(),listWord);
                 listSavedWord.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Word>> call, Throwable t) {
                  t.printStackTrace();
            }
        });





    }
}