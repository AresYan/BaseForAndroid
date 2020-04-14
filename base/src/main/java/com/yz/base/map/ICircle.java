package com.yz.base.map;

/**
 * @ClassName: ICircle
 * @Description: java类作用描述
 * @Author: YZ-PC
 * @Date: 2020/2/5 19:48
 */
public interface ICircle extends IPoly{

    void setCircle(Point point, double radius);
    void setFillColor(int color);
    void setStrokeColor(int color);
    void setStrokeWidth(int width);
}
