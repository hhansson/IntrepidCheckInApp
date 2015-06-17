package io.intrepid.hhansson.intrepidcheckinapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocatorService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private static final String TAG = LocatorService.class.getSimpleName();
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final long INTERVAL = 15 * 60 * 1000;
    public static final long FAST_INTERVAL = 60 * 1000;
    public static final double INTREPID_LAT = 42.367010;
    public static final double INTREPID_LON = -71.080210;
    public double distance;
    public boolean atIntrepid;
    public Bundle bundleSaved;
    public NotificationCompat.Builder builder;
    public NotificationManager notificationManager;

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

    }

    @Override
    public void onConnected(Bundle bundle) {
        bundleSaved = bundle;
        Log.i(TAG, "Location services connected");

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

        Double distance = Math.sqrt((INTREPID_LAT - currentLatitude) * (INTREPID_LAT - currentLatitude) + (INTREPID_LON - currentLongitude) * (INTREPID_LON - currentLongitude));
        if (atIntrepid) {
            if (distance > 50.0) {
                atIntrepid = false;
                makeDepartureNotification();
            }
        } else if (distance <= 50.0) {
            Log.d(TAG, "@Intrepid is true");
            atIntrepid = true;
            makeArrivalNotification();
        }
    }

    private void makeDepartureNotification() {
        Intent i = new Intent(getApplicationContext(), NotifyDeparture.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("Click to post to #Who's-Here!")
                .setContentText("Departure Message I'm Out!")
                .setContentIntent(contentIntent);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
        Log.d(TAG, "Notification Built");
    }

    public void makeArrivalNotification() {
        Intent i = new Intent(getApplicationContext(), NotifyArrival.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("Click to post to #Who's-Here!")
                .setContentText("Arrival Message I'm Here!")
                .setContentIntent(contentIntent);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
        Log.d(TAG, "Notification Built");
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
