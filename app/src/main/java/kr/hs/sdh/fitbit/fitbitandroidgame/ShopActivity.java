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
        //setCoinresult();

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

    public void buyitem(){
            list.clear();
            Cursul();
            setCoindTxv();
        }


    public void onClick(View view){
        int price;
        switch (view.getId()){

            case R.id.one:
                price = 1;
                Intent i = new Intent(getApplicationContext(), Gumai.class);
                i.putExtra("price",""+price);
                i.putExtra("sex",""+sex);
                i.putExtra("location",""+1);
                i.putExtra("descript",""+1);
                startActivity(i);
                buyitem();
                break;
            case R.id.two:
                price = 2;
                Intent i2 = new Intent(getApplicationContext(), Gumai.class);
                i2.putExtra("price",""+price);
                i2.putExtra("sex",""+sex);
                i2.putExtra("location",""+1);
                i2.putExtra("descript",""+1);
                startActivity(i2);
                buyitem();
                break;
            case R.id.sre:

                price = 3;
                Intent i3 = new Intent(getApplicationContext(), Gumai.class);
                i3.putExtra("price",""+price);
                i3.putExtra("sex",""+sex);
                i3.putExtra("location",""+1);
                i3.putExtra("descript",""+1);
                startActivity(i3);
                buyitem();
                break;

            case R.id.one_2:
                price = 1;
                i = new Intent(getApplicationContext(), Gumai.class);
                i.putExtra("price",""+price);
                i.putExtra("sex",""+sex);
                i.putExtra("location",""+1);
                i.putExtra("descript",""+1);
                startActivity(i);
                buyitem();
                break;
            case R.id.two_2:
                price = 2;
                i = new Intent(getApplicationContext(), Gumai.class);
                i.putExtra("price",""+price);
                i.putExtra("sex",""+sex);
                i.putExtra("location",""+1);
                i.putExtra("descript",""+1);
                startActivity(i);
                buyitem();
                break;
            case R.id.sre_2:
                price = 3;
                i = new Intent(getApplicationContext(), Gumai.class);
                i.putExtra("price",""+price);
                i.putExtra("sex",""+sex);
                i.putExtra("location",""+1);
                i.putExtra("descript",""+1);
                startActivity(i);
                buyitem();
                break;
            case R.id.one_3:
                price = 1;
                i = new Intent(getApplicationContext(), Gumai.class);
                i.putExtra("price",""+price);
                i.putExtra("sex",""+sex);
                i.putExtra("location",""+1);
                i.putExtra("descript",""+1);
                startActivity(i);
                buyitem();
                break;
            case R.id.two_3:
                price = 2;
                i = new Intent(getApplicationContext(), Gumai.class);
                i.putExtra("price",""+price);
                i.putExtra("sex",""+sex);
                i.putExtra("location",""+1);
                i.putExtra("descript",""+1);
                startActivity(i);
                buyitem();
                break;
            case R.id.sre_3:
                price = 3;
                i = new Intent(getApplicationContext(), Gumai.class);
                i.putExtra("price",""+price);
                i.putExtra("sex",""+sex);
                i.putExtra("location",""+1);
                i.putExtra("descript",""+1);
                startActivity(i);
                buyitem();
                break;
        }
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