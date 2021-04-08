package com.yz.base.http;

/**
 * @author :yanzheng
 * @describe ：返回给UI层的下载回调接口
 */
public abstract class MyResultProgressListener<T> extends MyResultListener<T>{

	public abstract void onProgress(int progress);
}
