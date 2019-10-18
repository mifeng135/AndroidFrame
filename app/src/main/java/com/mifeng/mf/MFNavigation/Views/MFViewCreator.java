package com.mifeng.mf.MFNavigation.Views;

import android.app.Activity;

import com.mifeng.mf.MFNavigation.Interface.IMFView;

public interface MFViewCreator {
    IMFView create(Activity activity, String componentId);
}
