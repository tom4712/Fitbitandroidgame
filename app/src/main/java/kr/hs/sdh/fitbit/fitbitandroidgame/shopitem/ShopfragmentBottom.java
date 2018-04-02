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

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopfragmentBottom extends Fragment {
    ImageView imageView;
    Button btn1,setting,nowset;
    char[] clothchar;
    private TextView coin;
    private DBhelper db;
    private Cursor all_cursor;
    private ArrayList<String> list = new ArrayList();
    private int sex,price;

    public ShopfragmentBottom() {
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

        View view = inflater.inflate(R.layout.fragment_shopfragment_bottom, container, false);

        Cursul();
        sex = Integer.parseInt(list.get(2));
        btn1 = view.findViewById(R.id.buybottom1);
        nowset = view.findViewById(R.id.setnowbottom1);
        setting = view.findViewById(R.id.settingwearbottom1);
        checkown();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkwearset();
            }
        });

        price = Integer.parseInt(getString(R.string.mid1));
        btn1.setText(price+"원");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Gumai.class);
                i.putExtra("price", "" + price);
                i.putExtra("sex", "" + sex);
                i.putExtra("location", "" + 3);
                i.putExtra("descript", "" + 1);
                startActivity(i);
                checkown();
            }
        });
        if(sex == 0) {
            imageView = view.findViewById(R.id.bottom1);
            imageView.setImageResource(R.drawable.mao);
        }
        if(sex == 1) {
            imageView = view.findViewById(R.id.bottom1);
            imageView.setImageResource(R.drawable.gao);
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
                        db.updateGarments("1000000000000000");
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
            if(clothchar[7] == '1'||clothchar[7] == '2'){
                btn1.setVisibility(View.GONE);
                setting.setVisibility(View.VISIBLE);
                nowset.setVisibility(View.GONE);
            }
        }
        if(sex == 1){
            if(clothchar[13] == '1'||clothchar[13] == '2'){
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
            clothchar[7]='2';


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
            clothchar[13]='2';
        }
        weardb();
        Toast.makeText(getContext(), "창착이 완료 되었습니다!", Toast.LENGTH_SHORT).show();
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
