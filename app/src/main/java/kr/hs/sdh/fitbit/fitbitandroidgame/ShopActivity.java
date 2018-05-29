package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.hs.sdh.fitbit.fitbitandroidgame.shopitem.ShopfragmentTop;
import kr.hs.sdh.fitbit.fitbitandroidgame.shopitem.ShopfragmentTop2;
import kr.hs.sdh.fitbit.fitbitandroidgame.shopitem.ShopfragmentTop3;
import kr.hs.sdh.fitbit.fitbitandroidgame.shopitem.pagerAdapter2;
import kr.hs.sdh.fitbit.fitbitandroidgame.shopitem.pagerAdapter3;

public class ShopActivity extends AppCompatActivity {


    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;

    ViewPager vp,vp2,vp3;

    int width;
    int height;
    char[] clothchar;
    int sex = 0;
    int Coinresult = 0;
    private DBhelper db;
    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList();
    private LinearLayout item_scroll, itempadding1, itempadding2, itempadding3, itempadding4, itempadding5, itempadding6, itempadding7, itempadding8, itempadding9, shopallback;
    private TextView coindTxv;
    private Button[] buyvibutton = new Button[9];
    private Button[] Setvibutton = new Button[9];

    public static Activity shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shop = ShopActivity.this;

        Cursul();
        sex = Integer.parseInt(list.get(2));
        shopallback = findViewById(R.id.shopallback);

        if(sex == 0){
            shopallback.setBackgroundResource(R.drawable.shopbackgroundman);
        }
        if(sex == 1){
            shopallback.setBackgroundResource(R.drawable.shopbagroundgirl);
        }

                try {
            if (Integer.parseInt(list.get(0)) == 0) {
                db.updateCoin(100);
            }
        } catch (Exception e) {

        }
        list.clear();
        Cursul();
        setCoinTxv();



        vp = findViewById(R.id.container1);
        vp2 = findViewById(R.id.container2);
        vp3 = findViewById(R.id.container3);
        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp2.setAdapter(new pagerAdapter2(getSupportFragmentManager()));
        vp3.setAdapter(new pagerAdapter3(getSupportFragmentManager()));
        vp.setCurrentItem(0);
        vp2.setCurrentItem(0);
        vp3.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int tag;
                switch (v.getId()){
                    case R.id.container1:
                        tag = (int) v.getTag();
                        Log.d("TAG",tag+"");
                        vp.setCurrentItem(tag);
                        break;
                    case R.id.container2:
                        tag = (int) v.getTag();

                        Log.d("TAG",tag+"");
                        vp2.setCurrentItem(tag);
                        break;
                    case R.id.container3:
                        tag = (int) v.getTag();

                        Log.d("TAG",tag+"");
                        vp3.setCurrentItem(tag);
                        break;

                }
            }
        };
        Log.d("DB", "asdasdaddsadassadsdasdasdasd");
    }

    private class pagerAdapter extends FragmentStatePagerAdapter {



        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {



            switch (position) {
                case 0:
                    return new ShopfragmentTop();
                case 1:
                    return new ShopfragmentTop2();
//                case 2:
//                    return new ShopfragmentTop3();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
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
                list.add(all_cursor.getString(all_cursor.getColumnIndex("COIN")));
                Log.d("DB", "코인값받아옴"+list.get(0));
                list.add(all_cursor.getString(all_cursor.getColumnIndex("GARMENTS")));
                Log.d("DB", "옷값받아옴");
                list.add(all_cursor.getString(all_cursor.getColumnIndex("SEX")));
                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

        }
    }


        public void setCoinTxv() {
        list.clear();
        Cursul();

        coindTxv = findViewById(R.id.cointext);
        coindTxv.setText(list.get(0));
    }
    public void setcoinother(){
        list.clear();
        Cursul();

        coindTxv = findViewById(R.id.cointext);
        coindTxv.setText(list.get(0));
    }
    public void backspace(View view){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();


    }


}



