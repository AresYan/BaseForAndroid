package com.yz.base.utils;

import java.text.DecimalFormat;

public class MyFormatUtils {

	public static final String AFTER_6="######0.000000";
	public static final String AFTER_3="######0.000";
	public static final String AFTER_2="######0.00";

	public static double doubleAfter(double d, String pattern){
		DecimalFormat df=new DecimalFormat(pattern);
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

	public static String bytes2HexString(byte[] bytes){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i){
			buffer.append(byte2HexString(bytes[i]));
		}
		return buffer.toString();
	}

	public static String byte2HexString(byte b){
		String s = Integer.toHexString(b & 0xFF);
		if (s.length() == 1){
			return "0" + s;
		}else{
			return s;
		}
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

	public static String second2Time(long time) {
		String timeStr = null;
		long hour = 0;
		long minute = 0;
		long second = 0;
		if (time <= 0)
			return "00:00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
			}
			timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
		}
		return timeStr;
	}

	private static String unitFormat(long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + i;
		else
			retStr = "" + i;
		return retStr;
	}
}
