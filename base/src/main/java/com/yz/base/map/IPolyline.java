package com.yz.base.map;

import java.util.List;

/**
 * @ClassName: IPolyline
 * @Description: java类作用描述
 * @Author: YZ-PC
 * @Date: 2020/2/5 19:48
 */
public interface IPolyline extends IPoly{

    void addPoint(Point point);
    void setPoints(List<Point> points);
    void setColor(int color);
    void setWidth(int width);
    void setDotted(boolean isDotted);
}
