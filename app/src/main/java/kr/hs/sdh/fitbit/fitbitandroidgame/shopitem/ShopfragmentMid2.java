package kr.hs.sdh.fitbit.fitbitandroidgame.shopitem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.hs.sdh.fitbit.fitbitandroidgame.R;


public class ShopfragmentMid2 extends Fragment {
    ImageView imageView;
    static int a= 1;
    public ShopfragmentMid2()
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
        View view = inflater.inflate(R.layout.fragment_shopfragment_mid2, container, false);
        imageView = view.findViewById(R.id.mid2);
        imageView.setImageResource(R.drawable.gt);
        return view;
    }



}
