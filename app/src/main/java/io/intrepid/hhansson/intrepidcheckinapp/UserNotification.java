package io.intrepid.hhansson.intrepidcheckinapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserNotification extends BroadcastReceiver {
    public static final String TAG = UserNotification.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Post Request Received.");
        sendPostRequest(intent.getExtras().getBoolean("arriving"));

    }

    private void sendPostRequest(boolean arriving) {
        String message = (arriving) ? "I'm here!" : "I'm out!";
        SlackMessage slackMessage = new SlackMessage(message, "Hayley");
        ServiceAdapter.getSlackServiceInstance()
                .postSlackMessage(BuildConfig.SLACK_CHANNEL_URL_KEY ,slackMessage, new Callback<Object>() {
            @Override
            public void success(Object aVoid, Response response) {
                Log.d(TAG, "Slack post successful!");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Slack post unsuccessful. Error: " + error);

            }
        });
    }
}
