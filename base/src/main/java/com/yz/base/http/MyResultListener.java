package com.yz.base.http;

/**
 * @author :yanzheng
 * @describe ：返回给UI层的数据接口
 */
public abstract class MyResultListener<T> {
	
	public abstract void onStart();

	public abstract void onFinished();
	
	public abstract void onSuccess(T result);

	public abstract void onFailure(String msg);

}
