package com.mifeng.mf.MFNavigation.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.mifeng.mf.MFNavigation.Interface.IMFView;

import static com.mifeng.mf.MFNavigation.Utils.CoordinatorLayoutUtils.matchParentLP;

@SuppressLint("ViewConstructor")
public class ComponentLayout extends CoordinatorLayout implements MFComponent {

    private IMFView mfView;
    public ComponentLayout(Context context, IMFView reactView) {
        super(context);
        this.mfView = reactView;
        addView(reactView.asView(), matchParentLP());
    }

    @Override
    public boolean isReady() {
        return mfView.isReady();
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void destroy() {
        mfView.destroy();
    }

}
