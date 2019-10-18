package com.mifeng.mf.MFNavigation.Impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.mifeng.mf.MFNavigation.Interface.IMFView;

public class MFView extends FrameLayout implements IMFView{

    public MFView(@NonNull Context context, String componentId) {
        super(context);
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public MFView asView() {
        return this;
    }

    @Override
    public void destroy() {

    }

}
