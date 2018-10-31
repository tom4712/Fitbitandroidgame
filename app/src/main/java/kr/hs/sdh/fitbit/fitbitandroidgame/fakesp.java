package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class fakesp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fakesp);
    try{
        Thread.sleep(3000);
    }catch (Exception e) {
    }
        Intent i = new Intent(this, Splash.class);
    startActivity(i);
}
}
