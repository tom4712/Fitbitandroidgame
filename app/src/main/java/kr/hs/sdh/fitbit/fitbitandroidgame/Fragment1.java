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
    int num = 40;
    private ArrayList<String> list = new ArrayList(num);
    private Cursor all_cursor;

    private LinearLayout Mainchar;

    char[] clothchar = new char[30];
    private ImageView[] image = new ImageView[30];

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
                db.updateGarments("100000000000000000000000000000");
                Cursul();
                Log.d("db","아아ㅏㅇ"+list.get(2));
            }
        } catch (Exception e) {

        }


        sex = Integer.parseInt(list.get(1));


        setcharback();
        String cloth = list.get(2);

        Log.d("씨발12341251235235345",list.get(2));

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
        image[2] = Mainchar.findViewById(R.id.head3);

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

        image[15] = Mainchar.findViewById(R.id.head4);
        image[16] = Mainchar.findViewById(R.id.head5);
        //남자티2
        image[17] = Mainchar.findViewById(R.id.topman4);
        image[18] = Mainchar.findViewById(R.id.topman5);
        image[19] = Mainchar.findViewById(R.id.topman6);
        //남자바지2
        image[20] = Mainchar.findViewById(R.id.bottomm4);
        image[21] = Mainchar.findViewById(R.id.bottomm5);
        image[22] = Mainchar.findViewById(R.id.bottomm6);

        //여자티2
        image[23] = Mainchar.findViewById(R.id.topg4);
        image[24] = Mainchar.findViewById(R.id.topg5);
        image[25] = Mainchar.findViewById(R.id.topg6);
        //여자바지2
        image[26] = Mainchar.findViewById(R.id.bottomg4);
        image[27] = Mainchar.findViewById(R.id.bottomg5);
        image[28] = Mainchar.findViewById(R.id.bottomg6);


        for(int g = 0; g < 29;g++ ){
            Log.d("db","asdasd"+g);
            image[g].setVisibility(View.INVISIBLE);
        }

        Log.d("db","안녕 ");

        for(int i =0;i<clothchar.length;i++){
            Log.d("dddd","씨발"+i);
        }

        if(sex == 0){ //남자

            Log.d("db","안녕 남자네");
            for(int i = 0;i < 9 ; i++){
                if (clothchar[i+1] == '2' ){

                    image[i].setVisibility(View.VISIBLE);
                }
            }

            for(int i = 15;i < 23; i++){
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

            for (int i = 9;i < 15; i++){
                if (clothchar[i + 1] == '2') {
                    image[i].setVisibility(View.VISIBLE);
                }
            }

            if(clothchar[16]=='2'){
                image[15].setVisibility(View.VISIBLE);
            }
            if(clothchar[17]=='2'){
                image[16].setVisibility(View.VISIBLE);
            }
            for (int i = 23;i < 29 ; i++){
                if (clothchar[i+1] == '2') {
                    image[i].setVisibility(View.VISIBLE);
                }
            }
        }

    }
}
