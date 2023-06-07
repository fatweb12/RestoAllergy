package com.fatweb.allergysafenz.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;


import com.fatweb.allergysafenz.R;

import java.util.Timer;
import java.util.TimerTask;


public class LocationTrackingService extends Service {
    public LocationTrackingService() {
    }

    GPSTracker gps;
    double latitude, longitude;
    SharedPreferences sharedPreferences;

    public static Location userLocation;

    public static final long GET_LOCATION_INTERVAL = 15 * 1000;

    private Handler mHandler = new Handler();
    private Timer mTimer = null;
   // SecurePreferences prefs;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        userLocation = new Location("userLocation");
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//        prefs =  new SecurePreferences(getApplicationContext(), RestoAllergyApplication.USERPREFERENCES, getString(R.string.app_name), true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocation();
            }
        }, 2 * 1000);

        new Thread(new Runnable() {
            public void run() {
                mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, GET_LOCATION_INTERVAL);
            }
        }).start();
    }

    private class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    getLocation();
                }
            });
        }
    }
    private void getLocation(){
        gps = new GPSTracker(getApplicationContext());
        if (gps.canGetLocation()) {
            if (gps.getLatitude() > 0 && gps.getLatitude() > 0 ||
                    gps.getLatitude() < 0 && gps.getLatitude() < 0){
                System.out.println("location tracking lat: " + gps.getLatitude() + " long: " + gps.getLongitude());
                userLocation.setLatitude(gps.getLatitude());
                userLocation.setLongitude(gps.getLongitude());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("userLatitude", (float) gps.getLatitude());
                editor.putFloat("userLongitude", (float) gps.getLongitude());
                editor.apply();

               /* prefs.put("userLatitude",  gps.getLatitude()+"");
                prefs.put("userLongitude",  gps.getLongitude()+"");*/
            }
            else {
                latitude = sharedPreferences.getFloat("userLatitude", 0);
                longitude = sharedPreferences.getFloat("userLongitude", 0);
                userLocation.setLatitude(latitude);
                userLocation.setLongitude(longitude);
            }
        }
        else {
            latitude = sharedPreferences.getFloat("userLatitude", 0);
            longitude = sharedPreferences.getFloat("userLongitude", 0);
            userLocation.setLatitude(latitude);
            userLocation.setLongitude(longitude);
        }
    }
}

