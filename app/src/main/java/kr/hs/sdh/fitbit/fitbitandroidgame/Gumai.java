package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Gumai extends Activity {

    char[] clothchar;
    int sex = 1;
    int Coinresult = 0;
    private DBhelper db;
    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gumai);
        Log.d("DB", "asdasdaddsadassadsdasdasdasd");

        db = new DBhelper(this);
        db.open();


        Cursul();



        Log.d("DB", "아시발 좀 길게 나와라"+list.get(1));
        try {
            if (Integer.parseInt(list.get(1)) == 0) {
                db.updateGarments("1000000000000000");
            }
        }catch (Exception e){

        }
        Log.d("DB", list.get(0) + "");
        Coinresult = Integer.parseInt(list.get(0));
        setCoinresult();

        Cursul();

        String cloth = list.get(3);
        clothchar = cloth.toCharArray();

        Log.d("DB", "아시발 좀 길게 나와라   "+list.get(0));
        Log.d("DB", "아시발 좀 길게 나asd와라   "+list.get(3));


    }

    public void setCoinresult(){
        //Coinresult = Integer.parseInt(list.get(0));
        Log.d("DB2",""+Coinresult);
    }

    public void onClick(View view){

        switch (view.getId()){

            case R.id.btn1:

                break;
            case R.id.btn2:
                break;
        }
    }
    public void Cursul(){
        db = new DBhelper(this);
        db.open();
        all_cursor = db.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                list.add(all_cursor.getString(all_cursor.getColumnIndex("COIN")));
                Log.d("DB", "코인값받아옴");
                list.add(all_cursor.getString(all_cursor.getColumnIndex("GARMENTS")));
                Log.d("DB", "옷값받아옴");
                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

        }
    }


    public void buyitem(int indexnum, int price){

        list.clear();
        Cursul();

        Log.d("DB",clothchar[indexnum]+"  ////   ");
        if(clothchar[indexnum] == '1'){
            Log.d("DB","이미구입함");
            Toast.makeText(this, "이미 구입한 상품입니다.", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("DB","이미구입한 상품이 아님");
            String input = "";
            Coinresult = Coinresult - price;
            clothchar[indexnum] = 49;
            db.updateCoin(Coinresult);
            for (int i = 0; i < clothchar.length; i++) {
                input += Character.toString(clothchar[i]);
            }
            db.updateGarments(input);
            Log.d("DB",""+input);
            Toast.makeText(this, "구입을 성공하였습니다!!", Toast.LENGTH_SHORT).show();
        }
    }





}