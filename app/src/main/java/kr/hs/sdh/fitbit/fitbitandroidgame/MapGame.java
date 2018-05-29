package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import kr.hs.sdh.fitbit.fitbitandroidgame.MainActivity;

public class MapGame extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng myPosition;
    private MarkerOptions myMarker;
    private MarkerOptions[] target = new MarkerOptions[5];

    private gps gps;

    private SupportMapFragment mapFragment;

    private double lastLat = 0, lastLon = 0, clearLat, clearLon;
    private double[] targetLat = new double[5], targetLon = new double[5];

    private boolean isFirst = true, isTarget;

    private DBhelper db;
    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList();

    private int pointLat, pointLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_game);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        isTarget = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gps = new gps(this);
        isFirst = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        gps.stopUsingGPS();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (lastLat != gps.getLatitude() || lastLon != gps.getLongitude()) {
            setMarker();
        }

        if (isFirst) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
            isFirst = false;
        }

        mapFragment.getMapAsync(this);
    }

    private void makeTarget() {
        for(int i = 0; i < 5; i++) {
            if ((int) (Math.random() * 2) == 1)
                pointLat = (int) (Math.random() * 5) + 5;
            else
                pointLat = (int) (Math.random() * 5) - 10;
            if ((int) (Math.random() * 2) == 1)
                pointLon = (int) (Math.random() * 5) + 5;
            else
                pointLon = (int) (Math.random() * 5) - 10;
            targetLat[i] = lastLat + ((double) pointLat / 10000);
            targetLon[i] = lastLon + ((double) pointLon / 10000);
            target[i] = new MarkerOptions().position(new LatLng(targetLat[i], targetLon[i]));
        }
        Toast.makeText(getApplicationContext(), "타겟 생성", Toast.LENGTH_SHORT).show();
        isTarget = false;
    }

    private void setMarker() {
        mMap.clear();


        lastLat = gps.getLatitude();
        lastLon = gps.getLongitude();

        myPosition = new LatLng(lastLat, lastLon);
        myMarker = new MarkerOptions().position(myPosition).title("MyPosition");
        this.mMap.addMarker(myMarker).showInfoWindow();
        if (isTarget)
            makeTarget();
        else{
            for(int i = 0; i < 5; i++) {
                clearLat = (targetLat[i] - lastLat) * 10000;
                clearLon = (targetLon[i] - lastLon) * 10000;

                if (clearLon + clearLat <= 2 && clearLon + clearLat >= -2){
                    AlertDialog.Builder di = new AlertDialog.Builder(MapGame.this);
                    di.setTitle("ARgame");
                    di.setMessage("목표 도착 보상으로 4코인 지급 되었습니다!");
                    di.setCancelable(false);
                    di.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.updateCoin(4);
                            finishend();
                        }
                    });
                    di.show();


                }
            }
        }

        if (target != null)
            for(int i = 0; i < 5; i++)
                this.mMap.addMarker(target[i]).showInfoWindow();
    }

    public void finishend(){
        Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i1);
        finish();
    }
}