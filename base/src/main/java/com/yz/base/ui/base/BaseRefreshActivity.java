package com.yz.base.ui.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yz.base.R;
import com.yz.base.utils.MyMainHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRefreshActivity extends BaseActivity{

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        mTitleLayout = findViewById(R.id.common_title_Layout);
        mTitleLeftImv = findViewById(R.id.common_title_ImageView_left);
        mTitleNameTv = findViewById(R.id.common_title_TextView_name);
        mTitleRightTv = findViewById(R.id.common_title_TextView_right);
        mRecyclerView = findViewById(R.id.common_refresh_RecyclerView);
        mSwipeRefreshLayout = findViewById(R.id.common_refresh_SwipeRefreshLayout);
        mNetErrorView = findViewById(R.id.include_common_net_error);
        mDataEmptyView = findViewById(R.id.include_common_data_empty);
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
