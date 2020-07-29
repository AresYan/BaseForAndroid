package com.yz.base.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

/**
 * @author :yanzheng
 * @describe ：
 * @date ：2016年6月1日 下午12:12:10
 */
public class MyToasty {

	public static final int TYPE_ERROR=1;
	public static final int TYPE_INFO=2;
	public static final int TYPE_NORMAL=3;
	public static final int TYPE_SUCCESS=4;
	public static final int TYPE_WARNING=5;

	public static void show(Activity activity, String text, int tpye){
		if (activity == null||activity.isFinishing()) {
			return;
		}
		switch(tpye){
			case TYPE_ERROR:
				Toasty.error(activity, text, Toast.LENGTH_SHORT,true).show();
				break;
			case TYPE_INFO:
				Toasty.info(activity, text, Toast.LENGTH_SHORT,true).show();
				break;
			case TYPE_NORMAL:
				Toasty.normal(activity, text, Toast.LENGTH_SHORT).show();
				break;
			case TYPE_SUCCESS:
				Toasty.success(activity, text, Toast.LENGTH_SHORT,true).show();
				break;
			case TYPE_WARNING:
				Toasty.warning(activity, text, Toast.LENGTH_SHORT,true).show();
				break;
		}
	}
}
