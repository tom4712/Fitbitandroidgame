package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String sql;
    private String dbName = "coin.db";
    int dbVersion = 1;
    private DBhelper dbhelper;

    String a = "600";

    private Cursor all_cursor;

    private LinearLayout mainView, settingView;

    private TextView Coin, charName, versionInfo;
    private Button Settings, Share, thrSec, Game, Shop, Inventory, Inventory_, Cafe, characterDetails, accountManagement;

    private Switch mSwitch;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (mainView.getVisibility() == View.GONE) {
            mainView.setVisibility(View.VISIBLE);
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
        // 인텐트 값 가져옴
        Intent get_intent = new Intent();
        String json = get_intent.getExtras().getString("json_value");
        Log.d("jsh",json);
        //-------------
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
        }else{
            //코인 테스트 끝나면 지우면됩니다.
            dbhelper.updateCoin(5);
        }

        setCoin();

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
        versionInfo = findViewById(R.id.versionInfo);
        mSwitch = findViewById(R.id.Switch);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    dbhelper.updateAlarm(0);
                else
                    dbhelper.updateAlarm(1);

            }
        });

        Settings.setOnClickListener(this);
        Share.setOnClickListener(this);
        thrSec.setOnClickListener(this);
        Game.setOnClickListener(this);
        Shop.setOnClickListener(this);
        Inventory.setOnClickListener(this);
        Cafe.setOnClickListener(this);
        characterDetails.setOnClickListener(this);
        accountManagement.setOnClickListener(this);
        versionInfo.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        dbhelper.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Settings:
                mainView.setVisibility(View.GONE);
                settingView.setVisibility(View.VISIBLE);
                break;

            case R.id.thrSec:
                Intent i = new Intent(getApplicationContext(), Move.class);
                startActivity(i);
                break;
            case R.id.Share:
                Intent intent = new Intent(this, Share.class);
                startActivity(intent);
                break;
            case R.id.Game:
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
        }
    }
    public void setCoin(){
        String a;
        Coin = findViewById(R.id.Coin);

        all_cursor.moveToFirst();
        a = all_cursor.getString(all_cursor.getColumnIndex("COIN"));
        Log.d("qawse",a+"");

        Coin.setText(a+"코인");
        Log.d("DB","텍스트뷰 설정완료함");


    }
}

