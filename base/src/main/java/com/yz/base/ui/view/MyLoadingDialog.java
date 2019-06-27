package com.yz.base.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.yz.base.R;
import com.yz.base.ui.base.BaseDialog;

/**
 * @author :yanzheng
 * @describe ：自定义loading提示框
 */
public class MyLoadingDialog extends BaseDialog {

	public MyLoadingDialog(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.common_dialog_loading, null);
		setContentView(view);
		setCancelable(false);
	}

}
