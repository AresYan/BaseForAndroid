package com.yz.base.http;

/**
 * @author :yanzheng
 * @describe ：返回给UI层的分页回调接口
 */
public abstract class MyResultHasMoreListener<T> extends MyResultListener<T>{

	public abstract void onHasMore(boolean hasMore);

}
