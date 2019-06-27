package com.yz.base.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author :yanzheng
 * @describe ：
 * @date ：2016年6月1日 下午12:12:10
 */
public class MyLogger {

	public static void init(String tag, final boolean isLoggable){
		FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
				.tag(tag)
				.build();
		Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
			@Override
			public boolean isLoggable(int priority, String tag) {
				return isLoggable;
			}
		});
	}

	public static void d(String message) {
        Logger.d(message);
	}

	public static void v(String message) {
        Logger.v(message);
	}

	public static void i(String message) {
        Logger.i(message);
	}

	public static void w(String message) {
        Logger.w(message);
	}

	public static void e(String message) {
        Logger.e(message);
	}

	public static void e(String message, Object obiect) {
        Logger.e(message,obiect);
	}
}
