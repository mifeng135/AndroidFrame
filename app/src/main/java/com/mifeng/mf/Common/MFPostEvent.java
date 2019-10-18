package com.mifeng.mf.Common;

public class MFPostEvent<T> {
    private String eventCmd;
    private T data;
    private T extraData;

    public MFPostEvent(String type, T data,T extraData) {
        this.eventCmd = type;
        this.data = data;
        this.extraData = extraData;
    }

    public MFPostEvent(String type, T data) {
        this.eventCmd = type;
        this.data = data;
    }

    public String getEventCmd() {
        return eventCmd;
    }

    public void setEventCmd(String type) {
        this.eventCmd = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public T getExtraData() {
        return extraData;
    }

    public void setExtraData(T extraData) {
        this.extraData = extraData;
    }
}
