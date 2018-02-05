package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String sql;
    private String dbName = "coin.db";
    int dbVersion = 1;
    private DBhelper dbhelper;
    private TextView cointextview;

    String a = "600";



    private BottomNavigationView bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final DBhelper dbHelper = new DBhelper(getApplicationContext(), "COIN.db", null, 1);

            dbHelper.insert(a);
        try {
            dbHelper.insert(a);
            Log.d("asd", "처음접속함");
            cointextview = (TextView) findViewById(R.id.cointextview);
            cointextview.setText(dbHelper.getResult());

        }catch (Exception e){
            cointextview.setText(dbHelper.getResult());
            Log.d("asd","있던거 써먹음");
        }




        bottomView = (BottomNavigationView) findViewById(R.id.bottombarnav);
        bottomView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottombaritem_calls:
                                Toast.makeText(MainActivity.this, "머리아프다", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.bottombaritem_recents:
                                Toast.makeText(MainActivity.this, "퀘스트", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Popup.class));
                                return true;
                            case R.id.bottombaritem_trips:
                                startActivity(new Intent(MainActivity.this, ShopActivity.class));
                                return true;
                        }
                        return false;
                    }
                });



    }

         public void Next(View view){
            Intent intent = new Intent(MainActivity.this,CommunityActivity.class);
            startActivity(intent);
            finish();
        }
    public void Next2(View view){
        Intent intent = new Intent(MainActivity.this,GGoolvoice.class);
        startActivity(intent);
        finish();
    }


    }
