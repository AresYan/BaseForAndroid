package com.yz.base.ui.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yz.base.R;
import com.yz.base.R2;
import com.yz.base.utils.MyMainHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public abstract class BaseRefreshActivity extends BaseActivity{

    protected static final int DELAYED=200;

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

    protected void setTitle(String title){
        if(mTitleNameTv!=null){
            mTitleNameTv.setText(title);
        }
    }

    protected void setTitle(String title, int color){
        if(mTitleNameTv!=null){
            mTitleNameTv.setText(title);
            mTitleNameTv.setTextColor(color);
        }
    }

    protected void setLeft(int rid){
        if(mTitleLeftImv!=null){
            mTitleLeftImv.setImageResource(rid);
        }
    }

    protected void setRight(String right){
        if(mTitleRightTv!=null){
            mTitleRightTv.setText(right);
        }
    }

    protected void setRight(String title, int color){
        if(mTitleRightTv!=null){
            mTitleRightTv.setText(title);
            mTitleRightTv.setTextColor(color);
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

    protected void setTitleBackground(int rid){
        if(mTitleLayout!=null){
            mTitleLayout.setBackgroundResource(rid);
        }
    }

    protected void setContentBackground(int rid){
        if(mRecyclerView!=null){
            mRecyclerView.setBackgroundResource(rid);
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
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseQuickAdapter){
            ((BaseQuickAdapter)mRecyclerView.getAdapter()).setEnableLoadMore(enable);
        }
    }

    protected <T> void setNewData(List<T> data){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseQuickAdapter){
            ((BaseQuickAdapter)mRecyclerView.getAdapter()).setNewData(data);
        }
    }

    protected <T> void addData(List<T> data){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseQuickAdapter){
            ((BaseQuickAdapter)mRecyclerView.getAdapter()).addData(data);
        }
    }

    protected <T> List<T> getData(){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseQuickAdapter){
            return ((BaseQuickAdapter)mRecyclerView.getAdapter()).getData();
        }
        return new ArrayList<T>();
    }

    protected void loadMoreComplete(){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseQuickAdapter){
            ((BaseQuickAdapter)mRecyclerView.getAdapter()).loadMoreComplete();
        }
    }

    protected void loadMoreFail(){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseQuickAdapter){
            ((BaseQuickAdapter)mRecyclerView.getAdapter()).loadMoreFail();
        }
    }

    protected void loadMoreEnd(){
        if(mRecyclerView!=null&&mRecyclerView.getAdapter()!=null&&mRecyclerView.getAdapter() instanceof BaseQuickAdapter){
            ((BaseQuickAdapter)mRecyclerView.getAdapter()).loadMoreEnd();
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

    protected void setAdapter(RecyclerView.Adapter adapter){
        if(mRecyclerView!=null){
            mRecyclerView.setAdapter(adapter);
        }
    }

    protected void setLayoutManager(RecyclerView.LayoutManager manager){
        if(mRecyclerView!=null){
            mRecyclerView.setLayoutManager(manager);
        }
    }

    protected void addItemDecoration(RecyclerView.ItemDecoration decoration){
        if(mSwipeRefreshLayout!=null){
            mRecyclerView.addItemDecoration(decoration);
        }
    }

    protected void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setOnRefreshListener(listener);
        }
    }

}
