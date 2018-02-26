package kr.hs.sdh.fitbit.fitbitandroidgame;

/**
 * Created by Resten on 2018-02-25.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static kr.hs.sdh.fitbit.fitbitandroidgame.Splash.name;


public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_a, container, false);
        TextView text = v.findViewById(R.id.char_Name);
        text.setText(Splash.name);
        return v;
    }


}


