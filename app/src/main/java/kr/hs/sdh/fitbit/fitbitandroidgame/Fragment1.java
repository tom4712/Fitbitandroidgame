package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {








    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment1,container,false);

        Button chang = (Button)view.findViewById(R.id.buychang);
        chang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DBhelper dbHelper = new DBhelper(getContext(), "COIN.db", null, 1);
                dbHelper.buyitem(30);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }


}