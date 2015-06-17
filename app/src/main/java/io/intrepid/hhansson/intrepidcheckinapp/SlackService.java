package io.intrepid.hhansson.intrepidcheckinapp;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface SlackService {

    @POST("/services/T026B13VA/B064U29MZ/vwexYIFT51dMaB5nrejM6MjK")
    void postSlackMessage(@Body SlackMessage slackMessage, Callback<Void> callback);

}
