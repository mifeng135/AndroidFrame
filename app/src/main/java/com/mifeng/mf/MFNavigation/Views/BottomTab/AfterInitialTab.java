package com.mifeng.mf.MFNavigation.Views.BottomTab;

import android.view.*;


import java.util.*;

import com.mifeng.mf.MFNavigation.Controller.ViewController;

import static com.mifeng.mf.MFNavigation.Utils.CollectionUtils.filter;
import static com.mifeng.mf.MFNavigation.Utils.CollectionUtils.forEach;


public class AfterInitialTab extends AttachMode {
    private final Runnable attachOtherTabs;

    public AfterInitialTab(ViewGroup parent, List<ViewController> tabs) {
        super(parent, tabs);
        attachOtherTabs = () -> forEach(otherTabs(), this::attach);
    }

    @Override
    public void attach() {
        initialTab.addOnAppearedListener(attachOtherTabs);
        attach(initialTab);
    }

    @Override
    public void destroy() {
        initialTab.removeOnAppearedListener(attachOtherTabs);
    }

    private List<ViewController> otherTabs() {
        return filter(tabs, t -> t != initialTab);
    }
}
