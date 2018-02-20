package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Gumai extends Activity {

    char[] clothchar;
    int Coinresult = 0;
    private DBhelper db;
    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gumai);

        Log.d("POPUP","내가 떳따!");

        Intent intent = getIntent();

        String price = intent.getStringExtra("price");
        String sex = intent.getStringExtra("sex");
        String location = intent.getStringExtra("location");
        String descript = intent.getStringExtra("descript");

        resultDB();

        checkSex(Integer.parseInt(price),Integer.parseInt(sex),Integer.parseInt(location),Integer.parseInt(descript));
    }

    public void cancle(){
        finish();
    }

    public void check(){

    }

    public void resultDB(){
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
            Coinresult = Integer.parseInt(list.get(0));
             String cloth = list.get(1);
            clothchar = cloth.toCharArray();


    }

    public void checkSex(int price,int sex, int location, int descript){
        int indexnum;
        if(sex == 0){//남자
            if(Coinresult < price) {
                Toast.makeText(Gumai.this, "코인이 " + (price - Coinresult) + " 개 부족합니다!", Toast.LENGTH_SHORT).show();
            }else {
                Coinresult = Coinresult - price;
                db.updateCoin(Coinresult);
                if(location == 1){
                    if(descript == 1){
                        indexnum = 1;
                        buyitem(indexnum,price);
                    }else if(descript == 2){
                        indexnum = 2;
                        buyitem(indexnum,price);
                    }else if(descript == 3){
                        indexnum = 3;
                        buyitem(indexnum,price);
                    }
                }else if(location == 2){
                    if(descript == 1){
                        indexnum = 4;
                        buyitem(indexnum,price);
                    }else if(descript == 2){
                        indexnum = 5;
                        buyitem(indexnum,price);
                    }else if(descript == 3){
                        indexnum = 6;
                        buyitem(indexnum,price);
                    }
                }else if(location == 3){
                    if(descript == 1){
                        indexnum = 7;
                        buyitem(indexnum,price);
                    }else if(descript == 2){
                        indexnum = 8;
                        buyitem(indexnum,price);
                    }else if(descript == 3){
                        indexnum = 9;
                        buyitem(indexnum,price);
                    }
                }
            }
        }else if(sex == 1){//여자
            if(Coinresult < price) {
                Toast.makeText(Gumai.this, "코인이 " + (price - Coinresult) + " 개 부족합니다!", Toast.LENGTH_SHORT).show();
            }else {
                if(location == 1){
                    if(descript == 1){
                        indexnum = 1;
                        buyitem(indexnum,price);
                    }else if(descript == 2){
                        indexnum = 2;
                        buyitem(indexnum,price);
                    }else if(descript == 3){
                        indexnum = 3;
                        buyitem(indexnum,price);
                    }
                }else if(location == 2){
                    if(descript == 1){
                        indexnum = 10;
                        buyitem(indexnum,price);
                    }else if(descript == 2){
                        indexnum = 11;
                        buyitem(indexnum,price);
                    }else if(descript == 3){
                        indexnum = 12;
                        buyitem(indexnum,price);
                    }
                }else if(location == 3){
                    if(descript == 1){
                        indexnum = 13;
                        buyitem(indexnum,price);
                    }else if(descript == 2){
                        indexnum = 14;
                        buyitem(indexnum,price);
                    }else if(descript == 3){
                        indexnum = 15;
                        buyitem(indexnum,price);
                    }
                }
            }
        }

    }

    public void buyitem(int indexnum, int price){

        list.clear();
        resultDB();

        Log.d("DB",clothchar[indexnum]+"  ////   ");
        if(clothchar[indexnum] == '1'){
            Log.d("DB","이미구입함");
            Toast.makeText(this, "이미 구입한 상품입니다.", Toast.LENGTH_SHORT).show();
            finish();
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
            list.clear();
            finish();
        }
    }
}