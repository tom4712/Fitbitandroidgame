package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {


    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;

    int width;
    int height;
    char[] clothchar;
    int sex = 1;
    int Coinresult = 0;
    private DBhelper db;
    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList();
    private LinearLayout item_scroll,itempadding1,itempadding2,itempadding3,itempadding4,itempadding5,itempadding6,itempadding7,itempadding8,itempadding9;
    private TextView coindTxv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        item_scroll = findViewById(R.id.item_scroll);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        item_scroll.setLayoutParams(new LinearLayout.LayoutParams(width,height));

        imageView1 = (ImageView) findViewById(R.id.im1);
        imageView2 = (ImageView) findViewById(R.id.im2);
        imageView3 = (ImageView) findViewById(R.id.im3);
        imageView4 = (ImageView) findViewById(R.id.im4);
        imageView5 = (ImageView) findViewById(R.id.im5);
        imageView6 = (ImageView) findViewById(R.id.im6);
        imageView7 = (ImageView) findViewById(R.id.im7);
        imageView8 = (ImageView) findViewById(R.id.im8);
        imageView9 = (ImageView) findViewById(R.id.im9);
        setpadding(width,height);
        Log.d("DB", "asdasdaddsadassadsdasdasdasd");

        db = new DBhelper(this);
        db.open();


        Cursul();


        if(sex==1){
            imageView4.setImageResource(R.drawable.go);
            imageView5.setImageResource(R.drawable.gt);
            imageView6.setImageResource(R.drawable.gr);
            imageView7.setImageResource(R.drawable.gao);
            imageView8.setImageResource(R.drawable.gat);
        }
        else {
            imageView4.setImageResource(R.drawable.mo);
            imageView5.setImageResource(R.drawable.mt);
            imageView6.setImageResource(R.drawable.mr);
            imageView7.setImageResource(R.drawable.mao);
            imageView8.setImageResource(R.drawable.mat);
        }


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

        setCoindTxv();

    }

    public void setCoinresult(){
        //Coinresult = Integer.parseInt(list.get(0));
        Log.d("DB2",""+Coinresult);
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
            list.clear();
            Cursul();
            setCoindTxv();
        }
    }

    public void onClick(View view){
        int price;
        switch (view.getId()){

            case R.id.one:
                price = 1;
                Intent i = new Intent(getApplicationContext(), Gumai.class);
                startActivity(i);
                show(price,sex,1,1);
                break;
            case R.id.two:
                price = 2;
          i = new Intent(getApplicationContext(), Gumai.class);
                startActivity(i);
                show(price,sex,1,2);
                break;
            case R.id.sre:

                price = 3;
                i = new Intent(getApplicationContext(), Gumai.class);
                show(price,sex,1,3);
                break;

            case R.id.one_2:
                price = 1;
                i = new Intent(getApplicationContext(), Gumai.class);
                show(price,sex,2,1);
                break;
            case R.id.two_2:
                price = 2;
                i = new Intent(getApplicationContext(), Gumai.class);
                show(price,sex,2,2);
                break;
            case R.id.sre_2:
                price = 3;
                i = new Intent(getApplicationContext(), Gumai.class);
                show(price,sex,2,3);
                break;
            case R.id.one_3:
                price = 1;
                i = new Intent(getApplicationContext(), Gumai.class);
                show(price,sex,3,1);
                break;
            case R.id.two_3:
                price = 2;
                i = new Intent(getApplicationContext(), Gumai.class);
                show(price,sex,3,2);
                break;
            case R.id.sre_3:
                price = 3;
                i = new Intent(getApplicationContext(), Gumai.class);
                show(price,sex,3,3);
                break;
        }
    }
    private void show(final int price, final int sex, final int location, final int descript) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("구매하실겁니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int indexnum;
                        if(sex == 0){//남자
                            if(Coinresult < price) {
                                Toast.makeText(ShopActivity.this, "코인이 " + (price - Coinresult) + " 개 부족합니다!", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ShopActivity.this, "코인이 " + (price - Coinresult) + " 개 부족합니다!", Toast.LENGTH_SHORT).show();
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
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public  void setpadding (int width,int height){
        coindTxv = findViewById(R.id.cointext);
        itempadding1 = findViewById(R.id.item_padding1);
        itempadding2 = findViewById(R.id.item_padding2);
        itempadding3 = findViewById(R.id.item_padding3);
        itempadding4 = findViewById(R.id.item_padding4);
        itempadding5 = findViewById(R.id.item_padding5);
        itempadding6 = findViewById(R.id.item_padding6);
        itempadding7 = findViewById(R.id.item_padding7);
        itempadding8 = findViewById(R.id.item_padding8);
        itempadding9 = findViewById(R.id.item_padding9);

        LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams parmcoin = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        parmcoin.leftMargin = width / 10;
        parmcoin.bottomMargin = height / 130;

        parm.rightMargin = width / 6;
        parm.leftMargin = width / 6;

        coindTxv.setLayoutParams(parmcoin);
        itempadding1.setLayoutParams(parm);
        itempadding2.setLayoutParams(parm);
        itempadding3.setLayoutParams(parm);
        itempadding4.setLayoutParams(parm);
        itempadding5.setLayoutParams(parm);
        itempadding6.setLayoutParams(parm);
        itempadding7.setLayoutParams(parm);
        itempadding8.setLayoutParams(parm);
        itempadding9.setLayoutParams(parm);

    }

    public void setCoindTxv(){
        coindTxv = findViewById(R.id.cointext);
        coindTxv.setText(list.get(0));
    }


}