package com.yz.base.map;

import android.content.Context;
import android.view.ViewGroup;

/**
 * @ClassName: IMap
 * @Description: java类作用描述
 * @Author: yz
 * @Date: 2019/11/25 14:00
 */
public interface IMap<T> {

    void onCreate(T t);
    void onSaveInstanceState(T t);
    void onResume();
    void onPause();
    void onDestroy();

    void addMapView(Context context, ViewGroup viewGroup);
    void setCenter(Point point, boolean isAnim);
    void zoomTo(int zoom);
    void zoomIn();
    void zoomOut();
    int getZoom();
    void setMapOrientation(float r);
    void setOnMapClickListener(OnMapClickListener listener);
    void setMapType(int type);
    void invalidateMapView();

    IMarker addMarker();
    IPolyline addPolyline();
    IPolygon addPolygon();
    ICircle addCircle();
}
