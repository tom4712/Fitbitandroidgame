package kr.hs.sdh.fitbit.fitbitandroidgame;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class fitbitAR extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "opencv";

    private Mat matInput;

    private CameraBridgeViewBase mOpenCvCameraView;

    private SurfaceView SurfaceEdge, SurfaceBorder;
    private TextView tv;
    private Button bt, bt2;

    private SensorManager mSensorManager = null;

    private SensorEventListener mSensorEventListener;
    private Sensor mGyroscope = null;

    private double pitch;
    private double roll;

    private double timestamp;
    private double dt;

    private double RAD2DGR = 180 / Math.PI;
    private static final float NS2S = 1.0f / 100000000.0f;

    private float displayX;
    private float displayY;
    private float locationX = 0;
    private float locationY = 0;

    private int centerX;
    private int centerY;
    private int level = 0;
    private int temp;
    private int nowTime;
    private int lastTime;

    private boolean check = false;

    private RelativeLayout.LayoutParams LayoutParams;

    private SharedPreferences SPF;
    private SharedPreferences.Editor editor;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitbit_ar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        displayX = getWindowManager().getDefaultDisplay().getWidth();
        displayY = getWindowManager().getDefaultDisplay().getHeight();
        locationX = displayX / 2;
        locationY = displayY / 2;

        centerX = 2000;
        centerY = 1200;

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorEventListener = new GyroscopeListener();

        mOpenCvCameraView = findViewById(R.id.surface);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setCameraIndex(0);
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        SurfaceBorder = findViewById(R.id.surface_border);
        SurfaceEdge = findViewById(R.id.surface_edge);


        tv = findViewById(R.id.check);
        bt = findViewById(R.id.test);
        bt2 = findViewById(R.id.test2);
        bt2.setVisibility(View.GONE);

        LayoutParams = new android.widget.RelativeLayout.LayoutParams(300 + 5, 300 + 5);
        LayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        LayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        SurfaceEdge.setLayoutParams(LayoutParams);

        LayoutParams = new android.widget.RelativeLayout.LayoutParams(300 - 5, 300 - 5);
        LayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        LayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        SurfaceBorder.setLayoutParams(LayoutParams);

        SPF = getSharedPreferences("clear", MODE_PRIVATE);
        try {
            temp = SPF.getInt("round", 0);
            lastTime = SPF.getInt("time", 0);
        } catch (Exception e) {
            editor = SPF.edit();
            editor.putInt("round", 0);
            editor.putInt("time", nowDate());
            editor.commit();
        }
        nowTime = nowDate();
        if (lastTime != nowTime) {
            editor = SPF.edit();
            editor.remove("round");
            editor.remove("time");

            editor.putInt("round", 0);
            editor.putInt("time", nowDate());
            editor.commit();
            temp = 0;
        }
    }

    private int nowDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("dd");

        return Integer.parseInt(sdf.format(date));
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorEventListener, mGyroscope, SensorManager.SENSOR_DELAY_UI);
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "onResume :: Internal OpenCV library not found.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "onResum :: OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorEventListener);
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        matInput = inputFrame.rgba();
        check = true;
        return matInput;
    }

    private void onVisible() {
        bt.setVisibility(View.GONE);
        bt2.setVisibility(View.VISIBLE);

    }

    private void clear() {
        if (level == (int) ((float) temp * 1.5f) + 2) {
            temp++;
            editor = SPF.edit();
            editor.remove("round");
            editor.putInt("round", temp);
            editor.commit();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        level++;
        if ((int)(Math.random()*2) == 0)
            centerX += (int) (Math.random() * 300) + 500;
        else
            centerX -= (int) (Math.random() * 300) + 500;
        if ((int)(Math.random()*2) == 0)
            centerY += (int) (Math.random() * 300) + 500;
        else
            centerY -= (int) (Math.random() * 300) + 500;
    }

    private class GyroscopeListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_GYROSCOPE:
                    dt = (sensorEvent.timestamp - timestamp) * NS2S;
                    timestamp = sensorEvent.timestamp;
                    if (dt - timestamp * NS2S != 0) {

                /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
                 * 여기까지의 pitch, roll의 단위는 '라디안'이다.
                 * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
                        pitch = pitch + sensorEvent.values[0] * dt;
                        roll = roll + sensorEvent.values[1] * dt;
                        LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                        LayoutParams.setMargins((int) (centerX + pitch * RAD2DGR * 2), (int) (centerY - roll * RAD2DGR * 2), 0, 0);
                        bt.setLayoutParams(LayoutParams);
                    }
                    if (check) {
                        Log.i(TAG, (int) (centerX + pitch * RAD2DGR * 2) + "      " + matInput.size().width);
                        if (matInput.size().width <= (int) (centerX + pitch * RAD2DGR * 2)) {
                            LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                            LayoutParams.setMargins((int) matInput.size().width - 150, (int) (centerY - roll * RAD2DGR * 2), 0, 0);
                            bt2.setLayoutParams(LayoutParams);
                            onVisible();
                        } else if (-150 >= (int) (centerX + pitch * RAD2DGR * 2)) {
                            LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                            LayoutParams.setMargins(0, (int) (centerY - roll * RAD2DGR * 2), 0, 0);
                            bt2.setLayoutParams(LayoutParams);
                            onVisible();
                        } else if (matInput.size().height <= (int) (centerY - roll * RAD2DGR * 2)) {
                            LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                            LayoutParams.setMargins((int) (centerX + pitch * RAD2DGR * 2), (int) matInput.size().height - 150, 0, 0);
                            bt2.setLayoutParams(LayoutParams);
                            onVisible();
                        } else if (-150 >= (int) (centerY - roll * RAD2DGR * 2)) {
                            LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                            LayoutParams.setMargins((int) (centerX + pitch * RAD2DGR * 2), 0, 0, 0);
                            bt2.setLayoutParams(LayoutParams);
                            onVisible();
                        } else {
                            bt.setVisibility(View.VISIBLE);
                            bt2.setVisibility(View.GONE);
                            if ((int) (centerY - roll * RAD2DGR * 2) < locationY && (int) (centerX + pitch * RAD2DGR * 2) < locationX && (int) (centerY - roll * RAD2DGR * 2) > locationY - 150 && (int) (centerX + pitch * RAD2DGR * 2) > locationX - 150)
                                clear();
                        }

                        if (-150 >= (int) (centerY - roll * RAD2DGR * 2) && -150 >= (int) (centerX + pitch * RAD2DGR * 2)) {
                            LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                            LayoutParams.setMargins(0, 0, 0, 0);
                            bt2.setLayoutParams(LayoutParams);
                            onVisible();
                        } else if (-150 >= (int) (centerY - roll * RAD2DGR * 2) && matInput.size().width <= (int) (centerX + pitch * RAD2DGR * 2)) {
                            LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                            LayoutParams.setMargins((int) matInput.size().width - 150, 0, 0, 0);
                            bt2.setLayoutParams(LayoutParams);
                            onVisible();

                        } else if (matInput.size().height <= (int) (centerY - roll * RAD2DGR * 2) && -150 >= (int) (centerX + pitch * RAD2DGR * 2)) {
                            LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                            LayoutParams.setMargins(0, (int) matInput.size().height - 150, 0, 0);
                            bt2.setLayoutParams(LayoutParams);
                            onVisible();
                        } else if (matInput.size().height <= (int) (centerY - roll * RAD2DGR * 2) && matInput.size().width <= (int) (centerX + pitch * RAD2DGR * 2)) {
                            LayoutParams = new RelativeLayout.LayoutParams(150, 150);
                            LayoutParams.setMargins((int) matInput.size().width - 150, (int) matInput.size().height - 150, 0, 0);
                            bt2.setLayoutParams(LayoutParams);
                            onVisible();
                        }


                    }

                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }


}