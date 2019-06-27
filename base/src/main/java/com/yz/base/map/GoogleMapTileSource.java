package com.yz.base.map;

import com.yz.base.config.BaseContants;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.MapTileIndex;

public class GoogleMapTileSource extends OnlineTileSourceBase {

    public final static String SATELLITE="satellite";
    public final static String ROAD="road";
    public final static String TERRAIN="terrain";

    public final static String[] SATELLITES={
            "http://mt0.google.cn/vt/lyrs=s@126&hl=en-US&gl=US&src=app&s=G",
            "http://mt1.google.cn/vt/lyrs=s@126&hl=en-US&gl=US&src=app&s=G",
            "http://mt2.google.cn/vt/lyrs=s@126&hl=en-US&gl=US&src=app&s=G",
            "http://mt3.google.cn/vt/lyrs=s@126&hl=en-US&gl=US&src=app&s=G"};
    public final static String[] ROADS={
            "http://mt0.google.cn/vt/lyrs=m@209712068&hl=en-US&gl=US&src=app&s=G",
            "http://mt1.google.cn/vt/lyrs=m@209712068&hl=en-US&gl=US&src=app&s=G",
            "http://mt2.google.cn/vt/lyrs=m@209712068&hl=en-US&gl=US&src=app&s=G",
            "http://mt3.google.cn/vt/lyrs=m@209712068&hl=en-US&gl=US&src=app&s=G"};
    public final static String[] TERRAINS={
            "http://mt0.google.cn/vt/lyrs=s@126&hl=en-US&gl=US&src=app&s=G",
            "http://mt1.google.cn/vt/lyrs=s@126&hl=en-US&gl=US&src=app&s=G",
            "http://mt2.google.cn/vt/lyrs=s@126&hl=en-US&gl=US&src=app&s=G",
            "http://mt3.google.cn/vt/lyrs=s@126&hl=en-US&gl=US&src=app&s=G"};

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public GoogleMapTileSource(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
    }

    @Override
    public String getTileURLString(long pMapTileIndex) {
        return getBaseUrl() + "&x=" + MapTileIndex.getX(pMapTileIndex) + "&y=" + MapTileIndex.getY(pMapTileIndex) + "&z=" + MapTileIndex.getZoom(pMapTileIndex);
    }

    public static OnlineTileSourceBase getMapTileSource(int type) {
        String name = "";
        String[] baseUrl = new String[0];
        switch (type){
            case BaseContants.MapType.TYPE_SATELLITE:
                name = SATELLITE;
                baseUrl = SATELLITES;
                break;
            case BaseContants.MapType.TYPE_ROAD:
                name = ROAD;
                baseUrl = ROADS;
                break;
            case BaseContants.MapType.TYPE_TERRAIN:
                name = TERRAIN;
                baseUrl = TERRAINS;
                break;
        }
        GoogleMapTileSource mapTileSource=new GoogleMapTileSource(name, 0, 19, 256, ".jpg", baseUrl);
        mapTileSource.setType(type);
        return mapTileSource;
    }
}
