package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Movie extends AppCompatActivity {
    VideoView vv;
    ProgressBar progressBar;
    int progress=0;
    int i;
    Thread thread;
    Button button1 ;
    int num=0;
    Handler handler;
    Button button2 ;
    TextView percent;
    AlertDialog.Builder dialog;
    boolean isPlaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.mo;

        vv = (VideoView) findViewById(R.id.vv);

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            // 동영상 재생이 완료된후 호출되는 메서드
            public void onCompletion(MediaPlayer player) {
                Toast.makeText(getApplicationContext(), "동영상 재생이 완료되었습니다.",
                        Toast.LENGTH_LONG).show();
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

}
