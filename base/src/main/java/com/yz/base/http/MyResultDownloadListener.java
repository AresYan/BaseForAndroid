package com.yz.base.http;

/**
 * @author :yanzheng
 * @describe ：返回给UI层的下载回调接口
 */
public abstract class MyResultDownloadListener<T> extends MyResultListener<T>{

	public abstract void onDownloading(int progress, boolean done);
}
