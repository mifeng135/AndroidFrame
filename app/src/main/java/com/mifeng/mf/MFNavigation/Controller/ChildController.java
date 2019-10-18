package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;
import android.view.ViewGroup;

import com.mifeng.mf.MFNavigation.Utils.StatusBarUtils;
import com.mifeng.mf.MFNavigation.Views.Component;

/**
 * Created by Administrator on 2019/8/19.
 */

public abstract class ChildController<T extends ViewGroup> extends ViewController<T> {

    private final ChildControllersRegistry childRegistry;

    public ChildControllersRegistry getChildRegistry() {
        return childRegistry;
    }

    protected ChildController(Activity activity, String id, ChildControllersRegistry childRegistry) {
        super(activity,id);
        this.childRegistry = childRegistry;
    }

    @Override
    public T getView() {
        if (view == null) {
            super.getView();
            view.setFitsSystemWindows(true);
            ViewCompat.setOnApplyWindowInsetsListener(view, this::onApplyWindowInsets);
        }
        return view;
    }

    @Override
    public void onViewAppeared() {
        super.onViewAppeared();
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
    }

    @Override
    public void destroy() {
        if (!isDestroyed() && getView() instanceof Component) {
            performOnParentController(parent -> parent.onChildDestroyed(this));
        }
        super.destroy();
    }

    private WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
        StatusBarUtils.saveStatusBarHeight(insets.getSystemWindowInsetTop());
        return applyWindowInsets(findController(view), insets);
    }

    protected WindowInsetsCompat applyWindowInsets(ViewController view, WindowInsetsCompat insets) {
        return insets.replaceSystemWindowInsets(
                insets.getSystemWindowInsetLeft(),
                0,
                insets.getSystemWindowInsetRight(),
                insets.getSystemWindowInsetBottom()
        );
    }
}
