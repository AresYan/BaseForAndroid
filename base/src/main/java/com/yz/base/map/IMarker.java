package com.yz.base.map;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.core.content.ContextCompat;

/**
 * @ClassName: IMarker
 * @Description: java类作用描述
 * @Author: YZ-PC
 * @Date: 2020/2/5 19:48
 */
public interface IMarker {

    String getId();
    void setExist(boolean exist);
    boolean isExist();

    void setPosition(Point point);
    void setInfoWindowEnable(boolean isInfoWindowEnable);
    void setIcon(Bitmap bitmap);
    void setAnchor(float var1, float var2);
    void setRotateAngle(float r);
    void setVisible(boolean isVisible);
    boolean isVisible();
    void remove();
    void setClickable(boolean isClickable);
    void setDraggable(boolean isDraggable);
    void setOnMarkerClickListener(OnMarkerClickListener listener);
    void setOnMarkerDragListener(OnMarkerDragListener listener);
    IInfoWindow getInfoWindow(Context context);
    boolean isInfoWindowShown();
    boolean isInfoWindowEnable();
}
