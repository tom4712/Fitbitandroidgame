package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.renderscript.Short4;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    View container;
    FileOutputStream fos;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String adrss = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + formatter+"capture.jpeg";

    private String sql;
    private String dbName = "coin.db";
    int dbVersion = 1;
    private DBhelper dbhelper;
    private DBhelper db;
    private ArrayList<String> list = new ArrayList();

    private Cursor all_cursor;

    private LinearLayout mainView, settingView,Mainchar;

    private TextView Coin, charName, versionInfo;
    private Button Share, thrSec, Game, Shop, Inventory, Cafe, characterDetails, accountManagement, goMain;
    private ImageButton Settings;
    private Switch mSwitch;

    Toolbar myToolbar;
    ViewPager viewPager = null;
    TextView a;
    private final long FINISH_INTERVAL_TIME = 2000;

    private long backPressedTime = 0;

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
            setContentView(R.layout.activity_main2);
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
        setCustomActionBar();
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        a = (TextView)findViewById(R.id.text_back);
        a.setText("133");
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        // 찾기
        goMain = findViewById(R.id.goMain);
        settingView = findViewById(R.id.settingView);
        Settings = findViewById(R.id.Settings);
        Share = findViewById(R.id.Share);
        thrSec = findViewById(R.id.thrSec);
        Game = findViewById(R.id.Game);
        Shop = findViewById(R.id.Shop);

        Cafe = findViewById(R.id.Cafe);
        characterDetails = findViewById(R.id.characterDetails);
        accountManagement = findViewById(R.id.accountManagement);
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

        goMain.setOnClickListener(this);
        Settings.setOnClickListener(this);
        Share.setOnClickListener(this);
        thrSec.setOnClickListener(this);
        Game.setOnClickListener(this);
        Shop.setOnClickListener(this);
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
                Intent intent1 = new Intent(getApplicationContext(), MapGame.class);
                startActivity(intent1);
                break;
            case R.id.Shop:
                i = new Intent(getApplicationContext(), ShopActivity.class);

                startActivity(i);
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
                i = new Intent(getApplicationContext(), Charsetting.class);
                startActivity(i);
                break;
            case R.id.goMain:
                thrSec.setClickable(true);
                Game.setClickable(true);

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
    public void setCustomActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        View mCustomView = LayoutInflater.from(this).inflate(R.layout.main_top_layout,null);
        actionBar.setCustomView(mCustomView);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView,params);
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

    public void Cursul() {
        list.clear();
        db = new DBhelper(this);
        db.open();
        all_cursor = db.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                list.add(all_cursor.getString(all_cursor.getColumnIndex("COIN")));
                Log.d("DB", "코인값받아옴");
                list.add(all_cursor.getString(all_cursor.getColumnIndex("SEX")));
                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

        }
    }

    public void setcharback(){
        Mainchar = findViewById(R.id.mainchar);
        if(Integer.parseInt(list.get(0)) == 0){

            Mainchar.setBackgroundResource(R.drawable.maincharm);
        }
        if (Integer.parseInt(list.get(1)) == 1)
        {
            Mainchar.setBackgroundResource(R.drawable.maincharg);
        }
    }

    public void Wearitem(){

    }

}


