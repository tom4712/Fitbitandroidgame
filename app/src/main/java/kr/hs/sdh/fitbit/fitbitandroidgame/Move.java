package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Move extends AppCompatActivity implements View.OnClickListener{

    long mNow;
    int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_move);



    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_1:
                super.onBackPressed();
                break;
            case R.id.im1:
                Intent  i = new Intent(getApplicationContext(), Movie.class);
                i.putExtra("num",num);
                num++;
                startActivity(i);
                break;
            case R.id.im2:
                i = new Intent(getApplicationContext(),Movie2.class);
                i.putExtra("num",num);
                num++;
                startActivity(i);
            break;
            case R.id.im3:
                 i = new Intent(getApplicationContext(), Movie3.class);
                i.putExtra("num",num);
                num++;
                startActivity(i);
                break;
            case R.id.im4:
                i = new Intent(getApplicationContext(), Movie4.class);
                i.putExtra("num",num);
                num++;
                i.putExtra("num",3);
                startActivity(i);
                break;
            case R.id.im5:
                i = new Intent(getApplicationContext(), Movie5.class);
                i.putExtra("num",num);
                num++;
                i.putExtra("num",4);
                startActivity(i);
                break;
            case R.id.im6:
                i = new Intent(getApplicationContext(), Movie6.class);
                i.putExtra("num",num);
                num++;
                i.putExtra("num",5);
                startActivity(i);
                break;
            case R.id.im7:

                 i = new Intent(getApplicationContext(), Movie7.class);
                i.putExtra("num",num);
                num++;
                i.putExtra("num",6);
                startActivity(i);
                break;

        }
    }



}

