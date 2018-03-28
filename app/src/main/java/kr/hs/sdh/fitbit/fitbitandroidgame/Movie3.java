package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Movie3 extends YouTubeBaseActivity {
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    private DBhelper db;
    private Cursor all_cursor;
    private int coinresult;
    int num2;
    ScrollView relativeLayout;
    VideoView vv;  YouTubePlayer.OnInitializedListener listener;
    ProgressBar progressBar;
    int progress=0;
    int i;
    Thread thread;
    YouTubePlayerView youTubeView;
            ImageButton button1 ;
    int num=0;
    Handler handler;
    ImageButton button2 ;
    TextView percent;
    AlertDialog.Builder dialog;
    boolean isPlaying = false;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie3);
        Intent intent = getIntent();
        num2  = intent.getExtras().getInt("num");

        resultDB();
        youTubeView = (YouTubePlayerView)findViewById(R.id.youtubeView);
        relativeLayout = (ScrollView) findViewById(R.id.Reeee);
        linearLayout = (LinearLayout)findViewById(R.id.RRr);
        linearLayout2=(LinearLayout)findViewById(R.id.gon);
        relativeLayout = (ScrollView) findViewById(R.id.Reeee);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("vqV9a13LImY");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


//
//        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//            // 동영상 재생이 완료된후 호출되는 메서드
//            public void onCompletion(MediaPlayer player) {
//
//
//                if(num2>=3){
//
//                    Toast.makeText(getApplicationContext(), "3번 이상 시청하셨습니다",
//                            Toast.LENGTH_LONG).show();
//
//                }
//                if(num2<3){
//
//
//                    Toast.makeText(getApplicationContext(), "완료보상으로 3코인이 지급되었습니다!",
//
//                            Toast.LENGTH_LONG).show();
//
//
//                    db.updateCoin(coinresult + 3);
//                }
//
//                finish();
//
//            }
//        });



        button1 = findViewById(R.id.stop);
        button2 = findViewById(R.id.play);



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



        if (view.getId() == R.id.stop) {

            button2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.INVISIBLE);
        }
        if (view.getId() == R.id.play) {
            youTubeView.initialize("AIzaSyAi6zpxpA00Yk3_VJNDzIveO7k_q_fV_mc",listener);

            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.INVISIBLE);
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

    public void resultDB(){
        db = new DBhelper(this);
        db.open();
        all_cursor = db.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                coinresult = Integer.parseInt(all_cursor.getString(all_cursor.getColumnIndex("COIN")));
                Log.d("DB", "코인값받아옴");
                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

        }

    }


}
