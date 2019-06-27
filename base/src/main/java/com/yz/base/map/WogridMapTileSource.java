package com.yz.base.map;

import com.yz.base.config.BaseContants;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.MapTileIndex;

public class WogridMapTileSource extends OnlineTileSourceBase {

    public final static String HOST="gis.wogrid.com";

    public final static String SATELLITE="satellite";
    public final static String ROAD="road";
    public final static String TERRAIN="terrain";
    public final static String[] SATELLITES={
            "http://"+HOST+"/satellitemap/China/瓦片_谷歌/"};
    public final static String[] ROADS={
            "http://"+HOST+"/roadmap/China/瓦片_谷歌/"};
    public final static String[] TERRAINS={
            "http://"+HOST+"/terrainmap/China/瓦片_谷歌/"};

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WogridMapTileSource(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
    }

    @Override
    public String getTileURLString(long pMapTileIndex) {
        return getBaseUrl() + MapTileIndex.getZoom(pMapTileIndex) + "/" + MapTileIndex.getX(pMapTileIndex)+ "/" + MapTileIndex.getY(pMapTileIndex) + mImageFilenameEnding;
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
        WogridMapTileSource mapTileSource=new WogridMapTileSource(name, 0, 19, 256, ".jpg", baseUrl);
        mapTileSource.setType(type);
        return mapTileSource;
    }

}
