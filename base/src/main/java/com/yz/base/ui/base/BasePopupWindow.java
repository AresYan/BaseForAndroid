package com.yz.base.ui.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.blankj.utilcode.util.ScreenUtils;

public abstract class BasePopupWindow extends PopupWindow {
	
	public View mConentView;
	public Context mContext;
	public LayoutInflater mInflater;

	public BasePopupWindowListener listener;

	public void setBasePopupWindowListener(BasePopupWindowListener listener) {
		this.listener = listener;
	}

	public BasePopupWindow(Context context) {
		mContext=context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mConentView = mInflater.inflate(getConentView(), null);
		int h = ScreenUtils.getScreenHeight()/2;
		int w = ScreenUtils.getScreenWidth();
		// 设置SelectPicPopupWindow的View
		this.setContentView(mConentView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(h);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		this.setBackgroundDrawable(new ColorDrawable(0x64000000));
		this.setAnimationStyle(android.R.style.Animation_InputMethod);
		this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	protected abstract int getConentView();

	public void show(View parent) {
		if (!this.isShowing()) {
			this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		} else {
			this.dismiss();
		}
	}

	public void show(View parent, int gravity, int x, int y) {
		if (!this.isShowing()) {
			this.showAtLocation(parent, gravity, x, y);
		} else {
			this.dismiss();
		}
	}

	public interface BasePopupWindowListener<T> {
		void onClick(T item);
	}

	public static abstract class Builder {
		public Context context;
		public BasePopupWindowListener listener;

		public Builder(Context context){
			this.context=context;
		}

		public BasePopupWindow.Builder setBasePopupWindowListener(BasePopupWindowListener listener) {
			this.listener = listener;
			return this;
		}

		public abstract BasePopupWindow build();
	}
}
