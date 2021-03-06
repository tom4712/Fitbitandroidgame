package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    View container;
    FileOutputStream fos;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String adrss = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + formatter + "capture.jpeg";

    private DBhelper dbhelper;
    private TextView coindTxv;int num = 40;
    private ArrayList<String> list = new ArrayList(num);
    static final int PICK_CONTACT_REQUEST = 1;
    private Cursor all_cursor;

    private LinearLayout settingView, gameList;

    private TextView Coin;

    private Button Share, thrSec, Game, Shop, Cafe, characterDetails, goMain, ox_question_move, map_move, ar_move, btnhistory,help;

    private ImageButton Settings;

    private Switch mSwitch;

    ViewPager viewPager = null;

    TextView a;

    private final long FINISH_INTERVAL_TIME = 2000;

    private long backPressedTime = 0;

    static final int PERMISSION_REQUEST_CODE = 1;
    static int[] arrayvalue_night = new int[7];
    static int[] arrayvalue_step = new int[7];
    static int[] arrayvalue_caloris = new int[7];

    private void requestNecessaryPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                //퍼미션을 거절했을 때 메시지 출력 후 종료
                if (!hasPermissions(PERMISSIONS)) {
                }
                Intent i;
                if(permissions == PERMISSIONS2) {
                    i = new Intent(getApplicationContext(), fitbitAR.class);
                    startActivity(i);
                }
                if(permissions == PERMISSIONS ) {
                    i = new Intent(getApplicationContext(), MapGame.class);
                    startActivity(i);
                }
                return;
            }
        }
    }
    String[] PERMISSIONS = {"android.permission.ACCESS_COARSE_LOCATION"};
    String[] PERMISSIONS2 = {"android.permission.CAMERA"};
    String[] PERMISSIONS3 ={"android.permission.WRITE_EXTERNAL_STORAGE"};
    String[] PERMISSIONS4 = {"android.permission.READ_EXTERNAL_STORAGE"};

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

    @Override

    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (settingView.getVisibility() == View.VISIBLE) {
            thrSec.setClickable(true);
            Game.setClickable(true);
            Shop.setClickable(true);
            settingView.setVisibility(View.GONE);
        } else if (gameList.getVisibility() == View.VISIBLE) {
            thrSec.setClickable(true);
            Game.setClickable(true);
            Shop.setClickable(true);
            gameList.setVisibility(View.GONE);

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
        if (!hasPermissions(PERMISSIONS3)) {
            requestNecessaryPermissions(PERMISSIONS3);
        }
        if (!hasPermissions(PERMISSIONS4)) {
            requestNecessaryPermissions(PERMISSIONS4);
        }
        ox_question_move = findViewById(R.id.ox_question_move);
        map_move = findViewById(R.id.map_move);
        ar_move = findViewById(R.id.ar_move);
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
        }
        setCustomActionBar();
        viewPager = findViewById(R.id.viewpager);
        Cursul();
        setCoinTxv();
        // 정상현 - 그래프 파일 가져오기
        String res = File_reader();
        String[] split = res.split("@");
        ListjsonParser_step(split[0]);
        ListjsonParser_caloris(split[1]);
        ListjsonParser_night(split[2]);
        ////////////////////////////////////
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        // 찾기
        gameList = findViewById(R.id.gameList);
        goMain = findViewById(R.id.goMain);
        settingView = findViewById(R.id.settingView);
        Settings = findViewById(R.id.Settings);
        Share = findViewById(R.id.Share);
        thrSec = findViewById(R.id.thrSec);
        Game = findViewById(R.id.Game);
        Shop = findViewById(R.id.Shop);
        help = findViewById(R.id.help);

        Cafe = findViewById(R.id.Cafe);
        characterDetails = findViewById(R.id.characterDetails);
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

        ox_question_move.setOnClickListener(this);
        map_move.setOnClickListener(this);
        ar_move.setOnClickListener(this);
        goMain.setOnClickListener(this);
        Settings.setOnClickListener(this);
        Share.setOnClickListener(this);
        thrSec.setOnClickListener(this);
        Game.setOnClickListener(this);
        Shop.setOnClickListener(this);
        Cafe.setOnClickListener(this);
        characterDetails.setOnClickListener(this);
        help.setOnClickListener(this);
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

        setCoinTxv();
    }
    public void onPause() {
        super.onPause();

    }

    public void ddd(View vie){
        dbhelper.updateCoin(100);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnHistory:
                setCoinTxv();
                break;

            case R.id.ar_move:
                if (!hasPermissions(PERMISSIONS2)) {
                    requestNecessaryPermissions(PERMISSIONS2);
                }else {
                    i = new Intent(getApplicationContext(), fitbitAR.class);
                    startActivity(i);
                }
                break;
            case R.id.ox_question_move:
                i = new Intent(getApplicationContext(), qusetionActivity.class);
                startActivity(i);
                break;
            case R.id.map_move:
                if (!hasPermissions(PERMISSIONS)) {
                    requestNecessaryPermissions(PERMISSIONS);
                    if (hasPermissions(PERMISSIONS)) {
                        i = new Intent(getApplicationContext(), MapGame.class);
                        startActivity(i);
                    }
                }else {
                    i = new Intent(getApplicationContext(), MapGame.class);
                    startActivity(i);
                }
                break;
            case R.id.Settings:
                thrSec.setClickable(false);
                Game.setClickable(false);
                Shop.setClickable(false);
                settingView.setVisibility(View.VISIBLE);
                break;

            case R.id.thrSec:
               i = new Intent(getApplicationContext(), Move.class);
                startActivity(i);
                break;
            case R.id.Share:
                try{
                    oo(view);
                }catch (Exception e){
                    Toast.makeText(this, "미구현입니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Game:
                thrSec.setClickable(false);
                Game.setClickable(false);
                Share.setClickable(false);
                Shop.setClickable(false);

                gameList.setVisibility(View.VISIBLE);
                break;
            case R.id.close:
                thrSec.setClickable(true);
                Game.setClickable(true);
                Share.setClickable(true);
                Shop.setClickable(true);

                gameList.setVisibility(View.INVISIBLE);
                break;
            case R.id.Shop:
                onPause();
                i = new Intent(MainActivity.this,ShopActivity.class);
                startActivityForResult(i, PICK_CONTACT_REQUEST);
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
                Share.setClickable(true);
                Shop.setClickable(true);
                settingView.setVisibility(View.GONE);
                break;
            case R.id.help:
                i = new Intent(getApplicationContext(), help.class);
                startActivity(i);
                break;
        }


    }
    public void Cursul() {
        list.clear();
        dbhelper = new DBhelper(this);
        dbhelper.open();
        all_cursor = dbhelper.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                list.add(all_cursor.getString(all_cursor.getColumnIndex("COIN")));
                Log.d("DB", "코인값받아옴"+list.get(0));
                list.add(all_cursor.getString(all_cursor.getColumnIndex("GARMENTS")));
                Log.d("DB", "옷값받아옴");
                list.add(all_cursor.getString(all_cursor.getColumnIndex("SEX")));
                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {


                list.clear();
                Cursul();
                onResume();
                coindTxv = findViewById(R.id.text_back);
                coindTxv.setText(list.get(0));
            }
        }
    }
    public void setCoinTxv() {
        list.clear();
        Cursul();

        coindTxv = findViewById(R.id.text_back);
        coindTxv.setText(list.get(0));
    }
    public void setcoinother(){
        list.clear();
        Cursul();

        coindTxv = findViewById(R.id.text_back);
        coindTxv.setText(list.get(0));
    }
    public void setCoin() {
        String a;
        Coin = findViewById(R.id.Coin);


        all_cursor.moveToFirst();

        a = all_cursor.getString(all_cursor.getColumnIndex("COIN"));





        Coin.setText(a + "코인");

        Log.d("DB", "텍스트뷰 설정완료함");


    }

    public void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        View mCustomView = LayoutInflater.from(this).inflate(R.layout.main_top_layout, null);
        actionBar.setCustomView(mCustomView);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, params);
    }

    public void oo(View view) {

        container = getWindow().getDecorView();

        container.buildDrawingCache();

        Bitmap captureView = container.getDrawingCache();

        try {

            fos = new FileOutputStream(adrss);

            captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(new File(adrss));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent chooser = Intent.createChooser(intent, "공유");
        startActivity(chooser);
    }
    //정상현 - 그래프용 JSON

    String File_reader(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+"text.txt"));
            String readStr = "";
            String str = null;
            while(((str = br.readLine()) != null)){
                readStr += str +"\n";
            }
            br.close();
            return readStr.substring(0,readStr.length()-1);


        }catch (FileNotFoundException e){
            e.printStackTrace();

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ListjsonParser_step(String jsonString) {
        int idx =jsonString.indexOf("|");
        String step = jsonString.substring(idx+1);
        int code = 0;
        Log.d("data",step);

        try {
            JSONArray jarray = new JSONObject(step).getJSONArray("activities-steps");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);
                code = jObject.optInt("value");
                arrayvalue_step[i] = code;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    public void ListjsonParser_caloris(String jsonString) {

        int code = 0;
        String date = null;

        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("activities-calories");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);
                code = jObject.optInt("value");
                arrayvalue_caloris[i] = code;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    public void ListjsonParser_night(String jsonString) {

        int code = 0;
        String date = null;

        try {

            JSONArray jarray = new JSONObject(jsonString).getJSONArray("sleep-time");
            int idx = 0;
            if(jarray.length()>7){
                idx = 7;
            }else{
                idx = 7;
            }
            for (int i = idx-1; i >-1; i--) {
                JSONObject jObject = jarray.getJSONObject(i);
                code = jObject.optInt("value");
                arrayvalue_night[i] = code;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


}


