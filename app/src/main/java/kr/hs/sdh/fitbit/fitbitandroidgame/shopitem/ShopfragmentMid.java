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


public class ShopfragmentMid extends Fragment {
    ImageView imageView;
    static int a= 1;
    public ShopfragmentMid()
    {
    }

    public void Shoppagefragment(int position){
        position = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_shopfragment_mid, container, false);
        imageView = view.findViewById(R.id.mid1);
        imageView.setImageResource(R.drawable.go);
        return view;
    }



}
