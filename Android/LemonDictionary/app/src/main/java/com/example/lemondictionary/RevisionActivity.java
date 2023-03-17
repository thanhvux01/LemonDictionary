package com.example.lemondictionary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemondictionary.Model.Word;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class RevisionActivity extends AppCompatActivity {
    int index,score;
    int time;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    CountDownTimer cTimer = null;
    ImageView imageView;
    TextView txtAnswer,txtCorrect,txtTime;
    Button btnNextQuestion,btnGoToHome;
    String[] Answer = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);

        index = 0;
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("ListQuestion");
        ArrayList<Word> word = (ArrayList<Word>) args.getSerializable("ARRAYLIST");
        btnNextQuestion = findViewById(R.id.btnNextQuestion);
        imageView = findViewById(R.id.revImage);
        txtAnswer = findViewById(R.id.txtAnswer);
        Picasso.get().load(word.get(index).getImg()).into(imageView);
        cTimer = new CountDownTimer(10000*word.size(), 1000) {
            public void onTick(long millisUntilFinished) {
                 time += 1;
            }
            public void onFinish() {
            }
        };
        cTimer.start();
        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.w(null,Answer[index]);
//                Log.w(null,word.get(index).getName());
                if(index<word.size()-1) {
                    Answer[index] = txtAnswer.getText().toString();
                    index++;
                    txtAnswer.setText("");
                    Picasso.get().load(word.get(index).getImg()).into(imageView);
                }else if(index== word.size()-1){
                    for(int i=0;i<word.size()-1;i++){

                        if(word.get(i).getName().equals(Answer[i])){
                                score++;
                        }

                    }
//                    Toast.makeText(RevisionActivity.this,score+"",Toast.LENGTH_LONG).show();
                       dialogBuilder = new AlertDialog.Builder(RevisionActivity.this);
                       final View contactPopupView = getLayoutInflater().inflate(R.layout.popup_result,null);
                       dialogBuilder.setView(contactPopupView);
                       txtCorrect = (TextView) contactPopupView.findViewById(R.id.txtCorrect);
                       txtTime = (TextView) contactPopupView.findViewById(R.id.txtTime);
                       btnGoToHome = contactPopupView.findViewById(R.id.btnGoToHome);
                       txtCorrect.setText(score+"câu");
                       txtTime.setText(time+"giây");
                       btnGoToHome.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent = new Intent(RevisionActivity.this,HomeActivity.class);
                               startActivity(intent);
                               finish();
                           }
                       });
                       dialog = dialogBuilder.create();
                       dialog.show();



                }
            }
        });

    }
}