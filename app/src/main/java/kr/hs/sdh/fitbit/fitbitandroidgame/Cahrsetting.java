package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Cahrsetting extends AppCompatActivity {

    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList(30);
    private int sex;
    private LinearLayout mainLi ;
    private DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cahrsetting);

        Cursul();

        mainLi = findViewById(R.id.mainLinear);

        if(sex == 0){
            mainLi.setBackgroundResource(R.drawable.cahrssetingm);
        }else if(sex == 1){
            mainLi.setBackgroundResource(R.drawable.cahrssetingg);
        }


    }

    public void onClick(View view) {
        mainLi = findViewById(R.id.mainLinear);
        switch (view.getId()) {

            case R.id.changeboy:
                sex = 0;
                mainLi.setBackgroundResource(R.drawable.cahrssetingm);
                break;
            case R.id.changegirl:
                sex = 1;
                mainLi.setBackgroundResource(R.drawable.cahrssetingg);
                break;
            case R.id.backpress:
                finish();
                break;
            case R.id.charsave:
                db.updateSex(sex);
                Log.d("DB","set"+sex);
                Toast.makeText(getApplicationContext(),"저장되었습니다!",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void Cursul() {
        list.clear();
        db = new DBhelper(this);
        db.open();
        all_cursor = db.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                list.add(all_cursor.getString(all_cursor.getColumnIndex("SEX")));
                Log.d("DB", "코인값받아옴");
                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

        }
        sex = Integer.parseInt(list.get(0));
    }
}
