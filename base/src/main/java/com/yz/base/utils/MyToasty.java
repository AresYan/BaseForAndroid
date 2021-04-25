package com.yz.base.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.yz.base.toast.SafeToastContext;

import java.lang.reflect.Field;

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
		Toast toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
		switch(tpye){
			case TYPE_ERROR:
				toast=Toasty.error(activity, text, Toast.LENGTH_SHORT,true);
				break;
			case TYPE_INFO:
				toast=Toasty.info(activity, text, Toast.LENGTH_SHORT,true);
				break;
			case TYPE_NORMAL:
				toast=Toasty.normal(activity, text, Toast.LENGTH_SHORT);
				break;
			case TYPE_SUCCESS:
				toast=Toasty.success(activity, text, Toast.LENGTH_SHORT,true);
				break;
			case TYPE_WARNING:
				toast=Toasty.warning(activity, text, Toast.LENGTH_SHORT,true);
				break;
		}
		setContextCompat(toast.getView(), new SafeToastContext(activity, toast));
		toast.show();
	}

	private static void setContextCompat(@NonNull View view, @NonNull Context context) {
		if (Build.VERSION.SDK_INT == 25) {
			try {
				Field field = View.class.getDeclaredField("mContext");
				field.setAccessible(true);
				field.set(view, context);
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
	}
}
