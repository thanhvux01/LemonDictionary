package com.example.lemondictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemondictionary.API.APICALL;
import com.example.lemondictionary.Model.Word;
import com.example.lemondictionary.Retrofit.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    String ret = "";
    String audioURL = "";
    String[] Hint;
    static Word global;
    String ticket = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzYTgzZTU1OTVlMmM3NDIzZTI0ODMyOCIsImlzQWRtaW4iOnt9LCJpYXQiOjE2NzIxMjgxNzF9.PGq3SWe59EvkoygedUG_Yo1rw70wHNOwga92PkeSEMA";
    ImageView imageView,btnSpeak,btnLove;
    AutoCompleteTextView editText;
    LinearLayout layoutMeaning,layoutSpeaking;
    MediaPlayer mediaPlayer;
    TextView txtMeaning,txtPhonetic,txtDefinition,txtExample;
    TextView labelMeaning,labelPhonetic,labelDef,labelExample;
    private String readFromFile() {
        FileInputStream fis = null;
        try {
           fis = openFileInput("Interface.txt");
            if ( fis != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis );
                BufferedReader br = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = br.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }
                fis.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("SearhActivity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("SearchAcitivty", "Can not read file: " + e.toString());
        }
        return ret;
    }
    public void playAudio() {


        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioURL);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        readFromFile();
        editText = findViewById(R.id.autoSearch);
        Hint = ret.split(",");
        btnSpeak = findViewById(R.id.btnSpeak);
        btnLove = findViewById(R.id.btnLove);
        txtMeaning = findViewById(R.id.txtMeaning);
        txtExample = findViewById(R.id.txtExample);
        imageView = findViewById(R.id.imageView);
        layoutMeaning = findViewById(R.id.layoutMeaning);
        layoutSpeaking = findViewById(R.id.layoutSpeak);
        txtPhonetic = findViewById(R.id.txtPhonetic);
        txtDefinition = findViewById(R.id.txtDefinition);
        labelMeaning = findViewById(R.id.labelMeaning);
        labelDef = findViewById(R.id.labelDef);
        labelExample= findViewById(R.id.labelExample);
        labelPhonetic = findViewById(R.id.labelPhonetic);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Hint);
        editText.setAdapter(adapter);
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = editText.getText().toString();
                APICALL myApi = RetrofitInstance.Init();
                Call<Word> wordData = myApi.GetInfo(word);
                wordData.enqueue(new Callback<Word>() {
                    @Override
                    public void onResponse(Call<Word> call, Response<Word> response) {
                        if(response.code() != 200){
                              Toast.makeText(SearchActivity.this,"No response",Toast.LENGTH_LONG).show();
                              return;
                        }
//                        Log.w(null,response.body().getExample()+"");
//                        Log.w(null,response.body().getPartOfSpeech()+"");
                          Word word = new Word();
                          word = response.body();
                          global = word;
                          Picasso.get().load(word.getImg()).into(imageView);
                          layoutMeaning.setVisibility(View.VISIBLE);
                          layoutSpeaking.setVisibility(View.VISIBLE);
                          labelDef.setVisibility(View.VISIBLE);
                          labelExample.setVisibility(View.VISIBLE);
                          audioURL = word.getAudioURL();
                          txtMeaning.setText(word.getMeaning());
                          txtPhonetic.setText(word.getPhonetic());
                        if(word.getDefinition().length()>0) {
                            txtDefinition.setText(word.getDefinition());
                            txtDefinition.setTextSize(20);
                        }
                          if(word.getExample().length()>0) {
                              txtExample.setText(word.getExample());
                              txtExample.setTextSize(20);
                          }


                    }

                    @Override
                    public void onFailure(Call<Word> call, Throwable t) {
                            t.printStackTrace();
                    }
                });


            }
        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio();
            }
        });
        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APICALL myApi = RetrofitInstance.Init();
                Call<ResponseBody> addFavorite = myApi.AddFavorite(ticket,global);
                addFavorite.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()!=200){
                            Toast.makeText(SearchActivity.this,"No response",Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(SearchActivity.this,"Added",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                         t.printStackTrace();
                    }
                });
            }
        });


    }
}