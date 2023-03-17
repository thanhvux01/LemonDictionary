package com.example.lemondictionary.Adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemondictionary.ListSavedActivity;
import com.example.lemondictionary.Model.Word;
import com.example.lemondictionary.R;

import java.io.IOException;
import java.util.ArrayList;

public class ListSavedAdapter extends ArrayAdapter<Word> {
    public ListSavedAdapter(Context context, ArrayList<Word> words) {
        super(context, 0, words);
    }
    MediaPlayer mediaPlayer;
    public void playAudio(String url) {


        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(url);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(getContext(), "Audio started playing..", Toast.LENGTH_SHORT).show();
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        try {
            Word word = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_saved,parent, false);
            }
            // Lookup view for data population
            TextView txtWord = (TextView) convertView.findViewById(R.id.ltxtWord);
            TextView txtMeaning = (TextView) convertView.findViewById(R.id.ltxtMeaning);

            TextView txtType = (TextView) convertView.findViewById(R.id.ltxtpartOfSpeech);
            ImageView lbtnVolumn = (ImageView) convertView.findViewById(R.id.lbtnVolumn);

            // Populate the data into the template view using the data object
           txtWord.setText(word.getName());
           txtMeaning.setText(word.getMeaning());
           txtType.setText(word.getPartOfSpeech());
           lbtnVolumn.setImageResource(R.drawable.ic_baseline_volume_up_24);
           lbtnVolumn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   playAudio(word.getAudioURL());
               }
           });


            // Return the completed view to render on screen
            return convertView;
        } catch (Throwable t) {

            t.printStackTrace();
            return convertView;
        }

    }
}
