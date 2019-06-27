package com.yz.base.item;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yz.base.entity.BaseEntity;

/**
 * Created by YZ on 2016/11/10.
 */
public class CommonItem extends BaseEntity implements MultiItemEntity {

    public final static int COMMON_TYPE_SELECT=0;
    public final static int COMMON_TYPE_SWITCH=1;
    public final static int COMMON_TYPE_EDIT=2;
    public final static int COMMON_TYPE_COMMIT=3;
    public final static int COMMON_TYPE_ADD_IMG=4;
    public final static int COMMON_TYPE_FEEDBACK=5;
    public final static int COMMON_TYPE_MSG=6;
    public final static int COMMON_TYPE_TEXT=7;
    public final static int COMMON_TYPE_IMG=8;
    public final static int COMMON_TYPE_PROGRESS=9;

    private int id;
    private String title;
    private String hint;
    private String content;
    private int type;
    private int position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getItemType() {
        return getType();
    }
}
