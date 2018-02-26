package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.renderscript.Short4;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static kr.hs.sdh.fitbit.fitbitandroidgame.fitbitAR.PERMISSION_REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    View container;
    FileOutputStream fos;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String adrss = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + formatter+"capture.jpeg";

    private String sql;
    private String dbName = "coin.db";
    int dbVersion = 1;
    private DBhelper dbhelper;

    String a = "600";

    private Cursor all_cursor;

    private LinearLayout mainView, settingView;

    private TextView Coin, charName, versionInfo;

    private Button Settings, Share, thrSec, Game, Shop, Inventory, Cafe, characterDetails, accountManagement, goMain;

    private Switch mSwitch;

    private final long FINISH_INTERVAL_TIME = 2000;

    private long backPressedTime = 0;
    String[] PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE"};

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
                    Toast.makeText(getApplicationContext(), "CAMERA PERMISSION FAIL", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }
    @Override
    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();

        long intervalTime = tempTime - backPressedTime;

        if (settingView.getVisibility() == View.VISIBLE) {

            thrSec.setClickable(true);

            Game.setClickable(true);

            Inventory.setClickable(true);

            Shop.setClickable(true);

            settingView.setVisibility(View.GONE);

        } else if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {

            super.onBackPressed();

        } else {

            backPressedTime = tempTime;

            Toast.makeText(getApplicationContext(), "한번 더 누를 시 종료됩니다.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new DBhelper(getApplication());
        dbhelper.open();
        all_cursor = dbhelper.AllRows();
        all_cursor.moveToFirst();
        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        boolean first = pref.getBoolean("isFirst", false);
        if (first == false) {
            dbhelper.insertGarbage();
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
        } else {
            //코인 테스트 끝나면 지우면됩니다.
            dbhelper.updateCoin(5);
        }


        goMain = findViewById(R.id.goMain);
        mainView = findViewById(R.id.mainView);
        settingView = findViewById(R.id.settingView);
        Coin = findViewById(R.id.Coin);
        charName = findViewById(R.id.charName);
        Settings = findViewById(R.id.Settings);
        Share = findViewById(R.id.Share);
        thrSec = findViewById(R.id.thrSec);
        Game = findViewById(R.id.Game);
        Shop = findViewById(R.id.Shop);
        Inventory = findViewById(R.id.Inventory);
        Cafe = findViewById(R.id.Cafe);
        characterDetails = findViewById(R.id.characterDetails);
        accountManagement = findViewById(R.id.accountManagement);
        mSwitch = findViewById(R.id.Switch);

        // 인텐트 값 가져옴 - 이름 설정
        //Intent get_value = getIntent();
        //String json = get_value.getStringExtra("json_value");
        //int idx = json.indexOf("|");
        String name = "닉네임";
        charName.setText(name);
        //-------------
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    dbhelper.updateAlarm(0);
                else
                    dbhelper.updateAlarm(1);

            }
        });

        goMain.setOnClickListener(this);
        Settings.setOnClickListener(this);
        Share.setOnClickListener(this);
        thrSec.setOnClickListener(this);
        Game.setOnClickListener(this);
        Shop.setOnClickListener(this);
        Inventory.setOnClickListener(this);
        Cafe.setOnClickListener(this);
        characterDetails.setOnClickListener(this);
        accountManagement.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        dbhelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            setCoin();
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.Settings:
                thrSec.setClickable(false);
                Game.setClickable(false);
                Inventory.setClickable(false);
                Shop.setClickable(false);
                settingView.setVisibility(View.VISIBLE);
                break;

            case R.id.thrSec:
                Intent i = new Intent(getApplicationContext(), Move.class);
                startActivity(i);
                break;
            case R.id.Share:
          oo(view);
                break;
            case R.id.Game:
                Intent intent1 = new Intent(getApplicationContext(), fitbitAR.class);
                startActivity(intent1);
                break;
            case R.id.Shop:
                i = new Intent(getApplicationContext(), ShopActivity.class);

                startActivity(i);
                break;
            case R.id.Inventory:
                break;
            case R.id.accountManagement:
                i = new Intent(getApplicationContext(), profileManagement.class);
                startActivity(i);
                break;
            case R.id.Cafe:
                i = new Intent(getApplicationContext(), CommunityActivity.class);
                startActivity(i);
                break;
            case R.id.characterDetails:
                break;
            case R.id.goMain:
                thrSec.setClickable(true);
                Game.setClickable(true);
                Inventory.setClickable(true);
                Shop.setClickable(true);
                settingView.setVisibility(View.GONE);
                break;
        }
    }

    public void setCoin() {
        String a;
        Coin = findViewById(R.id.Coin);


        all_cursor.moveToFirst();

        a = all_cursor.getString(all_cursor.getColumnIndex("COIN"));

        Log.d("qawse", a + "");


        Coin.setText(a + "코인");

        Log.d("DB", "텍스트뷰 설정완료함");


    }
    public void oo(View view){

        container = getWindow().getDecorView();

        container.buildDrawingCache();

        Bitmap captureView = container.getDrawingCache();

        try{

            fos = new FileOutputStream(adrss);

            captureView.compress(Bitmap.CompressFormat.JPEG,100,fos);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(new File(adrss));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent,"공유"));
    }


}


