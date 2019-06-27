package com.yz.base.ui.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

public abstract class BaseRecyclerDialog extends BaseAlertDialog {

	private RecyclerView mRecyclerView;

	public BaseRecyclerDialog(Context context) {
		super(context);
	}

	@Override
	public View getFrameLayoutView() {
		FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		mRecyclerView=new RecyclerView(getContext());
		mRecyclerView.setLayoutParams(params);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		return mRecyclerView;
	}

	public void setAdapter(BaseQuickAdapter adapter) {
		if(mRecyclerView!=null&&adapter!=null){
			mRecyclerView.setAdapter(adapter);
		}
	}

}
