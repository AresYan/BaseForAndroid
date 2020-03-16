package com.yz.base.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;

import java.util.List;

/**
 * @ClassName: IMap
 * @Description: java类作用描述
 * @Author: yz
 * @Date: 2019/11/25 14:00
 */
public interface IMap {

    void addMapView(Context context, ViewGroup viewGroup);

    IMarker addMarker(Point point);
    void setMarkerIcon(IMarker marker, Bitmap bitmap, boolean isAnchorCenter);
    void setMarkerPosition(IMarker marker, Point point) ;
    void setMarkerRotateAngle(IMarker marker, float r);
    void setMarkerVisible(IMarker marker, boolean isVisible);
    boolean isMarkerVisible(IMarker marker);
    void setMarkerData(IMarker marker, Object data);
    void removeMarker(IMarker marker);
    void setInfoWindowEnable(IMarker marker, boolean isEnable);
    boolean isInfoWindowShown(IMarker marker);
    void setOnMarkerClickListener(IMarker marker, OnMarkerClickListener listener);
    void setOnMarkerDragListener(IMarker marker, OnMarkerDragListener listener);

    IPolyline addPolyline(List<Point> points, int color, int w);
    void setPolylinePoints(IPolyline polyline, List<Point> points);
    void setPolylineDotted(IPolyline polyline, boolean isDotted);
    void setPolylineVisible(IPolyline polyline, boolean isVisible);
    boolean isPolylineVisible(IPolyline polyline);
    void removePolyline(IPolyline polyline);

    IPolygon addPolygon(List<Point> points, int fillColor, int strokeColor, int strokeWidth);
    void setPolygonPoints(IPolygon polygon, List<Point> points);
    void setPolygonVisible(IPolygon polygon, boolean isVisible);
    boolean isPolygonVisible(IPolygon polygon);
    void removePolygon(IPolygon polygon);

    void setCenter(Point point, boolean isAnim);
    void zoomTo(int zoom);
    void zoomIn();
    void zoomOut();
    int getZoom();
    void setMapOrientation(float r);
    void setOnMapClickListener(OnMapClickListener listener);
    void setMapType(int type);
    void invalidateMapView();
}
