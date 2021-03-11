package com.yz.base.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yz.base.R;
import com.yz.base.R2;
import com.yz.base.entity.PopSelected;
import com.yz.base.ui.base.BaseAdapter;
import com.yz.base.ui.base.BasePopupWindow;

import java.util.List;

import butterknife.BindView;

public class MyListPopupWindow extends BasePopupWindow {

	@BindView(R2.id.common_popup_list_RecyclerView)
	RecyclerView mRecyclerView;
	@BindView(R2.id.common_popup_list_TextView_cannel)
	TextView mCannelTv;

	private MyListPopupWindow(Context context){
		super(context);
	}

	@Override
	protected int getConentView() {
		return R.layout.common_popup_list;
	}

	private List<PopSelected> selecteds;

	public void setPopSelecteds(List<PopSelected> selecteds) {
		this.selecteds=selecteds;
	}

	@Override
	public void show(View parent) {
		if(selecteds!=null){
			int size=selecteds.size();
			int w1 = (int) mContext.getResources().getDimension(R.dimen.d160);
			int w2 = (int) mContext.getResources().getDimension(R.dimen.d152);
			setHeight(w1+w2*(size>5?5:size));
			mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
			mRecyclerView.setAdapter(new MyListPopupWindowAdapter(selecteds));
			mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
				@Override
				public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
					dismiss();
					if(listener!=null){
						listener.onClick(selecteds.get(i));
					}
				}
			});
			mCannelTv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dismiss();
				}
			});
		}
		super.show(parent);
	}

	public class MyListPopupWindowAdapter extends BaseAdapter<PopSelected> {

		public MyListPopupWindowAdapter(List<PopSelected> list) {
			super(R.layout.common_popup_list_item, list);
		}

		@Override
		protected void convert(BaseViewHolder helper, PopSelected item) {
			helper.setText(R.id.common_popup_list_item_TextView, item.getName());
		}
	}

	public static class Builder extends BasePopupWindow.Builder{

		public List<PopSelected> selecteds;

		public Builder(Context context) {
			super(context);
		}

		public Builder setPopSelecteds(List<PopSelected> selecteds) {
			this.selecteds=selecteds;
			return this;
		}

		private void construct(MyListPopupWindow dialog) {
			dialog.setPopSelecteds(selecteds);
			dialog.setBasePopupWindowListener(listener);
		}

		@Override
		public MyListPopupWindow build() {
			MyListPopupWindow dialog = new MyListPopupWindow(context);
			construct(dialog);
			return dialog;
		}
	}

}
