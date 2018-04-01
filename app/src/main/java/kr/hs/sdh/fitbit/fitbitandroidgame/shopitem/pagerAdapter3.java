package kr.hs.sdh.fitbit.fitbitandroidgame.shopitem;

import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by tom47 on 2018-03-30.
 */

public class pagerAdapter3 extends FragmentStatePagerAdapter {

    public pagerAdapter3(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {



        switch (position) {
            case 0:
                return new ShopfragmentBottom();
            case 1:
                return new ShopfragmentBottom2();
            case 2:
                return new ShopfragmentBottom3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
