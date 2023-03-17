package com.example.lemondictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lemondictionary.Model.Word;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailSavedActivity extends AppCompatActivity {
    EditText editMeaning,editName,editPhonetic,editPartOfSpeech;
    ImageView imageWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_saved);
        editMeaning = findViewById(R.id.editMeaning);
        editName = findViewById(R.id.editWord);
        editPhonetic = findViewById(R.id.editPhonetic);
        editPartOfSpeech = findViewById(R.id.editPartOfSpeech);
        imageWord = findViewById(R.id.imageWord);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("WordData");
        Word word = (Word) args.getSerializable("Model");
        Picasso.get().load(word.getImg()).into(imageWord);
        editMeaning.setText(word.getMeaning());
        editName.setText(word.getName());
        editPhonetic.setText(word.getPhonetic());
        editPartOfSpeech.setText(word.getPartOfSpeech());

    }


}