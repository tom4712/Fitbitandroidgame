package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Fragmentmain extends AppCompatActivity implements View.OnClickListener {



    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 =  3;
    private TextView cointextviewfrag;

    private Button bt_tab1, bt_tab2,bt_tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentmain);

        final DBhelper dbHelper = new DBhelper(getApplicationContext(), "COIN.db", null, 1);

        Log.d(":asd","셋코인");


        cointextviewfrag = (TextView) findViewById(R.id.cointextviewfrag);
        cointextviewfrag.setText(dbHelper.getResult());
        Log.d("asd", "있던거 써먹음");

        // 위젯에 대한 참조
        bt_tab1 = (Button) findViewById(R.id.bt_tab1);
        bt_tab2 = (Button) findViewById(R.id.bt_tab2);
        bt_tab3 = (Button)  findViewById(R.id.bt_tab3);
        // 탭 버튼에 대한  리스너 연결
        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);
        bt_tab3.setOnClickListener(this);
        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        callFragment(FRAGMENT1);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tab1:
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                callFragment(FRAGMENT1);
                break;

            case R.id.bt_tab2:
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT2);
                break;
            case R.id.bt_tab3:
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT3);
                break;

        }
    }

    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no) {
            case 1:
                // '프래그먼트1' 호출
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                Fragment2 dd = new Fragment2();
                transaction.replace(R.id.fragment_container, dd);
                transaction.commit();
                break;
            case 3:
                // '프래그먼트2' 호출
                Fragment3 dde = new Fragment3();
                transaction.replace(R.id.fragment_container, dde);
                transaction.commit();
                break;
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Fragmentmain.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
