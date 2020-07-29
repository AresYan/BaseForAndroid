package com.yz.base.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.yz.base.R;
import com.yz.base.event.BaseEvent;
import com.yz.base.utils.MyEventBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseDialog extends Dialog {

	public Context mContext;

	public BaseDialog(Context context) {
		super(context, R.style.base_dialog);
		mContext=context;
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		MyEventBus.register(this);
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		MyEventBus.unregister(this);
	}

	public void setContentView(View view) {
		setContentView(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
	}

	public void setContentView(View view, int w, int h) {
		super.setContentView(view);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = w;
		params.height = h;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	}

	public boolean isLand(){
		return mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEventBus(BaseEvent event) {
	}
}
