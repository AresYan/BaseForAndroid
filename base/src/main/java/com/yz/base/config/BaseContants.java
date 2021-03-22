package com.yz.base.config;

/**
 * @author :yanzheng
 * @describe ：常量
 * @date ：2016年10月25日 上午10:29:21
 */
public class BaseContants {

    public static class IntentKey{
        public final static String ORIENTATION="orientation";
        public final static String NOTITF_MSG="NotifyMsg";
        public final static String STATUSBAR_MODE="StatusBarMode";
    }

	public static class Orientation{
		public final static int PORTRAIT=0;
		public final static int SENSOR_LANDSCAPE=1;
	}

	public static class MapType{
		public final static int TYPE_SATELLITE=1;
		public final static int TYPE_ROAD=2;
		public final static int TYPE_TERRAIN=3;
	}

	public static class StatusBarMode{
		public final static int LIGHT=0;
		public final static int DARK=1;
	}

}
