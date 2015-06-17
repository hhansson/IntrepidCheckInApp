package io.intrepid.hhansson.intrepidcheckinapp;

import com.google.gson.annotations.SerializedName;

public class SlackMessage {

    private String text;

    private String username;

    public SlackMessage(String text, String username) {
        this.text = text;
        this.username = username;
    }
}
