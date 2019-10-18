package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;

import com.mifeng.mf.MFNavigation.Presenter.ComponentPresenter;
import com.mifeng.mf.MFNavigation.Views.ComponentLayout;
import com.mifeng.mf.MFNavigation.Views.ComponentViewCreator;
import com.mifeng.mf.MFNavigation.Views.MFViewCreator;

import static com.mifeng.mf.MFNavigation.Utils.ObjectUtils.perform;


public class ComponentViewController extends ChildController<ComponentLayout> {
    private final MFViewCreator viewCreator;
    private final ComponentPresenter presenter;


    public ComponentViewController(final Activity activity,
                                   final ChildControllersRegistry childRegistry,
                                   final String id) {
        super(activity, id,childRegistry);
        this.viewCreator = new ComponentViewCreator();
        this.presenter = new ComponentPresenter();
    }


    @NonNull
    @Override
    protected ComponentLayout createView() {
        view = (ComponentLayout) viewCreator.create(getActivity(), getId());
        return (ComponentLayout) view.asView();
    }

    @Override
    public void applyTopInset() {
        if (view != null) presenter.applyTopInsets(view, getTopInset());
    }

    @Override
    public int getTopInset() {
        int statusBarInset = 0;
        return statusBarInset + perform(getParentController(), 0, p -> p.getTopInset(this));
    }
    @Override
    public void applyBottomInset() {
        if (view != null) presenter.applyBottomInset(view, getBottomInset());
    }

    @Override
    protected WindowInsetsCompat applyWindowInsets(ViewController view, WindowInsetsCompat insets) {
        ViewCompat.onApplyWindowInsets(view.getView(), insets.replaceSystemWindowInsets(
                insets.getSystemWindowInsetLeft(),
                insets.getSystemWindowInsetTop(),
                insets.getSystemWindowInsetRight(),
                Math.max(insets.getSystemWindowInsetBottom() - getBottomInset(), 0)
        ));
        return insets;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
