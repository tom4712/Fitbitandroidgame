package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * Created by Resten on 2018-02-19.
 */

public class Splash extends AppCompatActivity {
    private String urlPath = "http://tlgj255.cafe24.com/fitbit/index.php";
    private WebView mWebView;
    String res;
    final Context context = this;
    TextView text;
    private LinearLayout layout;
    public static String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        layout = (LinearLayout) findViewById(R.id.show_lay);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        text = (TextView) findViewById(R.id.text);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl("http://tlgj255.cafe24.com/fitbit/index.php");

        mWebView.setWebViewClient(new WebViewClient() {
            // 페이지 읽기가 시작되었을 때의 동작을 설정한다

            @Override
            public void onPageStarted(WebView view, String url, Bitmap b) {


                if (mWebView.getVisibility() == View.INVISIBLE) {
                    if (url.contains("www.fitbit.com/login")) {
                        show();
                        layout.setVisibility(View.INVISIBLE);
                        mWebView.setVisibility(View.VISIBLE);


                    }
                }
                if (url.contains("?code") && url.contains("&state")) {
                    mWebView.setVisibility(View.INVISIBLE);
                    layout.setVisibility(View.VISIBLE);
                    urlPath = url;


                    try {
                        res = new HttpTask().execute().get();

                  //  res= "이승현|MALE#{\"activities-steps\":[{\"dateTime\":\"2018-02-20\",\"value\":\"7950\"},{\"dateTime\":\"2018-02-21\",\"value\":\"14513\"},{\"dateTime\":\"2018-02-22\",\"value\":\"13020\"},{\"dateTime\":\"2018-02-23\",\"value\":\"7788\"},{\"dateTime\":\"2018-02-24\",\"value\":\"8425\"},{\"dateTime\":\"2018-02-25\",\"value\":\"7209\"},{\"dateTime\":\"2018-02-26\",\"value\":\"807\"}]}@{\"activities-calories\":[{\"dateTime\":\"2018-02-20\",\"value\":\"3038\"},{\"dateTime\":\"2018-02-21\",\"value\":\"3575\"},{\"dateTime\":\"2018-02-22\",\"value\":\"3509\"},{\"dateTime\":\"2018-02-23\",\"value\":\"3053\"},{\"dateTime\":\"2018-02-24\",\"value\":\"2934\"},{\"dateTime\":\"2018-02-25\",\"value\":\"2872\"},{\"dateTime\":\"2018-02-26\",\"value\":\"1513\"}]}";
                            int idx = res.indexOf("|");
                            if (idx != -1) {
                                name = res.substring(0, idx);
                            text.setText(name + "님 환영합니다");
                                Handler mHandler = new Handler();
                                mHandler.postDelayed(new Runnable()
                                {
                                    @Override     public void run()
                                    {
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        intent.putExtra("json_value",res);
                                        startActivity(intent);
                                        finish();

                                    }
                                }, 3000);

                        } else {
                            mWebView.loadUrl("http://tlgj255.cafe24.com/fitbit/index.php");

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }



                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("jsh1", url);

            }

        });

    }


    void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fitbit 로그인");
        builder.setMessage("앱의 사용을 위해서는 Fitbit 게정 로그인이 필요합니다.");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("asdf", "asdf");
                    }
                });

        builder.show();
    }


    public class HttpTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try {
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                //nameValue.add(new BasicNameValuePair("age", "24"));
                //nameValue.add(new BasicNameValuePair("sex", "male"));

                //웹 접속 - utf-8 방식으로
                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                //웹 서버에서 값받기
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

                String total = "";
                String tmp = "";
                //버퍼에있는거 전부 더해주기
                //readLine -> 파일내용을 줄 단위로 읽기
                while ((tmp = reader.readLine()) != null) {
                    if (tmp != null) {
                        total += tmp;
                    }
                }
                im.close();
                //결과창뿌려주기 - ui 변경시 에러
                //result.setText(total);

                return total;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //오류시 null 반환
            return null;
        }

        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);


            Log.d("sdddf", value);
//            int idx = value.indexOf("@");
//           if(idx == -1) {result.setText(value);
////                step = value.substring(0, idx);
////                calories = value.substring(idx + 1);
////
//            }


        }


    }

}
