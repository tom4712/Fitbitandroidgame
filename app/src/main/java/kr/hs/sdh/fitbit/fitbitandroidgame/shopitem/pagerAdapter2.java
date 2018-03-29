package kr.hs.sdh.fitbit.fitbitandroidgame.shopitem;

import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by tom47 on 2018-03-26.
 */

public class pagerAdapter2 extends FragmentStatePagerAdapter {

    public pagerAdapter2(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        


        switch (position) {
            case 0:
                return new ShopfragmentMid();
            case 1:
                return new ShopfragmentMid2();
            case 2:
                return new ShopfragmentMid3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
