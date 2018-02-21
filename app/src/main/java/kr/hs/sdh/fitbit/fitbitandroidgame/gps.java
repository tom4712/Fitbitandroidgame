package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class gps extends Service  implements LocationListener {

    private final Context mContext;

    boolean isGPSEnabled = false,
            isNetworkEnabled = false,
            isGetLocation = false;

    public static final int REQUEST_GPS = 0;
    private static final int MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final int MIN_TIME_BW_UPDATES = 1;

    Location location;
    double lat;
    double lon;

    protected LocationManager locationManager;

    public gps(Context context) {
        mContext = context;
        getLocation();
    }

    public Location getLocation() {
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);
        checkPermission();

        if (!isGPSEnabled && !isNetworkEnabled) {
            Log.d("check", "안됨");
        } else {
            this.isGetLocation = true;
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            }

            if (isGPSEnabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                }
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            }
        }
        return location;
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, REQUEST_GPS);

        }
    }

    public void stopUsingGPS() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager != null) {
            locationManager.removeUpdates(gps.this);
        }
    }

    public double getLatitude() {
        Log.i("test",lat+"");
        return lat;
    }

    public double getLongitude() {
        Log.i("test",lon+"");
        return lon;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onLocationChanged(Location location){lon = location.getLongitude(); lat = location.getLatitude();Log.i("location",lon+"   "+lat);}

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
        Log.i("location",provider+"1");
    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
        Log.i("location",provider+"2");
    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
        Log.i("location",provider+"3");
    }
}
