package io.intrepid.hhansson.intrepidcheckinapp;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Proximity extends Activity{
    public static final double INTREPID_LAT = 42.367010;
    public static final double INTREPID_LON = -71.080210;
    public boolean atIntrepid;

    public void findDistance(double latitude, double longitude){
        Double distance = Math.sqrt((INTREPID_LAT - latitude)*(INTREPID_LAT - latitude) + (INTREPID_LON - longitude)*(INTREPID_LON - longitude));
        if (atIntrepid) {
            if (distance > 50.0) {
                atIntrepid = false;
            }
        } else if (distance <= 50.0){
            atIntrepid = true;
            //toast post to slack
            //Toast toast = Toast.makeText(Proximity.this, "You're here!", Toast.LENGTH_LONG);
            //toast.show();
        }
    }


}
