package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class Move extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        Log.d("dddd","동영상리스트");
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_1:
                super.onBackPressed();
                break;
            case R.id.im1:
                Intent  i = new Intent(getApplicationContext(), Movie.class);
                i.putExtra("num",7);
                startActivity(i);
                break;
            case R.id.im2:
                i = new Intent(getApplicationContext(),Movie2.class);
                startActivity(i);
            break;
            case R.id.im3:
                 i = new Intent(getApplicationContext(), Movie3.class);
                startActivity(i);
                break;
            case R.id.im4:
                i = new Intent(getApplicationContext(), Movie4.class);
                i.putExtra("num",3);
                startActivity(i);
                break;
            case R.id.im5:
                i = new Intent(getApplicationContext(), Movie5.class);
                i.putExtra("num",4);
                startActivity(i);
                break;
            case R.id.im6:
                i = new Intent(getApplicationContext(), Movie6.class);
                i.putExtra("num",5);
                startActivity(i);
                break;
            case R.id.im7:

                 i = new Intent(getApplicationContext(), Movie7.class);
                i.putExtra("num",6);
                startActivity(i);
                break;

        }
    }
}
