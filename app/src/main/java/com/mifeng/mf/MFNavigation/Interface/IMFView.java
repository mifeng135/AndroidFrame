package com.mifeng.mf.MFNavigation.Interface;

import android.view.View;


public interface IMFView extends IDestroyable {
    boolean isReady();

    View asView();
}
