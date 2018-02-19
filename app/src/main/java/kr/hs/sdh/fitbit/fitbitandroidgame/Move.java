package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Move extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);




    }
    public void onClick(View view){

        switch (view.getId()){

            case R.id.imagebtn1:

                Intent  i = new Intent(getApplicationContext(), Movie.class);
                startActivity(i);
            break;
        }
    }
}
