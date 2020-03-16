package com.yz.base.map;

import java.util.List;

/**
 * @ClassName: IPolygon
 * @Description: java类作用描述
 * @Author: YZ-PC
 * @Date: 2020/2/5 19:48
 */
public interface IPolygon {

    void setVisible(boolean isVisible);
    boolean isVisible();

    void setPoints(List<Point> points);
    void setFillColor(int color);
    void setStrokeColor(int color);
    void setStrokeWidth(int width);
    void remove();
}
