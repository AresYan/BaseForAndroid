package com.yz.base.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.yz.base.R;
import com.yz.base.utils.MyMainHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YZ on 2016/11/25.
 */

public abstract class BaseRefreshFragment extends BaseFragment {

    protected static final int DELAYED=200;

    protected RelativeLayout mTitleLayout;
    protected ImageView mTitleLeftImv;
    protected TextView mTitleNameTv;
    protected TextView mTitleRightTv;

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected View mNetErrorView;
    protected View mDataEmptyView;

    @Override
    protected int getContentViewId() {
        return R.layout.common_refresh_activity;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTitleLayout = view.findViewById(R.id.common_title_Layout);
        mTitleLeftImv = view.findViewById(R.id.common_title_ImageView_left);
        mTitleNameTv = view.findViewById(R.id.common_title_TextView_name);
        mTitleRightTv = view.findViewById(R.id.common_title_TextView_right);
        mRecyclerView = view.findViewById(R.id.common_refresh_RecyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.common_refresh_SwipeRefreshLayout);
        mNetErrorView = view.findViewById(R.id.include_common_net_error);
        mDataEmptyView = view.findViewById(R.id.include_common_data_empty);
    }

    protected void showTitle(){
        if(mTitleLayout!=null){
            mTitleLayout.setVisibility(View.VISIBLE);
        }
    }

    protected void hideTitle(){
        if(mTitleLayout!=null){
            mTitleLayout.setVisibility(View.GONE);
        }
    }

    protected void showNetError(){
        MyMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mNetErrorView!=null){
                    mNetErrorView.setVisibility(View.VISIBLE);
                }
            }
        },DELAYED);
    }

    protected void hideNetError(){
        if(mNetErrorView!=null){
            mNetErrorView.setVisibility(View.GONE);
        }
    }

    protected void showDataEmpty(){
        MyMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mDataEmptyView!=null){
                    mDataEmptyView.setVisibility(View.VISIBLE);
                }
            }
        },DELAYED);
    }

    protected void hideDataEmpty(){
        if(mDataEmptyView!=null){
            mDataEmptyView.setVisibility(View.GONE);
        }
    }

    protected void setEnableLoadMore(boolean enable){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseAdapter){
            ((BaseAdapter)mRecyclerView.getAdapter()).setEnableLoadMore(enable);
        }
    }

    protected <T> void setNewData(List<T> data){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseAdapter){
            ((BaseAdapter)mRecyclerView.getAdapter()).setNewData(data);
        }
    }

    protected <T> void addData(List<T> data){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseAdapter){
            ((BaseAdapter)mRecyclerView.getAdapter()).addData(data);
        }
    }

    protected <T> List<T> getData(){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseAdapter){
            return ((BaseAdapter)mRecyclerView.getAdapter()).getData();
        }
        return new ArrayList<T>();
    }

    protected void loadMoreComplete(){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseAdapter){
            ((BaseAdapter)mRecyclerView.getAdapter()).loadMoreComplete();
        }
    }

    protected void loadMoreFail(){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseAdapter){
            ((BaseAdapter)mRecyclerView.getAdapter()).loadMoreFail();
        }
    }

    protected void loadMoreEnd(){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseAdapter){
            ((BaseAdapter)mRecyclerView.getAdapter()).loadMoreEnd();
        }
    }

    protected void setEnableRefresh(boolean enable){
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    protected void setRefreshing(boolean refreshing){
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setRefreshing(refreshing);
        }
    }

}
