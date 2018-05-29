package kr.hs.sdh.fitbit.fitbitandroidgame;

/**
 * Created by Resten on 2018-02-25.
 */

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static kr.hs.sdh.fitbit.fitbitandroidgame.Splash.name;


public class Fragment1 extends Fragment {
    private int sex = 0;

    private DBhelper db;
    private ArrayList<String> list = new ArrayList();

    private Cursor all_cursor;

    private LinearLayout Mainchar;

    char[] clothchar;
    private ImageView[] image = new ImageView[15];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_a, container, false);
        TextView text = v.findViewById(R.id.char_Name);
        text.setText(Splash.name);


        Mainchar = v.findViewById(R.id.mainchar);

        Cursul();

        try {
            if (Integer.parseInt(list.get(2)) == 0) {
                db.updateGarments("1000000000000000");
                Cursul();
                Log.d("db","아아ㅏㅇ"+list.get(2));
            }
        } catch (Exception e) {

        }

        sex = Integer.parseInt(list.get(1));

        setcharback();
        String cloth = list.get(2);
        clothchar = cloth.toCharArray();
        Wearitem();





        return v;
    }
    @Override
public void onPause(){
       super.onPause();
        Log.d("pp",  "퍼지걸림");
}
public void onResume(){
        super.onResume();
    Cursul();
    setcharback();
    String cloth = list.get(2);
    clothchar = cloth.toCharArray();
    Wearitem();
    Log.d("pp",  "다시");
}
    public void Cursul() {
        list.clear();
        db = new DBhelper(getActivity());
        db.open();
        all_cursor = db.AllRows();
        all_cursor.moveToFirst();
        while (true) {
            try {
                list.add(all_cursor.getString(all_cursor.getColumnIndex("COIN")));
                Log.d("DB", "코인값받아옴");
                list.add(all_cursor.getString(all_cursor.getColumnIndex("SEX")));
                list.add(all_cursor.getString(all_cursor.getColumnIndex("GARMENTS")));

                if (!all_cursor.moveToNext())
                    break;
            } catch (Exception e) {

            }

            Log.d("db","123123   "+list.get(2));



        }
    }

    public void setcharback(){

        if(Integer.parseInt(list.get(1)) == 0){

            Mainchar.setBackgroundResource(R.drawable.maincharm);
        }
        if (Integer.parseInt(list.get(1)) == 1)
        {
            Mainchar.setBackgroundResource(R.drawable.maincharg);
        }

    }

    public void Wearitem(){

        image[0] = Mainchar.findViewById(R.id.head1);
        image[1] = Mainchar.findViewById(R.id.head2);
        image[2] = Mainchar.findViewById(R.id.head2);
        image[3] = Mainchar.findViewById(R.id.topm1);
        image[4] = Mainchar.findViewById(R.id.topman2);
        image[5] = Mainchar.findViewById(R.id.topman3);
        image[6] = Mainchar.findViewById(R.id.bottomm1);
        image[7] = Mainchar.findViewById(R.id.bottomm2);
        image[8] = Mainchar.findViewById(R.id.bottomm3);
        image[9] = Mainchar.findViewById(R.id.topg1);
        image[10] = Mainchar.findViewById(R.id.topg2);
        image[11] = Mainchar.findViewById(R.id.topg3);
        image[12] = Mainchar.findViewById(R.id.bottomg1);
        image[13] = Mainchar.findViewById(R.id.bottomg2);
        image[14] = Mainchar.findViewById(R.id.bottomg3);

        for(int g = 0; g < 15;g++ ){
            Log.d("db","asdasd"+g);
            image[g].setVisibility(View.INVISIBLE);
        }

        Log.d("db","안녕 ");

        if(sex == 0){

            Log.d("db","안녕 남자네");
            for(int i = 0;i < 9 ; i++){
                if (clothchar[i+1] == '2' ){

                    image[i].setVisibility(View.VISIBLE);
                }
            }
        }

        if (sex == 1){
            Log.d("db","안녕 여자네");
            for(int i = 0;i < 3 ; i++) {
                if (clothchar[i + 1] == '2') {
                    Log.d("db",i+"번을 보여준다!");
                    image[i].setVisibility(View.VISIBLE);
                }
            }

            for (int i = 9;i < 15 ; i++){
                if (clothchar[i + 1] == '2') {
                    image[i].setVisibility(View.VISIBLE);
                }
            }
        }

    }
}


