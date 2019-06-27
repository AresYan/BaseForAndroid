package com.yz.base.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author :yanzheng
 * @describe ：实体类基类
 * @date ：2016年10月25日 上午10:29:21
 */
public class BaseEntity implements Serializable, Cloneable {

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	@Override
	public BaseEntity clone(){
		try {
			return (BaseEntity)super.clone();
		} catch (CloneNotSupportedException e) {
		    e.printStackTrace();
		}
		return null;
	}
}