//        db = new DBhelper(this);
//        db.open();
//
//
//        Cursul();
//
//        sex = Integer.parseInt(list.get(2));
//        Log.d("DB", "sex "+list.get(2) + "");
//
//        try {
//            if (Integer.parseInt(list.get(0)) == 0) {
//                db.updateCoin(100);
//            }
//        } catch (Exception e) {
//
//        }
//
//
//        if (sex == 1) {
//            imageView4.setImageResource(R.drawable.go);
//            imageView5.setImageResource(R.drawable.gt);
//            imageView6.setImageResource(R.drawable.gr);
//            imageView7.setImageResource(R.drawable.gao);
//            imageView8.setImageResource(R.drawable.gat);
//            imageView9.setImageResource(R.drawable.gar);
//
//        } else {
//            imageView4.setImageResource(R.drawable.mo);
//            imageView5.setImageResource(R.drawable.mt);
//            imageView6.setImageResource(R.drawable.mr);
//            imageView7.setImageResource(R.drawable.mao);
//            imageView8.setImageResource(R.drawable.mat);
//            imageView9.setImageResource(R.drawable.mat);
//        }
//
//        if(sex == 0){
//            shopallback.setBackgroundResource(R.drawable.shopbackgroundman);
//        }else if(sex == 1){
//            shopallback.setBackgroundResource(R.drawable.shopbagroundgirl);
//        }
//
//
//        try {
//            if (Integer.parseInt(list.get(1)) == 0) {
//                db.updateGarments("1000000000000000");
//            }
//        } catch (Exception e) {
//
//        }
//        Log.d("DB", list.get(0) + "");
//        Coinresult = Integer.parseInt(list.get(0));
//        sex = Integer.parseInt(list.get(2));
//        Log.d("DB", "sex "+list.get(2) + "");
//        //setCoinresult();
//
//        Cursul();
//        String cloth = list.get(1);
//        clothchar = cloth.toCharArray();
//
//        list.clear();
//        Cursul();
//        Cehcksetwaer();
//        SetCehck();
//        setCoinTxv();
//
//    }
//
//
//
//    public void setCoinresult() {
//        //Coinresult = Integer.parseInt(list.get(0));
//        Log.d("DB2", "" + Coinresult);
//    }
//
//    public void Cursul() {
//        list.clear();
//        db = new DBhelper(this);
//        db.open();
//        all_cursor = db.AllRows();
//        all_cursor.moveToFirst();
//        while (true) {
//            try {
//                list.add(all_cursor.getString(all_cursor.getColumnIndex("COIN")));
//                Log.d("DB", "코인값받아옴");
//                list.add(all_cursor.getString(all_cursor.getColumnIndex("GARMENTS")));
//                Log.d("DB", "옷값받아옴");
//                list.add(all_cursor.getString(all_cursor.getColumnIndex("SEX")));
//                if (!all_cursor.moveToNext())
//                    break;
//            } catch (Exception e) {
//
//            }
//
//        }
//    }
//
//    public void buyitem() {
//
//        //setCoinTxv();
//    }
//
//
//    public void onClick(View view) {
//        int price;
//        switch (view.getId()) {
//
//            case R.id.one:
//                price = 1;
//                Intent i = new Intent(getApplicationContext(), Gumai.class);
//                i.putExtra("price", "" + price);
//                i.putExtra("sex", "" + sex);
//                i.putExtra("location", "" + 1);
//                i.putExtra("descript", "" + 1);
//                startActivity(i);
//                onPause();
//                setCoinTxv();
//                finish();
//                break;
//            case R.id.two:
//                price = 2;
//                Intent i2 = new Intent(getApplicationContext(), Gumai.class);
//                i2.putExtra("price", "" + price);
//                i2.putExtra("sex", "" + sex);
//                i2.putExtra("location", "" + 1);
//                i2.putExtra("descript", "" + 2);
//                startActivity(i2);
//                onPause();
//                setCoinTxv();
//                finish();
//                break;
//            case R.id.sre:
//
//                price = 3;
//                Intent i3 = new Intent(getApplicationContext(), Gumai.class);
//                i3.putExtra("price", "" + price);
//                i3.putExtra("sex", "" + sex);
//                i3.putExtra("location", "" + 1);
//                i3.putExtra("descript", "" + 3);
//                startActivity(i3);
//                onPause();
//                setCoinTxv();
//                finish();
//                break;
//
//            case R.id.one_2:
//                price = 1;
//                i = new Intent(getApplicationContext(), Gumai.class);
//                i.putExtra("price", "" + price);
//                i.putExtra("sex", "" + sex);
//                i.putExtra("location", "" + 2);
//                i.putExtra("descript", "" + 1);
//
//                startActivity(i);
//                onPause();
//                setCoinTxv();
//                finish();
//                break;
//            case R.id.two_2:
//                price = 2;
//                i = new Intent(getApplicationContext(), Gumai.class);
//                i.putExtra("price", "" + price);
//                i.putExtra("sex", "" + sex);
//                i.putExtra("location", "" + 2);
//                i.putExtra("descript", "" + 2);
//                startActivity(i);
//                onPause();
//                setCoinTxv();
//                finish();
//                break;
//            case R.id.sre_2:
//                price = 3;
//                i = new Intent(getApplicationContext(), Gumai.class);
//                i.putExtra("price", "" + price);
//                i.putExtra("sex", "" + sex);
//                i.putExtra("location", "" + 2);
//                i.putExtra("descript", "" + 3);
//                startActivity(i);
//                onPause();
//                setCoinTxv();
//                finish();
//                break;
//            case R.id.one_3:
//                price = 1;
//                i = new Intent(getApplicationContext(), Gumai.class);
//                i.putExtra("price", "" + price);
//                i.putExtra("sex", "" + sex);
//                i.putExtra("location", "" + 3);
//                i.putExtra("descript", "" + 1);
//                startActivity(i);
//                onPause();
//                setCoinTxv();
//                break;
//            case R.id.two_3:
//                price = 2;
//                i = new Intent(getApplicationContext(), Gumai.class);
//                i.putExtra("price", "" + price);
//                i.putExtra("sex", "" + sex);
//                i.putExtra("location", "" + 3);
//                i.putExtra("descript", "" + 2);
//                startActivity(i);
//                onPause();
//                setCoinTxv();
//                finish();
//                break;
//            case R.id.sre_3:
//                price = 3;
//                i = new Intent(getApplicationContext(), Gumai.class);
//                i.putExtra("price", "" + price);
//                i.putExtra("sex", "" + sex);
//                i.putExtra("location", "" + 3);
//                i.putExtra("descript", "" + 3);
//                startActivity(i);
//                onPause();
//                setCoinTxv();
//                finish();
//                break;
//            case R.id.set1:
//                checkinner(1,1);
//                SetCehck();
//                break;
//            case R.id.set12:
//                checkinner(1,2);
//                SetCehck();
//                break;
//            case R.id.set13:
//                checkinner(1,3);
//                SetCehck();
//                break;
//            case R.id.set2:
//                checkinner(2,1);
//                SetCehck();
//                break;
//            case R.id.set22:
//                checkinner(2,2);
//                SetCehck();
//                break;
//            case R.id.set23:
//                checkinner(2,3);
//                SetCehck();
//                break;
//            case R.id.set3:
//                checkinner(3,1);
//                SetCehck();
//                break;
//            case R.id.set32:
//                checkinner(3,2);
//                SetCehck();
//                break;
//            case R.id.set33:
//                checkinner(3,3);
//                SetCehck();
//                break;
//        }
//    }
//
//    public void setpadding(int width, int height) {
//        coindTxv = findViewById(R.id.cointext);
//        itempadding1 = findViewById(R.id.item_padding1);
//        itempadding2 = findViewById(R.id.item_padding2);
//        itempadding3 = findViewById(R.id.item_padding3);
//        itempadding4 = findViewById(R.id.item_padding4);
//        itempadding5 = findViewById(R.id.item_padding5);
//        itempadding6 = findViewById(R.id.item_padding6);
//        itempadding7 = findViewById(R.id.item_padding7);
//        itempadding8 = findViewById(R.id.item_padding8);
//        itempadding9 = findViewById(R.id.item_padding9);
//
//        LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        LinearLayout.LayoutParams parmcoin = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//
//        parmcoin.leftMargin = width / 10;
//        parmcoin.bottomMargin = height / 130;
//
//        parm.rightMargin = width / 6;
//        parm.leftMargin = width / 6;
//
//        coindTxv.setLayoutParams(parmcoin);
//        itempadding1.setLayoutParams(parm);
//        itempadding2.setLayoutParams(parm);
//        itempadding3.setLayoutParams(parm);
//        itempadding4.setLayoutParams(parm);
//        itempadding5.setLayoutParams(parm);
//        itempadding6.setLayoutParams(parm);
//        itempadding7.setLayoutParams(parm);
//        itempadding8.setLayoutParams(parm);
//        itempadding9.setLayoutParams(parm);
//
//    }
//
//    public void setCoinTxv() {
//        list.clear();
//        Cursul();
//
//        coindTxv = findViewById(R.id.cointext);
//        coindTxv.setText(list.get(0));
//    }
//
//    public void setCoinTxv2() {
//        coindTxv = findViewById(R.id.cointext);
//        coindTxv.setText(Coinresult);
//    }
//
//    public void Cehcksetwaer() {
//        buyvibutton[0] = findViewById(R.id.one);
//        buyvibutton[1] = findViewById(R.id.two);
//        buyvibutton[2] = findViewById(R.id.sre);
//        buyvibutton[3] = findViewById(R.id.one_2);
//        buyvibutton[4] = findViewById(R.id.two_2);
//        buyvibutton[5] = findViewById(R.id.sre_2);
//        buyvibutton[6] = findViewById(R.id.one_3);
//        buyvibutton[7] = findViewById(R.id.two_3);
//        buyvibutton[8] = findViewById(R.id.sre_3);
//
//        Setvibutton[0] = findViewById(R.id.set1);
//        Setvibutton[1] = findViewById(R.id.set12);
//        Setvibutton[2] = findViewById(R.id.set13);
//        Setvibutton[3] = findViewById(R.id.set2);
//        Setvibutton[4] = findViewById(R.id.set22);
//        Setvibutton[5] = findViewById(R.id.set23);
//        Setvibutton[6] = findViewById(R.id.set3);
//        Setvibutton[7] = findViewById(R.id.set32);
//        Setvibutton[8] = findViewById(R.id.set33);
//
//
//        for (int i = 1; i < clothchar.length; i++) {
//            if (sex == 0) {
//                if (i >= 1 && i <= 9) {
//                    if (clothchar[i] == '1' || clothchar[i] == '2') {
//                        buyvibutton[i - 1].setVisibility(View.GONE);
//                        Log.d("DB", "곤곤곤곤곤곤곤곤곤곤" + i);
//                        Setvibutton[i - 1].setVisibility(View.VISIBLE);
//                        Log.d("DB", "비짖을비짖을 비짖을 비지증ㄹ");
//                    }
//                }
//            }
//
//            if(sex == 1){
//                if (i >= 1 && i <= 9) {
//                    if (clothchar[i] == '1' || clothchar[i] == '2') {
//                        if(i == 10){
//                            buyvibutton[3].setVisibility(View.GONE);
//                            Setvibutton[3].setVisibility(View.VISIBLE);
//                        }else if(i == 11){
//                            buyvibutton[4].setVisibility(View.GONE);
//                            Setvibutton[4].setVisibility(View.VISIBLE);
//                        }else if(i == 12){
//                            buyvibutton[5].setVisibility(View.GONE);
//                            Setvibutton[5].setVisibility(View.VISIBLE);
//                        }else if(i == 13){
//                            buyvibutton[6].setVisibility(View.GONE);
//                            Setvibutton[6].setVisibility(View.VISIBLE);
//                        }else if(i == 14){
//                            buyvibutton[7].setVisibility(View.GONE);
//                            Setvibutton[7].setVisibility(View.VISIBLE);
//                        }else if(i == 15){
//                            buyvibutton[8].setVisibility(View.GONE);
//                            Setvibutton[8].setVisibility(View.VISIBLE);
//                        }
//
//                    }
//                }
//            }
//        }
//    }
//
//    public void checkinner(int posision,int descript){
//        if(sex == 0){
//            if (posision == 1){
//                if(descript == 1){
//                    if (clothchar[2] == '2'){
//                        clothchar[2] = '1';
//                    }
//                    if (clothchar[3] == '2'){
//                        clothchar[3] = '1';
//                    }
//                    clothchar[1] = '2';
//                }
//                if(descript == 2){
//                    if (clothchar[1] == '2'){
//                        clothchar[1] = '1';
//                    }
//                    if (clothchar[3] == '2'){
//                        clothchar[3] = '1';
//                    }
//                    clothchar[2] = '2';
//                }
//                if(descript == 3){
//                    if (clothchar[1] == '2'){
//                        clothchar[1] = '1';
//                    }
//                    if (clothchar[2] == '2'){
//                        clothchar[2] = '1';
//                    }
//                    clothchar[3] = '2';
//                }
//            }
//            if (posision == 2){
//                if(descript == 1){
//                    if (clothchar[5] == '2'){
//                        clothchar[5] = '1';
//                    }
//                    if (clothchar[6] == '2'){
//                        clothchar[6] = '1';
//                    }
//                    clothchar[4] = '2';
//                }
//                if(descript == 2){
//                    if (clothchar[4] == '2'){
//                        clothchar[4] = '1';
//                    }
//                    if (clothchar[6] == '2'){
//                        clothchar[6] = '1';
//                    }
//                    clothchar[5] = '2';
//                }
//                if(descript == 3){
//                    if (clothchar[4] == '2'){
//                        clothchar[4] = '1';
//                    }
//                    if (clothchar[5] == '2'){
//                        clothchar[5] = '1';
//                    }
//                    clothchar[6] = '2';
//                }
//            }
//            if (posision == 3){
//                if(descript == 1){
//                    if (clothchar[8] == '2'){
//                        clothchar[8] = '1';
//                    }
//                    if (clothchar[9] == '2'){
//                        clothchar[9] = '1';
//                    }
//                    clothchar[7] = '2';
//                }
//                if(descript == 2){
//                    if (clothchar[7] == '2'){
//                        clothchar[7] = '1';
//                    }
//                    if (clothchar[9] == '2'){
//                        clothchar[9] = '1';
//                    }
//                    clothchar[8] = '2';
//                }
//                if(descript == 3){
//                    if (clothchar[7] == '2'){
//                        clothchar[7] = '1';
//                    }
//                    if (clothchar[8] == '2'){
//                        clothchar[8] = '1';
//                    }
//                    clothchar[9] = '2';
//                }
//            }
//        }
//        if(sex == 1){
//            if (posision == 1){
//                if(descript == 1){
//                    if (clothchar[2] == '2'){
//                        clothchar[2] = '1';
//                    }
//                    if (clothchar[3] == '2'){
//                        clothchar[3] = '1';
//                    }
//                    clothchar[1] = '2';
//                }
//                if(descript == 2){
//                    if (clothchar[1] == '2'){
//                        clothchar[1] = '1';
//                    }
//                    if (clothchar[3] == '2'){
//                        clothchar[3] = '1';
//                    }
//                    clothchar[2] = '2';
//                }
//                if(descript == 3){
//                    if (clothchar[1] == '2'){
//                        clothchar[1] = '1';
//                    }
//                    if (clothchar[2] == '2'){
//                        clothchar[2] = '1';
//                    }
//                    clothchar[3] = '2';
//                }
//            }
//            if (posision == 2){
//                if(descript == 1){
//                    if (clothchar[11] == '2'){
//                        clothchar[11] = '1';
//                    }
//                    if (clothchar[12] == '2'){
//                        clothchar[12] = '1';
//                    }
//                    clothchar[10] = '2';
//                }
//                if(descript == 2){
//                    if (clothchar[10] == '2'){
//                        clothchar[10] = '1';
//                    }
//                    if (clothchar[12] == '2'){
//                        clothchar[12] = '1';
//                    }
//                    clothchar[11] = '2';
//                }
//                if(descript == 3){
//                    if (clothchar[10] == '2'){
//                        clothchar[10] = '1';
//                    }
//                    if (clothchar[11] == '2'){
//                        clothchar[11] = '1';
//                    }
//                    clothchar[12] = '2';
//                }
//            }
//            if (posision == 3){
//                if(descript == 1){
//                    if (clothchar[14] == '2'){
//                        clothchar[14] = '1';
//                    }
//                    if (clothchar[15] == '2'){
//                        clothchar[15] = '1';
//                    }
//                    clothchar[13] = '2';
//                }
//                if(descript == 2){
//                    if (clothchar[13] == '2'){
//                        clothchar[13] = '1';
//                    }
//                    if (clothchar[15] == '2'){
//                        clothchar[15] = '1';
//                    }
//                    clothchar[14] = '2';
//                }
//                if(descript == 3){
//                    if (clothchar[13] == '2'){
//                        clothchar[13] = '1';
//                    }
//                    if (clothchar[14] == '2'){
//                        clothchar[14] = '1';
//                    }
//                    clothchar[15] = '2';
//                }
//            }
//        }
//        String input = "";
//        for (int i = 0; i < clothchar.length; i++) {
//            input += Character.toString(clothchar[i]);
//        }
//        db.updateGarments(input);
//        Toast.makeText(this, "창착이 완료 되었습니다!", Toast.LENGTH_SHORT).show();
//        Log.d("DB",""+input);
//
//    }
//
//    public void SetCehck(){
//
//        Setvibutton[0] = findViewById(R.id.set1);
//        Setvibutton[1] = findViewById(R.id.set12);
//        Setvibutton[2] = findViewById(R.id.set13);
//        Setvibutton[3] = findViewById(R.id.set2);
//        Setvibutton[4] = findViewById(R.id.set22);
//        Setvibutton[5] = findViewById(R.id.set23);
//        Setvibutton[6] = findViewById(R.id.set3);
//        Setvibutton[7] = findViewById(R.id.set32);
//        Setvibutton[8] = findViewById(R.id.set33);
//
//        Cursul();
//        String cloth = list.get(1);
//        clothchar = cloth.toCharArray();
//
//
//        for (int i = 1; i < clothchar.length; i++) {
//            if (sex == 0) {
//                if (i >= 1 && i <= 9) {
//                    if (clothchar[i] == '2') {
//                        Setvibutton[i - 1].setText("착용중");
//                        Log.d("DB", "착용했다!");
//                    }
//                    if (clothchar[i] == '1') {
//                        Setvibutton[i - 1].setText("착용");
//                        Log.d("DB", "착용했다!");
//                    }
//
//                }
//            }
//            if (sex == 1) {
//                if (i == 1 || i== 2|| i== 3|| i== 10|| i== 11|| i== 12|| i== 13|| i== 14|| i== 15) {
//                    if (clothchar[i] == '2') {
//                        Setvibutton[i - 1].setText("착용중");
//                    }
//                    if (clothchar[i] == '1') {
//                        Setvibutton[i - 1].setText("착용");
//                    }
//
//                }
//            }
//
//        }
//
//    }
//
//
//    public void back(View view){
//        finish();
//    }
//
//}