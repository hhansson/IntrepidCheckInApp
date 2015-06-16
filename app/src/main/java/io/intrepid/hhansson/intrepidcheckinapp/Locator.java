package io.intrepid.hhansson.intrepidcheckinapp;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.TimerTask;

public class Locator extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private static final String TAG = Locator.class.getSimpleName();
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final long INTERVAL = 15 * 60 * 1000;
    public static final long FAST_INTERVAL = 60 * 1000;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Override
    public void onCreate() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(INTERVAL)
                .setFastestInterval(FAST_INTERVAL);

        googleApiClient.connect();
        Log.d(TAG, "through onCreate!!!!!!!!!!!!!!!!!");

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Location services connected!!!!!!!!!!!!!!!");

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }else {
            handleNewLocation(location);
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        Proximity proximity = new Proximity();
        proximity.findDistance(currentLatitude, currentLongitude);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Location services failed to connect" + connectionResult);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
