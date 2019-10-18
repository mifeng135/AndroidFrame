package com.mifeng.mf.MFNavigation.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;

@SuppressLint("ViewConstructor")
public class StackLayout extends CoordinatorLayout implements Component {
    private String stackId;

    public StackLayout(Context context,String stackId) {
        super(context);
        this.stackId = stackId;
    }

    public String getStackId() {
        return stackId;
    }

}
