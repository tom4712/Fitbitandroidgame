package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }

    public void onClick(View view){

        switch (view.getId()){

            case R.id.one:


                show();
                Toast.makeText(this,"버튼 1",Toast.LENGTH_SHORT).show();
                break;

            case R.id.two:
                Toast.makeText(this,"버튼 2  ",Toast.LENGTH_SHORT).show();
                show();
                break;
            case R.id.sre:
                show();
                Toast.makeText(this,"버튼 3",Toast.LENGTH_SHORT).show();
                break;

            case R.id.one_2:
                Toast.makeText(this,"버튼 4",Toast.LENGTH_SHORT).show();
                show();
                break;
            case R.id.two_2:
                show();
                Toast.makeText(this,"버튼 5",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sre_2:
                show();
                Toast.makeText(this,"버튼 6",Toast.LENGTH_SHORT).show();
                break;
            case R.id.one_3:
                Toast.makeText(this,"버튼 7",Toast.LENGTH_SHORT).show();
                show();
                break;
            case R.id.two_3:
                Toast.makeText(this,"버튼 8",Toast.LENGTH_SHORT).show();
                show();
                break;
            case R.id.sre_3:
                Toast.makeText(this,"버튼 9",Toast.LENGTH_SHORT).show();
                show();
                break;
        }
    }
    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("AlertDialog Content");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
}
