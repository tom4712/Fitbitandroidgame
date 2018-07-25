package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Movie extends YouTubeBaseActivity implements  YouTubePlayer.OnInitializedListener{

    long mNow;

    Date mDate;

    int num2;

    private ArrayList<String> list = new ArrayList();

    TextView mTextView;

    Button mRefreshBtn;

    private DBhelper db;

    private Cursor all_cursor;

    private int coinresult;

    ScrollView relativeLayout;



    ProgressBar progressBar;

    int progress=0;

    int i;

    Thread thread;

    ImageButton button1 ;
    YouTubePlayerView youTubeView;
    View view;
    YouTubePlayer.OnInitializedListener listener;
    int num=0;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    Handler handler;

    ImageButton button2 ;

    TextView percent;
    AlertDialog.Builder dialog;

    boolean isPlaying = false;
    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
         num2  = intent.getExtras().getInt("num");

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);

        setContentView(R.layout.activity_movie);
        resultDB();

        //bind listener
//        mRefreshBtn.setOnClickListener(this);
//        mNow = System.currentTimeMillis();
//        mDate = new Date(mNow);
//        if (mDate.getDate()==mDate.getDate()+1){
//
//            num2=0;
//
//        }
        linearLayout = (LinearLayout)findViewById(R.id.RRr);
        linearLayout2=(LinearLayout)findViewById(R.id.gon);
        relativeLayout = (ScrollView) findViewById(R.id.Reeee);


    }
    public void giverewards(){

        Cursul();
        int money = 5;

        money = Integer.parseInt(list.get(0)) + money;

        db.updateCoin(money);
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
                Log.d("DB", "코인값받아옴"+list.get(0));
                if (!all_cursor.moveToNext())
                    break;
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
