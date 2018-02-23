package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Share extends Activity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.d("ddd","dddd");


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_share);

    }

}