package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapGame extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng myPosition;
    private MarkerOptions myMarker, target;

    private gps gps;

    private SupportMapFragment mapFragment;

    private double lastLat = 0, lastLon = 0, targetLat, targetLon, clearLat, clearLon;

    private boolean isFirst = true, isTarget;

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
        if ((int) (Math.random() * 2) == 1)
            pointLat = (int) (Math.random() * 5) + 3;
        else
            pointLat = (int) (Math.random() * 5) - 8;
        if ((int) (Math.random() * 2) == 1)
            pointLon = (int) (Math.random() * 5) + 3;
        else
            pointLon = (int) (Math.random() * 5) - 8;
        targetLat = lastLat + ((double) pointLat / 10000);
        targetLon = lastLon + ((double) pointLon / 10000);
        target = new MarkerOptions().position(new LatLng(targetLat, targetLon));
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
            clearLat = (targetLat - lastLat) * 10000;
            clearLon = (targetLon - lastLon) * 10000;

            Log.i("clear", clearLat+"  "+clearLon);

            if (clearLon + clearLat <= 2 && clearLon + clearLat >= -2)
                Toast.makeText(this, "clear", Toast.LENGTH_SHORT).show();
        }

        if (target != null)
            this.mMap.addMarker(target).showInfoWindow();
    }
}