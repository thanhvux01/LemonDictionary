package com.example.lemondictionary.Model;

import com.google.gson.annotations.SerializedName;

public class Res {
    @SerializedName("message")
    private String messages;
    private int status;
    private boolean success;



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean sucess) {
        this.success = sucess;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String message) {
        this.messages = message;
    }
}
