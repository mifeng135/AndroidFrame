package com.mifeng.mf.Common;

import org.greenrobot.eventbus.EventBus;

import com.mifeng.mf.MFEventBusIndex;

/**
 * Created by Administrator on 2019/9/3.
 */

public class MFEvent {
    private static volatile MFEvent mInstance;

    public static MFEvent getInstance() {
        if (mInstance == null) {
            synchronized (MFEvent.class) {
                if (mInstance == null) {
                    mInstance = new MFEvent();
                }
            }
        }
        return mInstance;
    }

    MFEvent() {
        EventBus.builder().addIndex(new MFEventBusIndex()).installDefaultEventBus();
    }

    public void register(Object oc) {
        if (!EventBus.getDefault().isRegistered(oc)) {
            EventBus.getDefault().register(oc);
        }
    }

    public void unregister(Object oc) {
        if (EventBus.getDefault().isRegistered(oc)) {
            EventBus.getDefault().unregister(oc);
        }
    }

    public void removeStickyEvent(Object oc) {
        EventBus.getDefault().removeStickyEvent(oc);
    }


    public void postEvent(String cmd, Object oc) {
        EventBus.getDefault().post(new MFPostEvent<>(cmd, oc));
    }


    public void postEvent(String cmd, Object oc, Object eoc) {
        EventBus.getDefault().post(new MFPostEvent<>(cmd, oc, eoc));
    }

    public void postStickyEvent(String cmd, Object oc, Object eoc) {
        EventBus.getDefault().postSticky(new MFPostEvent<>(cmd, oc, eoc));
    }

}
