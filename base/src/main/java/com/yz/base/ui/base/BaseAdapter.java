package com.yz.base.ui.base;

import android.content.Intent;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yz.base.utils.MyMainHandler;

import java.util.List;

/**
 * Created by YZ on 2016/12/9.
 */

public abstract class BaseAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {

    public BaseAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    public void removeItem(int position){
        getData().remove(position);
        notifyItemRemoved(position);
        if(position != getData().size()){
            notifyItemRangeChanged(position, getData().size() - position);
        }
    }

    public void startActivity(Intent intent){
        if(mContext!=null){
            if(mContext instanceof BaseActivity){
                ((BaseActivity)mContext).startActivity(intent);
            }else{
                mContext.startActivity(intent);
            }
        }
    }

    public void showLoading() {
        if(mContext!=null&&mContext instanceof BaseActivity){
            ((BaseActivity)mContext).showLoading();
        }
    }

    public void dismissLoading() {
        if(mContext!=null&&mContext instanceof BaseActivity){
            ((BaseActivity)mContext).dismissLoading();
        }
    }

}
