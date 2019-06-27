package com.yz.base.utils;

import java.text.DecimalFormat;

public class MyNumberUtils {

	public static double format6(double d){
		DecimalFormat df=new DecimalFormat("######0.000000");
		return Double.parseDouble(df.format(d));
	}

	public static double format2(double d){
		DecimalFormat df=new DecimalFormat("######0.00");
		return Double.parseDouble(df.format(d));
	}

	public static double bytes2Double(byte[] arr) {
		long value = 0;
		for (int i = 0; i < 8; i++) {
			value |= ((long) (arr[i] & 0xff)) << (8 * i);
		}
		return Double.longBitsToDouble(value);
	}

	public static float bytes2float(byte[] arr) {
		int value;
		value = arr[0];
		value &= 0xff;
		value |= ((long) arr[1] << 8);
		value &= 0xffff;
		value |= ((long) arr[2] << 16);
		value &= 0xffffff;
		value |= ((long) arr[3] << 24);
		return Float.intBitsToFloat(value);
	}

	public static int getUnsignedByte(byte data){
		return data&0x0FF;
	}

	public static int getUnsignedShort(short data){
		return data&0x0FFFF;
	}

	public static long getUnsignedInt(int data){
		return data&0x0FFFFFFFF;
	}

}
