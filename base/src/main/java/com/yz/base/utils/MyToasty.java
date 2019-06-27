package com.yz.base.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

/**
 * @author :yanzheng
 * @describe ：
 * @date ：2016年6月1日 下午12:12:10
 */
public class MyToasty {

    private static Context mContext;

    public static void init(Context context){
        mContext=context;
    }

	private static Toast mToast = null;

	public static void toast(final String text) {
	    if(mContext==null){
	        return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mToast == null) {
					mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
				} else {
					mToast.setText(text);
					mToast.setDuration(Toast.LENGTH_SHORT);
				}
				mToast.show();
			}
		});
	}

	private static Toast mError = null;

	public static void error(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mError == null) {
					mError = Toasty.error(mContext,text, Toast.LENGTH_SHORT,true);
				} else {
					TextView textView = (TextView)mError.getView().findViewById(es.dmoral.toasty.R.id.toast_text);
					textView.setText(text);
					mError.setDuration(Toast.LENGTH_SHORT);
				}
				mError.show();
			}
		});
	}

	private static Toast mInfo = null;

	public static void info(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mInfo == null) {
					mInfo = Toasty.info(mContext,text, Toast.LENGTH_SHORT,true);
				} else {
					TextView textView = (TextView)mInfo.getView().findViewById(es.dmoral.toasty.R.id.toast_text);
					textView.setText(text);
					mInfo.setDuration(Toast.LENGTH_SHORT);
				}
				mInfo.show();
			}
		});
	}

	private static Toast mNormal = null;

	public static void normal(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mNormal == null) {
					mNormal = Toasty.normal(mContext,text, Toast.LENGTH_SHORT);
				} else {
					TextView textView = (TextView)mNormal.getView().findViewById(es.dmoral.toasty.R.id.toast_text);
					textView.setText(text);
					mNormal.setDuration(Toast.LENGTH_SHORT);
				}
				mNormal.show();
			}
		});
	}

	private static Toast mSuccess = null;

	public static void success(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mSuccess == null) {
					mSuccess = Toasty.success(mContext,text, Toast.LENGTH_SHORT,true);
				} else {
					TextView textView = (TextView)mSuccess.getView().findViewById(es.dmoral.toasty.R.id.toast_text);
					textView.setText(text);
					mSuccess.setDuration(Toast.LENGTH_SHORT);
				}
				mSuccess.show();
			}
		});
	}

	private static Toast mWarning = null;

	public static void warning(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mWarning == null) {
					mWarning = Toasty.warning(mContext,text, Toast.LENGTH_SHORT,true);
				} else {
					TextView textView = (TextView)mWarning.getView().findViewById(es.dmoral.toasty.R.id.toast_text);
					textView.setText(text);
					mWarning.setDuration(Toast.LENGTH_SHORT);
				}
				mWarning.show();
			}
		});
	}
}
