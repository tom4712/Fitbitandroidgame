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
    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first)
    {
        super.onApplyThemeResource(theme, resid, first);
        theme.applyStyle(android.R.style.Theme_Panel, true);
    }
    //확인 버튼 클릭
    public void mOnClose(View v) {

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }


    public void mOnCaptureClick(View v){
        Log.d("ddee","눌림");
        //전체화면
        View rootView = getWindow().getDecorView();
        Log.d("ddee","첫줄");
        File screenShot = ScreenShot(rootView);
        Log.d("ddee","메소드 실행");
        if(screenShot!=null){
            Log.d("ddee","저장저장");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
        }
        Log.d("ddee","갤러리저장");
    }

    //화면 캡쳐하기
    public File ScreenShot(View view){
        view.setDrawingCacheEnabled(true);

        Bitmap screenBitmap = view.getDrawingCache();

        String filename = "Fitbit.png";
        File file = new File(Environment.getExternalStorageDirectory()+"/Download", filename);
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        Log.d("ddee","ddddeeeearae");
        view.setDrawingCacheEnabled(false);
        return file;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}