package kr.hs.sdh.fitbit.fitbitandroidgame.shopitem;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hs.sdh.fitbit.fitbitandroidgame.DBhelper;
import kr.hs.sdh.fitbit.fitbitandroidgame.Gumai;
import kr.hs.sdh.fitbit.fitbitandroidgame.R;


public class ShopfragmentTop3 extends Fragment {
    ImageView imageView;
    Button btn1,setting,nowset;
    char[] clothchar = new char[30];
    private TextView coin;
    private DBhelper db;
    private Cursor all_cursor;
    int num = 40;
    private ArrayList<String> list = new ArrayList(num);
    private int sex,price;
    public ShopfragmentTop3()
    {
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_shopfragment_top3, container, false);
        Cursul();
        sex = Integer.parseInt(list.get(2));
        btn1 = view.findViewById(R.id.buy3);
        nowset = view.findViewById(R.id.setnow3);
        setting = view.findViewById(R.id.settingwear3);
        checkown();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkwearset();
            }
        });
        price = Integer.parseInt(getString(R.string.top3));
        btn1.setText(price+"원");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Gumai.class);
                i.putExtra("price", "" + price);
                i.putExtra("sex", "" + sex);
                i.putExtra("location", "" + 1);
                i.putExtra("descript", "" + 3);
                startActivity(i);
                checkown();
            }
        });
        imageView = view.findViewById(R.id.top3);
        imageView.setImageResource(R.drawable.gongf);
        return view;
    }

    public void Cursul() {
        list.clear();
        db = new DBhelper(getContext());
        db.open();
        all_cursor = db.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                list.add(all_cursor.getString(all_cursor.getColumnIndex("COIN")));
                Log.d("DB", "코인값받아옴");
                list.add(all_cursor.getString(all_cursor.getColumnIndex("GARMENTS")));
                Log.d("DB", "옷값받아옴");
                list.add(all_cursor.getString(all_cursor.getColumnIndex("SEX")));
                try {
                    if (Integer.parseInt(list.get(1)) == 0) {
                        db.updateGarments("100000000000000000000000000000");
                    }
                } catch (Exception e) {

                }

                String cloth = list.get(1);
                clothchar = cloth.toCharArray();
                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

        }
    }

    public void checkown(){
        Cursul();
        if(sex == 0){
            if(clothchar[3] == '1'||clothchar[3] == '2'){
                btn1.setVisibility(View.GONE);
                setting.setVisibility(View.VISIBLE);
                nowset.setVisibility(View.GONE);
            }
        }
        if(sex == 1){
            if(clothchar[3] == '1'||clothchar[3] == '2'){
                btn1.setVisibility(View.GONE);
                setting.setVisibility(View.VISIBLE);
                nowset.setVisibility(View.GONE);
            }
        }
        weardb();
    }
    public void checkwearset(){
        Cursul();


        if(clothchar[1] == '2') {
            clothchar[1]='1';
        }
        if(clothchar[2] == '2') {
            clothchar[2]='1';
        }if(clothchar[3] == '2') {
            clothchar[3]='1';
        }if(clothchar[16] == '2') {
            clothchar[16]='1';
        }if(clothchar[17] == '2') {
            clothchar[17]='1';
        }

        clothchar[3]='2';
        weardb();
        Toast.makeText(getContext(), "장착이 완료 되었습니다!", Toast.LENGTH_SHORT).show();
    }

    public void weardb(){
        String input = "";
        for (int i = 0; i < clothchar.length; i++) {
            input += Character.toString(clothchar[i]);
        }
        db.updateGarments(input);

        Log.d("DB",""+input);
    }


}