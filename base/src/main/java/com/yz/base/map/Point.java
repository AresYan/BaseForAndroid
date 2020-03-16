package com.yz.base.map;

import com.yz.base.entity.BaseEntity;

/**
 * @ClassName: Point
 * @Description: java类作用描述
 * @Author: YZ-PC
 * @Date: 2020/2/5 20:18
 */
public class Point extends BaseEntity {

    public Point(double lat, double lng){
        this.lat=lat;
        this.lng=lng;
    }

    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
