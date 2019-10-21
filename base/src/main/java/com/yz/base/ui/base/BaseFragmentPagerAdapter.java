package com.yz.base.ui.base;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by YZ on 2016/12/9.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList=list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
