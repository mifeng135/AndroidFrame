package com.mifeng.mf.MFNavigation.Controller;

import com.mifeng.mf.MFNavigation.Views.BehaviourAdapter;
import com.mifeng.mf.MFNavigation.Views.BehaviourDelegate;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;


public class StackBehaviour<V extends ViewGroup> extends BehaviourDelegate<V> {
    public StackBehaviour(BehaviourAdapter<V> delegate) {
        super(delegate);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        return false;
    }



}
