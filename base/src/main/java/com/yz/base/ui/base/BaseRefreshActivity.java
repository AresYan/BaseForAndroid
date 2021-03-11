package com.yz.base.ui.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.yz.base.R;
import com.yz.base.R2;
import com.yz.base.utils.MyMainHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public abstract class BaseRefreshActivity extends BaseActivity{

    protected static final int DELAYED=200;

    @BindView(R2.id.common_refresh_activity_LinearLayout)
    LinearLayout mLinearLayout;
    @BindView(R2.id.common_title_Layout)
    RelativeLayout mTitleLayout;
    @BindView(R2.id.common_title_ImageView_left)
    ImageView mTitleLeftImv;
    @BindView(R2.id.common_title_TextView_name)
    TextView mTitleNameTv;
    @BindView(R2.id.common_title_TextView_right)
    TextView mTitleRightTv;
    @BindView(R2.id.common_refresh_RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.common_refresh_SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R2.id.include_common_net_error)
    View mNetErrorView;
    @BindView(R2.id.include_common_data_empty)
    View mDataEmptyView;

    @Override
    protected int getContentViewId() {
        return R.layout.common_refresh_activity;
    }

    protected void setBackground(int rid) {
        if(mLinearLayout!=null){
            mLinearLayout.setBackgroundResource(rid);
        }
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
