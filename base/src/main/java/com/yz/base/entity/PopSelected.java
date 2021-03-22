package com.yz.base.entity;

/**
 * Created by yz on 2018/1/29.
 */

public class PopSelected extends BaseEntity implements Comparable<PopSelected>{

    public static final int RES_TOP=1;
    public static final int RES_BOTTOM=2;
    public static final int RES_LEFT=3;
    public static final int RES_RIGHT=4;

    private int id;
    private int res;
    private int resPress;
    private int resPosition;
    private String name;
    private boolean isSelected;
    private int position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getResPress() {
        return resPress;
    }

    public void setResPress(int resPress) {
        this.resPress = resPress;
    }

    public int getResPosition() {
        return resPosition;
    }

    public void setResPosition(int resPosition) {
        this.resPosition = resPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(PopSelected o) {
        return this.getId() - o.getId();
    }
}