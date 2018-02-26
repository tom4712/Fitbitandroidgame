package kr.hs.sdh.fitbit.fitbitandroidgame;

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

public class Movie4 extends AppCompatActivity {

    private DBhelper db;
    private Cursor all_cursor;
    private int coinresult;

    ScrollView relativeLayout;
    VideoView vv;
    ProgressBar progressBar;
    int progress=0;
    int i;
    Thread thread;
    ImageButton button1 ;
    int num=0;
    Handler handler;
    ImageButton button2 ;
    TextView percent;
    AlertDialog.Builder dialog;
    boolean isPlaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie4);

        resultDB();

        relativeLayout = (ScrollView) findViewById(R.id.Reeee);

        Intent intent = getIntent();
        String num = intent.getStringExtra("num");

        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.m3;

        vv = (VideoView) findViewById(R.id.vv);

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            // 동영상 재생이 완료된후 호출되는 메서드
            public void onCompletion(MediaPlayer player) {

                Toast.makeText(getApplicationContext(), "완료보상으로 3코인이 지급되었습니다!",
                        Toast.LENGTH_LONG).show();
                db.updateCoin(coinresult + 3);
                finish();
            }
        });
        Uri uri = Uri.parse(uriPath);

        vv.setVideoURI(uri);
        button1 = findViewById(R.id.stop);
        button2 = findViewById(R.id.play);
        vv.requestFocus();


    }
    public void onClick(View view) {

        if (view.getId() == R.id.im2) {
            super.onBackPressed();

        }

        if(view.getId()==R.id.Tip){
            relativeLayout.setVisibility(View.VISIBLE);
        }

        if (view.getId() == R.id.stop) {
            vv.pause();

            button2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.INVISIBLE);
        }
        if (view.getId() == R.id.play) {
            vv.start();

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
