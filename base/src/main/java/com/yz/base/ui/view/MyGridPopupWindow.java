package com.yz.base.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class MyGridPopupWindow extends BasePopupWindow {

	private int spanCount=1;
	private List<PopSelected> selecteds;

	public void setSpanCount(int spanCount) {
		this.spanCount = spanCount;
	}

	public void setPopSelecteds(List<PopSelected> selecteds) {
		this.selecteds=selecteds;
	}

	@BindView(R2.id.common_popup_grid_RecyclerView)
	RecyclerView mRecyclerView;

	@Override
	protected int getConentView() {
		return R.layout.common_popup_grid;
	}

	@Override
	public void initView() {
		super.initView();
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		mRecyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
		final MyGridPopupWindowAdapter adapter=new MyGridPopupWindowAdapter(selecteds);
		mRecyclerView.setAdapter(adapter);
		mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
				dismiss();
				int size=selecteds.size();
				for (int j=0; j<size; j++){
					selecteds.get(j).setSelected(j==i?true:false);
				}
				if(basePopupWindowListener!=null){
					basePopupWindowListener.onClick(selecteds.get(i));
				}
			}
		});
	}

	public class MyGridPopupWindowAdapter extends BaseAdapter<PopSelected> {

		public MyGridPopupWindowAdapter(List<PopSelected> list) {
			super(R.layout.common_popup_grid_item, list);
		}

		@Override
		protected void convert(BaseViewHolder helper, PopSelected item) {
			helper.setBackgroundRes(R.id.common_popup_grid_item_LinearLayout,item.isSelected()?R.drawable.common_popup_grid_item_red_stroke_bg :R.drawable.common_popup_grid_item_gray_stroke_bg);
			helper.setImageResource(R.id.common_popup_grid_item_ImageView_pic,item.getRes());
			helper.setText(R.id.common_popup_grid_item_TextView_name, item.getName());
		}
	}

	public static class Builder extends BasePopupWindow.Builder{

		public int spanCount=1;
		public List<PopSelected> selecteds;

		public Builder(Context context) {
			super(context);
		}

		public Builder setSpanCount(int spanCount) {
			this.spanCount=spanCount;
			return this;
		}

		public Builder setPopSelecteds(List<PopSelected> selecteds) {
			this.selecteds=selecteds;
			return this;
		}

		private void construct(MyGridPopupWindow dialog) {
			dialog.setSpanCount(spanCount);
			dialog.setPopSelecteds(selecteds);
			dialog.setBasePopupWindowListener(listener);
			super.construct(dialog);
		}

		@Override
		public MyGridPopupWindow build() {
			MyGridPopupWindow dialog = new MyGridPopupWindow();
			construct(dialog);
			return dialog;
		}
	}

}
