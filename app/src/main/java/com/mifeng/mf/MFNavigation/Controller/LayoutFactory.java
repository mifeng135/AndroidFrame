package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;

import java.util.List;

import com.mifeng.mf.MFNavigation.Utils.CompatUtils;
import com.mifeng.mf.MFNavigation.Utils.ImageLoader;
import com.mifeng.mf.MFNavigation.Views.BottomTab.BottomTabsAttacher;

public class LayoutFactory {
    private Activity activity;
    private ChildControllersRegistry childRegistry;
    private static volatile LayoutFactory mInstance;

    public static LayoutFactory getInstance() {
        return mInstance;
    }

    public LayoutFactory(Activity activity, ChildControllersRegistry childRegistry) {
        this.activity = activity;
        this.childRegistry = childRegistry;
        mInstance = this;
    }

    public ViewController createStack(List<ViewController> child) {
        String stackId = "stack" + CompatUtils.generateViewId();
        return new StackControllerBuilder(activity)
                .setChildRegistry(childRegistry)
                .setChildren(child)
                .setId(stackId)
                .build();
    }

    public ViewController createBottomTabs(List<ViewController> tabs) {
        String controllerId = "Bottoms" + CompatUtils.generateViewId();
        return new BottomTabsController(activity,
                tabs,
                childRegistry,
                new ImageLoader(),
                controllerId,
                new BottomTabsAttacher(tabs));
    }

}
