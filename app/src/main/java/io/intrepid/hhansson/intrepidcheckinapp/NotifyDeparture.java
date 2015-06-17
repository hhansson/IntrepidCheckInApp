package io.intrepid.hhansson.intrepidcheckinapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NotifyDeparture extends BroadcastReceiver {
    public static final String TAG = NotifyDeparture.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Post Request Received.");
        sendPostRequest();

    }

    private void sendPostRequest() {
        SlackMessage slackMessage = new SlackMessage("I'm gone! Boom.", "Hayley");
        ServiceAdapter.getSlackServiceInstance().postSlackMessage(slackMessage, new Callback<Void>() {
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
