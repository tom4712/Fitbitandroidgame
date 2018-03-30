package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class beforeGPS extends AppCompatActivity {

    private Button setPermission;
    private Spinner min, hour;
    private SharedPreferences time;
    private int hourI, minI;

    static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS = {"android.permission.ACCESS_COARSE_LOCATION"};
    String[] PERMISSIONS2 = {"android.permission.ACCESS_FINE_LOCATION"};
    String[] PERMISSIONS3 = {"android.permission.INTERNET"};

    private boolean hasPermissions(String[] permissions) {
        // 퍼미션 확인
        int result = -1;
        for (int i = 0; i < permissions.length; i++) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[i]);
        }
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            return false;
        }
    }


    private void requestNecessaryPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                //퍼미션을 거절했을 때 메시지 출력 후 종료
                if (!hasPermissions(PERMISSIONS)) {
                    Toast.makeText(getApplicationContext(), "퍼미션을 승인하셔야 합니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_gps);
        setPermission = findViewById(R.id.setPermission);
        setPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(PERMISSIONS)) {
                    requestNecessaryPermissions(PERMISSIONS);
                }
                if (!hasPermissions(PERMISSIONS2)) {
                    requestNecessaryPermissions(PERMISSIONS2);
                }
                if (!hasPermissions(PERMISSIONS3)) {
                    requestNecessaryPermissions(PERMISSIONS3);
                }
                time = getSharedPreferences("MapGameFirst", 0);
                SharedPreferences.Editor tE = time.edit();
                tE.putBoolean("MapGameFirst", false);

                startAlarm();
                Intent i = new Intent(getApplicationContext(), MapGame.class);
                startActivity(i);
            }
        });
        min = findViewById(R.id.min);
        hour = findViewById(R.id.hour);
        time = getSharedPreferences("hour", 0);
        hourI = time.getInt("hour", 18);
        hour.setSelection(hourI);
        hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = getSharedPreferences("hour", 0);
                SharedPreferences.Editor tE = time.edit();
                tE.putInt("hour", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        min = findViewById(R.id.min);
        time = getSharedPreferences("min", 0);
        minI = time.getInt("min", 6);
        min.setSelection(minI);
        min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = getSharedPreferences("min", 0);
                SharedPreferences.Editor tE = time.edit();
                tE.putInt("min", i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void startAlarm(){
        new setAlarm(getApplicationContext()).alarm();
    }

    public class setAlarm {
        Context context;

        public setAlarm(Context context) {
            this.context = context;
        }
        private int nowHour() {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("hh");

            return Integer.parseInt(sdf.format(date));
        }
        private int nowMin() {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("mm");

            return Integer.parseInt(sdf.format(date));
        }

        public void alarm() {
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, BroadcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(context, 0, i, 0);
            Calendar calendar = Calendar.getInstance();
            int hour = time.getInt("hour",16),min = time.getInt("min",6)*5;
            if(hour < nowHour()) {
                if (min < nowMin()) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, min, 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+1, hour, min, 0);
               }
            } else{
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+1, hour, min, 0);
            }
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        }
    }
}
