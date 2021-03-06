package com.yz.base.map;

import android.graphics.Bitmap;

/**
 * @ClassName: IMarker
 * @Description: java类作用描述
 * @Author: YZ-PC
 * @Date: 2020/2/5 19:48
 */
public interface IMarker extends IPoly{

    String getId();
    void setPosition(Point point);
    void setIcon(Bitmap bitmap);
    void setAnchor(float var1, float var2);
    void setRotateAngle(float r);
    void setClickable(boolean isClickable);
    void setDraggable(boolean isDraggable);
    void setOnMarkerClickListener(OnMarkerClickListener listener);
    void setOnMarkerDragListener(OnMarkerDragListener listener);
    void setInfoWindow(IInfoWindow infoWindow);
    IInfoWindow getInfoWindow();
    void showInfoWindow();
    void hideInfoWindow();
    void setInfoWindowEnable(boolean isEnable);
    boolean isInfoWindowShown();
    boolean isInfoWindowEnable();
}
