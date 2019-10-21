package com.yz.base.ui.base;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by YZ on 2016/12/9.
 */

public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mList;

    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.mList=list;
    }

    @Override
    public BaseFragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
