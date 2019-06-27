package com.yz.base.entity;

/**
 * Created by yz on 2018/1/29.
 */

public class PopSelected extends BaseEntity implements Comparable<PopSelected>{

    private int id;
    private int res;
    private int resPress;
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