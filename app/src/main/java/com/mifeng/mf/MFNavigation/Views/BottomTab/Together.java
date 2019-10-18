package com.mifeng.mf.MFNavigation.Views.BottomTab;

import android.view.ViewGroup;

import java.util.List;

import com.mifeng.mf.MFNavigation.Controller.ViewController;

import static com.mifeng.mf.MFNavigation.Utils.CollectionUtils.forEach;

public class Together extends AttachMode {
    public Together(ViewGroup parent, List<ViewController> tabs) {
        super(parent, tabs);
    }

    @Override
    public void attach() {
        forEach(tabs, this::attach);
    }
}