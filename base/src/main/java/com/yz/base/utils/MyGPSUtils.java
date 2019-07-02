package com.yz.base.utils;

import com.yz.base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YZ on 2017/4/24.
 */

public class MyGPSUtils {

    private static double a = 6378245.0;
    private static double ee = 0.00669342162296594323;
    private static double PI = 3.14159265358979324;

    /**
     * 转火星坐标
     * point：坐标点{lat,lng}
     */
    public static double[] change2Gaode(double[] point){
        return WGS2GCJ(point[0],point[1]);
    }

    /**
     * 转火星坐标
     * points：坐标点集合{lat,lng}
     */
    public static List<double[]> change2Gaode(List<double[]> points){
        List<double[]> list=new ArrayList<>();
        for (double[] point:points){
            list.add(WGS2GCJ(point[0],point[1]));
        }
        return list;
    }

    /**
     * 转GPS坐标
     * point：坐标点{lat,lng}
     */
    public static double[] change2Goodle(double[] point){
        return GCJ2WGS(point[0],point[1]);
    }

    /**
     * 转GPS坐标
     * points：坐标点集合{lat,lng}
     */
    public static List<double[]> change2Goodle(List<double[]> points){
        List<double[]> list=new ArrayList<>();
        for (double[] point:points){
            list.add(GCJ2WGS(point[0],point[1]));
        }
        return list;
    }

    /**
     * 手机GPS坐标转火星坐标
     */
    public static double[] WGS2GCJ(double lat, double lng) {
//        if (outOfChina(lat, lng)) {//如果在国外，则默认不进行转换
//            return new GCJ(lat, lng);
//        }
        double dLat = transformWGSLat(lng - 105.0,lat - 35.0);
        double dLon = transformWGSLon(lng - 105.0,lat - 35.0);
        double radLat = lat / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
        lat += dLat;
        lng += dLon;
        double[] p=new double[2];
        p[0]=MyNumberUtils.format6(lat);
        p[1]=MyNumberUtils.format6(lng);
        return p;
    }

    private static double transformWGSLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(x > 0 ? x : -x);
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x
                * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0
                * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y
                * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformWGSLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(x > 0 ? x : -x);
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x
                * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0
                * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x
                / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 火星坐标转手机GPS坐标
     */
    public static double[] GCJ2WGS(double lat, double lng) {
        double a = 6378245.0;
        double ee = 0.00669342162296594323;
        double dLat = transformGCJLat(lng - 105.0, lat - 35.0);
        double dLng = transformGCJLon(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLng = (dLng * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        lat -= dLat;
        lng -= dLng;
        double[] p=new double[2];
        p[0]=MyNumberUtils.format6(lat);
        p[1]=MyNumberUtils.format6(lng);
        return p;
    }

    private static double transformGCJLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformGCJLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 是否国外
     */
    public static boolean outOfChina(double lat, double lng) {
        if (lng < 72.004 || lng > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    /**
     * 经纬度距离
     */
    public static double distance(double lng1, double lat1, double lng2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (lng1 - lng2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2* R* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)* Math.cos(lat2) * sb2 * sb2));
        return MyNumberUtils.format2(d);
    }
    public static double M_PI = Math.PI;

    /**
     * 经纬度转墨卡托
     */
    public static double[] lonLat2Mercator(double lng, double lat) {
        double[] xy = new double[2];
        double x = lng * 20037508.34 / 180;
        double y = Math.log(Math.tan((90 + lat) * M_PI / 360)) / (M_PI / 180);
        y = y * 20037508.34 / 180;
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

    /**
     * 墨卡托转经纬度
     */
    public static double[] mercator2LonLat(double mercatorY, double mercatorX) {
        double[] xy = new double[2];
        double x = mercatorX / 20037508.34  * 180;
        double y = mercatorY / 20037508.34  * 180;
        y = 180 / M_PI * (2 * Math.atan(Math.exp(y * M_PI / 180)) - M_PI / 2);
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

    /**
     * 获取扇形坐标集合
     * xy：基站坐标{lng,lat}
     * r：半径
     * angle：方位角
     * @return
     */
    public static List<double[]> getFanshaped(double[] xy, double r, double angle) {
        if(angle<0){
            return new ArrayList<double[]>();
        }
        double[] o = lonLat2Mercator(xy[0],xy[1]);
        List<double[]> list = new ArrayList<>();
        double beginAngle=angle-13.5<0?angle-13.5+360:angle-13.5;
        double endAngle=angle+13.5>360?angle+13.5-360:angle+13.5;
        int n1 = new Double(beginAngle).intValue();
        int n2 = new Double(endAngle).intValue();
        list.add(xy);
        if (n1 < n2) {
            for (int i = 360 - (270 + n2); i < 360 - (270 + n1); i++) {//120---300
                double[] p = aoPoint(o, r, i);
                p = mercator2LonLat(p[1],p[0]);
                if (i % 4 == 0 || i == 0 || i == n2 - 1) {
                    list.add(p);
                }
            }
        } else {
            for (int i = 360 - (270 + n2); i < 450 - n1; i++) {//300---60
                double[] p = aoPoint(o, r, i);
                p = mercator2LonLat(p[1],p[0]);
                if (i % 4 == 0 || i == 0 || i == n2 - 1) {
                    list.add(p);
                }
            }
        }
        double[] p=new double[2];
        p[0]=xy[0];
        p[1]=xy[1];
        list.add(p);
        return list;
    }

    private static double[] aoPoint(double[] xy, double r, int ao) {
        double[] p = new double[2];
        p[0] = xy[0] + r * Math.cos(ao * 3.14 / 180);
        p[1] = xy[1] + r * Math.sin(ao * 3.14 / 180);
        return p;
    }

}
