package kr.hs.sdh.fitbit.fitbitandroidgame.shopitem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hs.sdh.fitbit.fitbitandroidgame.DBhelper;
import kr.hs.sdh.fitbit.fitbitandroidgame.Gumai;
import kr.hs.sdh.fitbit.fitbitandroidgame.R;

public class ShopfragmentBottom6 extends Fragment {
    ImageView imageView;
    Button btn1,setting,nowset;
    char[] clothchar;
    private TextView coin;
    private DBhelper db;
    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList(30);
    private int sex,price;

    public ShopfragmentBottom6() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_shopfragment_bottom6, container, false);

        Cursul();
        sex = Integer.parseInt(list.get(2));
        btn1 = view.findViewById(R.id.buybottom6);
        nowset = view.findViewById(R.id.setnowbottom6);
        setting = view.findViewById(R.id.settingwearbottom6);
        checkown();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkwearset();
            }
        });

        price = Integer.parseInt(getString(R.string.mid3));
        btn1.setText(price+"원");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Gumai.class);
                i.putExtra("price", "" + price);
                i.putExtra("sex", "" + sex);
                i.putExtra("location", "" + 3);
                i.putExtra("descript", "" + 6);
                startActivity(i);
                checkown();
            }
        });
        if(sex == 0) {
            imageView = view.findViewById(R.id.bottom6);
            imageView.setImageResource(R.drawable.tmath);
        }
        if(sex == 1) {
            imageView = view.findViewById(R.id.bottom6);
            imageView.setImageResource(R.drawable.tgath);
        }

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
            if(clothchar[23] == '1'||clothchar[23] == '2'){
                btn1.setVisibility(View.GONE);
                setting.setVisibility(View.VISIBLE);
                nowset.setVisibility(View.GONE);
            }
        }
        if(sex == 1){
            if(clothchar[29] == '1'||clothchar[29] == '2'){
                btn1.setVisibility(View.GONE);
                setting.setVisibility(View.VISIBLE);
                nowset.setVisibility(View.GONE);
            }
        }
        weardb();
    }
    public void checkwearset(){
        Cursul();
        if(sex == 0){
            if(clothchar[7] == '2') {
                clothchar[7]='1';
            }
            if(clothchar[8] == '2') {
                clothchar[8]='1';
            }
            if(clothchar[9] == '2') {
                clothchar[9]='1';
            }
            if(clothchar[21] == '2') {
                clothchar[21]='1';
            }
            if(clothchar[22] == '2') {
                clothchar[22]='1';
            }if(clothchar[23] == '2') {
                clothchar[23]='1';
            }

            clothchar[23]='2';


        }
        if(sex == 1){
            if(clothchar[13] == '2') {
                clothchar[13]='1';
            }
            if(clothchar[14] == '2') {
                clothchar[14]='1';
            }
            if(clothchar[15] == '2') {
                clothchar[15]='1';
            }
            if(clothchar[27] == '2') {
                clothchar[27]='1';
            }
            if(clothchar[28] == '2') {
                clothchar[28]='1';
            }if(clothchar[29] == '2') {
                clothchar[29]='1';
            }

            clothchar[29]='2';
        }
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
