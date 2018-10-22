package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
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

public class Movie extends YouTubeBaseActivity implements  YouTubePlayer.OnInitializedListener{

    int num2;

    private int list;

    private DBhelper db;

    private Cursor all_cursor;
    ScrollView relativeLayout;
    View view;
    int num=0;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;

    TextView percent;
    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
         num2  = intent.getExtras().getInt("num");

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);

        setContentView(R.layout.activity_movie);
        linearLayout = (LinearLayout)findViewById(R.id.RRr);
        linearLayout2=(LinearLayout)findViewById(R.id.gon);
        relativeLayout = (ScrollView) findViewById(R.id.Reeee);


    }
    public void giverewards(){

        Cursul();
        int money = 5;

        money += list;

        db.updateCoin(money);
    }

    public void Cursul() {
        db = new DBhelper(this);
        db.open();
        all_cursor = db.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                list = all_cursor.getInt(all_cursor.getColumnIndex("COIN"));
                Log.d("DB", "코인값받아옴"+list);
                if (!all_cursor.moveToNext()) {
                    break;
                }
            } catch (Exception e) {

            }

        }
    }



    public void onClick(View view) {

        if (view.getId() == R.id.im2) { // 백버튼
            super.onBackPressed();

        }
        if (view.getId() == R.id.youtubebutton) { // 백버튼
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
            if(view.getId()==R.id.Tip){ // 팁버튼
            relativeLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.INVISIBLE);
        }
        if (view.getId()==R.id.RRr){ //팁버튼에서 그외 레이아웃 클릭
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
            player.cueVideo("IZJwvlPioQs");
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
