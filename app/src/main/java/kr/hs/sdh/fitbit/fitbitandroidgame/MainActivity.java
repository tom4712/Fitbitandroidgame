package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String sql;
    private String dbName = "coin.db";
    int dbVersion = 1;
    private DBhelper dbhelper;

    String a = "600";


    private LinearLayout mainView, settingView;

    private TextView Coin, charName, versionInfo;
    private Button Settings, Share, thrSec, Game, Shop, Inventory, Inventory_, Cafe, characterDetails, accountManagement, characterSex;

    private Spinner spinner;
    private ArrayAdapter spinnerAdapter;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;
        if(mainView.getVisibility() == View.GONE) {
            mainView.setVisibility(View.VISIBLE);
            settingView.setVisibility(View.GONE);
        }
        else
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누를 시 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Inventory_ = findViewById(R.id.Inventory_);
        Cafe = findViewById(R.id.Cafe);
        characterDetails = findViewById(R.id.characterDetails);
        accountManagement = findViewById(R.id.accountManagement);
        versionInfo = findViewById(R.id.versionInfo);
        characterSex = findViewById(R.id.characterSex);
        spinner = findViewById(R.id.spinner);

        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.characterSex,
                android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        Settings.setOnClickListener(this);
        Share.setOnClickListener(this);
        thrSec.setOnClickListener(this);
        Game.setOnClickListener(this);
        Shop.setOnClickListener(this);
        Inventory.setOnClickListener(this);
        Inventory_.setOnClickListener(this);
        Cafe.setOnClickListener(this);
        characterDetails.setOnClickListener(this);
        accountManagement.setOnClickListener(this);
        versionInfo.setOnClickListener(this);
        characterSex.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Settings:
                mainView.setVisibility(View.GONE);
                settingView.setVisibility(View.VISIBLE);
                break;
            case R.id.Share:
                break;
            case R.id.thrSec:
                break;
            case R.id.Game:
                break;
            case R.id.Shop:
                Intent i = new Intent(getApplicationContext(), ShopActivity.class);
                startActivity(i);
                break;
            case R.id.Inventory:
                break;
            case R.id.Inventory_:
                break;
            case R.id.accountManagement:
                break;
            case R.id.Cafe:
                break;
            case R.id.characterDetails:
                break;
            case R.id.characterSex:
                break;
        }
    }
}
