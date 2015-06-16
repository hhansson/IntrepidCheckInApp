package io.intrepid.hhansson.intrepidcheckinapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;

public class TimerService extends Service {
    public static final long INTERVAL = 15 * 60 * 1000;

    @Override
    public void onCreate() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Locator(), 0, INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
