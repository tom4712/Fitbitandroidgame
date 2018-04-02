package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Resten on 2018-02-25.
 */

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    Fragment[] fragments = new Fragment[4];

    public MyViewPagerAdapter(FragmentManager fm) {

        super(fm);

        fragments[0] = new Fragment1();

        fragments[1] = new Fragment2();

        fragments[2] = new Fragment3();
        fragments[3] = new Fragment4();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }


    @Override
    public int getCount() {
        return fragments.length;
    }
}
