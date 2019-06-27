package com.yz.base.utils;

import org.greenrobot.eventbus.EventBus;

public class MyEventBus {

	public static void register(Object object){
		if(!isRegistered(object)){
			EventBus.getDefault().register(object);
		}
	}

	public static void unregister(Object object){
		if(isRegistered(object)){
			EventBus.getDefault().unregister(object);
		}
	}

	public static boolean isRegistered(Object object){
		return EventBus.getDefault().isRegistered(object);
	}

	public static void post(Object object){
		EventBus.getDefault().post(object);
	}

	public static void postDelayed(final Object object, int delayed){
		MyMainHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				post(object);
			}
		},delayed);
	}

	public static void postSticky(Object object){
		EventBus.getDefault().postSticky(object);
	}

	public static void postStickyDelayed(final Object object, int delayed){
		MyMainHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				postSticky(object);
			}
		},delayed);
	}

}
