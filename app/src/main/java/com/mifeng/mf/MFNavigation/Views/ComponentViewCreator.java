package com.mifeng.mf.MFNavigation.Views;

import android.app.Activity;

import com.mifeng.mf.MFNavigation.Interface.IMFView;


public class ComponentViewCreator implements MFViewCreator {
    public ComponentViewCreator() {

    }
    @Override
    public IMFView create(Activity activity, String componentId) {
        IMFView reactView = new MFComponentViewCreator().create(activity, componentId);
        return new ComponentLayout(activity, reactView);
    }
}
