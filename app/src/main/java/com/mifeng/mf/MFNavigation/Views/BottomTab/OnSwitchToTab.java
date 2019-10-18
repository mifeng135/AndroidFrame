package com.mifeng.mf.MFNavigation.Views.BottomTab;

import android.view.*;

import java.util.List;

import com.mifeng.mf.MFNavigation.Controller.ViewController;

public class OnSwitchToTab extends AttachMode {
    private final ViewController initialTab;

    public OnSwitchToTab(ViewGroup parent, List<ViewController> tabs) {
        super(parent, tabs);
        this.initialTab = tabs.get(0);
    }

    @Override
    public void attach() {
        attach(initialTab);
    }

    @Override
    public void onTabSelected(ViewController tab) {
        if (tab != initialTab && isNotAttached(tab)) {
            attach(tab);
        }
    }

    private boolean isNotAttached(ViewController tab) {
        return tab.getView().getParent() == null;
    }
}
