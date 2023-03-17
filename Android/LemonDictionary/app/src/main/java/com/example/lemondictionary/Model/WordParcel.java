package com.example.lemondictionary.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class WordParcel implements Parcelable {
    private String name;
    private String phonetic;
    private String audioURL;
    private String partOfSpeech;
    private String example;
    private String meaning;
    private String definition;
    private String img;

    protected WordParcel(Parcel in) {
        name = in.readString();
        phonetic = in.readString();
        audioURL = in.readString();
        partOfSpeech = in.readString();
        example = in.readString();
        meaning = in.readString();
        definition = in.readString();
        img = in.readString();
    }

    public static final Creator<WordParcel> CREATOR = new Creator<WordParcel>() {
        @Override
        public WordParcel createFromParcel(Parcel in) {
            return new WordParcel(in);
        }

        @Override
        public WordParcel[] newArray(int size) {
            return new WordParcel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phonetic);
        parcel.writeString(audioURL);
        parcel.writeString(partOfSpeech);
        parcel.writeString(example);
        parcel.writeString(meaning);
        parcel.writeString(definition);
        parcel.writeString(img);
    }
}
