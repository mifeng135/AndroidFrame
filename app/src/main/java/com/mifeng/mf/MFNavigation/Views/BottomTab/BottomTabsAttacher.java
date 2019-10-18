package com.mifeng.mf.MFNavigation.Views.BottomTab;

import com.mifeng.mf.MFNavigation.Controller.ViewController;

import android.support.annotation.VisibleForTesting;
import android.view.ViewGroup;


import java.util.List;

public class BottomTabsAttacher {
    private final List<ViewController> tabs;
    @VisibleForTesting
    AttachMode attachStrategy;

    public BottomTabsAttacher(List<ViewController> tabs) {
        this.tabs = tabs;
    }

    public void init(ViewGroup parent) {
        attachStrategy = AttachMode.get(parent, tabs);
    }

    public void attach() {
        attachStrategy.attach();
    }

    public void destroy() {
        attachStrategy.destroy();
    }

    public void onTabSelected(ViewController tab) {
        attachStrategy.onTabSelected(tab);
    }
}
