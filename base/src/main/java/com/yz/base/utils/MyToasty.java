package com.yz.base.utils;

import android.content.Context;
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

	public static void error(String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				try {
					Toasty.error(mContext,text, Toast.LENGTH_SHORT,true).show();
				}catch (Exception e){
				    e.printStackTrace();
				}
			}
		});
	}

	public static void info(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				try {
					Toasty.info(mContext,text, Toast.LENGTH_SHORT,true).show();
				}catch (Exception e){
				    e.printStackTrace();
				}
			}
		});
	}

	public static void normal(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				try {
					Toasty.normal(mContext,text, Toast.LENGTH_SHORT).show();
				}catch (Exception e){
				    e.printStackTrace();
				}
			}
		});
	}

	public static void success(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				try {
					Toasty.success(mContext,text, Toast.LENGTH_SHORT,true).show();
				}catch (Exception e){
				    e.printStackTrace();
				}
			}
		});
	}

	public static void warning(final String text){
        if(mContext==null){
            return;
        }
		MyMainHandler.post(new Runnable() {
			@Override
			public void run() {
				try {
					Toasty.warning(mContext,text, Toast.LENGTH_SHORT,true).show();
				}catch (Exception e){
				    e.printStackTrace();
				}
			}
		});
	}
}
