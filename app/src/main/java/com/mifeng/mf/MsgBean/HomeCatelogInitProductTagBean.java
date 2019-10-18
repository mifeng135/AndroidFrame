package com.mifeng.mf.MsgBean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2019/9/4.
 */

public class HomeCatelogInitProductTagBean implements MultiItemEntity {
    private int code;
    private String message;
    private List<List<String>> data;
    private int itemType = 0;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<List<String>> getData() {
        return data;
    }


    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int type) {
        this.itemType = type;
    }
}
