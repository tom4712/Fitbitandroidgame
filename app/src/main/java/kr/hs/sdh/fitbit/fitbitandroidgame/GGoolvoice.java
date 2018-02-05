package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GGoolvoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggoolvoice);


    }

        public void Next3(View view){
        startActivity(new Intent(GGoolvoice.this, GGOOLRANK.class));
    }


}
