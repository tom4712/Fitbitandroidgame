package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    private DBhelper dbHelper = new DBhelper(this, "COIN.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShopActivity.this, MainActivity.class));
        finish();
    }

    public void onClick(View view){

        switch (view.getId()){

            case R.id.one:
                dbHelper.buyitem(1);
                break;

            case R.id.two:
                Toast.makeText(this,"버튼 2  ",Toast.LENGTH_SHORT).show();
                dbHelper.buyitem(2);
                break;
            case R.id.sre:
                Toast.makeText(this,"버튼 3",Toast.LENGTH_SHORT).show();
                dbHelper.buyitem(3);
                break;

            case R.id.one_2:
                Toast.makeText(this,"버튼 4",Toast.LENGTH_SHORT).show();
                dbHelper.buyitem(1);
                break;
            case R.id.two_2:
                dbHelper.buyitem(2);
                Toast.makeText(this,"버튼 5",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sre_2:
                dbHelper.buyitem(3);
                Toast.makeText(this,"버튼 6",Toast.LENGTH_SHORT).show();
                break;
            case R.id.one_3:
                dbHelper.buyitem(1);
                Toast.makeText(this,"버튼 7",Toast.LENGTH_SHORT).show();

                break;
            case R.id.two_3:
                dbHelper.buyitem(2);
                Toast.makeText(this,"버튼 8",Toast.LENGTH_SHORT).show();

                break;
            case R.id.sre_3:
                dbHelper.buyitem(3);
                Toast.makeText(this,"버튼 9",Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
