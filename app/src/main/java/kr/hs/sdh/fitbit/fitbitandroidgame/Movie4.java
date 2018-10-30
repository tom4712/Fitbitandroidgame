package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
public class Movie4 extends YouTubeBaseActivity implements  YouTubePlayer.OnInitializedListener {
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    private DBhelper db;
    private Cursor all_cursor;
    int num2;
    ScrollView relativeLayout;
    int list;

    int i;
    int num=0;
    TextView percent;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie4);
        Intent intent = getIntent();
        num2  = intent.getExtras().getInt("num");

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);

        relativeLayout = (ScrollView) findViewById(R.id.Reeee);
        linearLayout = (LinearLayout)findViewById(R.id.RRr);
        linearLayout2=(LinearLayout)findViewById(R.id.gon);

    }
    public void giverewards(){

        Cursul();
        int money = 8;

        money += list;

        db.updateCoin(money);
    }
    @Override
    protected void onResume() {
        super.onResume();
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        youTubeView.initialize("AIzaSyBnhv56KxpyX8pROVeFqkTbCPAihqwd1_8", this);
        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                giverewards();
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,30000);
    }

    public void Cursul() {
        db = new DBhelper(this);
        db.open();
        all_cursor = db.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                list = all_cursor.getInt(all_cursor.getColumnIndex("COIN"));
                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

        }
    }
    public void onClick(View view) {

        if (view.getId() == R.id.im2) {
            super.onBackPressed();

        }

        if(view.getId()==R.id.Tip){
            relativeLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.INVISIBLE);
        }
        if (view.getId()==R.id.RRr){
            linearLayout2.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }

    }
    @Override

    protected void onUserLeaveHint() {

//여기서 감지
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "홈버튼",
                Toast.LENGTH_LONG).show();
        super.onUserLeaveHint();

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("pggBFcIo4As");
        }
    }

    @SuppressLint("WrongViewCast")
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtubeView);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0,
                                        YouTubeInitializationResult arg1) {
        // TODO Auto-generated method stub

    }
}
