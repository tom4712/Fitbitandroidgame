package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Gumai extends Activity {

    ShopActivity shop;
    Button check, cancle;
    char[] clothchar;
    int Coinresult = 0;
    private DBhelper db;
    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList();
    private String price, sex, location, descript;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gumai);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Log.d("POPUP", "내가 떳따!");

        Intent intent = getIntent();

        price = intent.getStringExtra("price");
        sex = intent.getStringExtra("sex");
        location = intent.getStringExtra("location");
        descript = intent.getStringExtra("descript");
        Log.d("Gumai", "price " + price + "  sex" + sex + "  location " + location + "  descript " + descript);

        resultDB();


    }

    public void nocheck(View view) {
        Toast.makeText(Gumai.this, "취소되었습니다!!", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void check(View view) {
        checkSex(Integer.parseInt(price), Integer.parseInt(sex), Integer.parseInt(location), Integer.parseInt(descript));
    }

    public void resultDB() {
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

    public void checkSex(int price, int sex, int location, int descript) {
        int indexnum;
        if (sex == 0) {//남자
            if (Coinresult < price) {
                Toast.makeText(Gumai.this, "코인이 " + (price - Coinresult) + " 개 부족합니다!", Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                if (location == 1) {
                    if (descript == 1) {
                        indexnum = 1;
                        buyitem(indexnum, price);
                    } else if (descript == 2) {
                        indexnum = 2;
                        buyitem(indexnum, price);
                    } else if (descript == 3) {
                        indexnum = 3;
                        buyitem(indexnum, price);
                    }
                } else if (location == 2) {
                    if (descript == 1) {
                        indexnum = 4;
                        buyitem(indexnum, price);
                    } else if (descript == 2) {
                        indexnum = 5;
                        buyitem(indexnum, price);
                    } else if (descript == 3) {
                        indexnum = 6;
                        buyitem(indexnum, price);
                    }
                } else if (location == 3) {
                    if (descript == 1) {
                        indexnum = 7;
                        buyitem(indexnum, price);
                    } else if (descript == 2) {
                        indexnum = 8;
                        buyitem(indexnum, price);
                    } else if (descript == 3) {
                        indexnum = 9;
                        buyitem(indexnum, price);
                    }
                }
            }
        } else if (sex == 1) {//여자
            if (Coinresult < price) {
                Toast.makeText(Gumai.this, "코인이 " + (price - Coinresult) + " 개 부족합니다!", Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                if (location == 1) {
                    if (descript == 1) {
                        indexnum = 1;
                        buyitem(indexnum, price);
                    } else if (descript == 2) {
                        indexnum = 2;
                        buyitem(indexnum, price);
                    } else if (descript == 3) {
                        indexnum = 3;
                        buyitem(indexnum, price);
                    }
                } else if (location == 2) {
                    if (descript == 1) {
                        indexnum = 10;
                        buyitem(indexnum, price);
                    } else if (descript == 2) {
                        indexnum = 11;
                        buyitem(indexnum, price);
                    } else if (descript == 3) {
                        indexnum = 12;
                        buyitem(indexnum, price);
                    }
                } else if (location == 3) {
                    if (descript == 1) {
                        indexnum = 13;
                        buyitem(indexnum, price);
                    } else if (descript == 2) {
                        indexnum = 14;
                        buyitem(indexnum, price);
                    } else if (descript == 3) {
                        indexnum = 15;
                        buyitem(indexnum, price);
                    }
                }
            }
        }

    }

    public void buyitem(int indexnum, int price) {

        list.clear();
        resultDB();

        Log.d("DB", clothchar[indexnum] + "  ////   " + indexnum);
        if (clothchar[indexnum] == '1') {
            Log.d("DB", "이미구입함");
            Toast.makeText(this, "이미 구입한 상품입니다.", Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            Log.d("DB", "이미구입한 상품이 아님");
            String input = "";
            Coinresult = Coinresult - price;
            clothchar[indexnum] = 49;
            db.updateCoin(Coinresult);
            Log.d("DB", "지금 있는돈임 DB에 들어갈 예정임 " + Coinresult);
            for (int i = 0; i < clothchar.length; i++) {
                input += Character.toString(clothchar[i]);
            }
            Log.d("DB", price+" 원");

            db.updateGarments(input);
            Log.d("DB", "DB 옷 값임" + input);
            Toast.makeText(this, "구입을 성공하였습니다!!", Toast.LENGTH_SHORT).show();
            list.clear();
            resultDB();

            Log.d("DB", "좀 길게 나와라   " + list.get(1));
            Intent i = new Intent(getApplicationContext(), ShopActivity.class);
            startActivity(i);
            finish();
        }
    }
}