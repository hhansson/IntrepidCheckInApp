package io.intrepid.hhansson.intrepidcheckinapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NotifyArrival extends BroadcastReceiver {
    public static final String TAG = NotifyArrival.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Post Request Received.");
        sendPostRequest();

    }

    private void sendPostRequest() {
        SlackMessage slackMessage = new SlackMessage("I'm here! Boom.", "Hayley");
        ServiceAdapter.getSlackServiceInstance().postSlackMessage(BuildConfig.SLACK_CHANNEL_URL_KEY ,slackMessage, new Callback<Void>() {
            @Override
            public void success(Void aVoid, Response response) {
                Log.d(TAG, "Slack post successful!");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Slack post unsuccessful. Error: " + error);

            }
        });

    }
}
