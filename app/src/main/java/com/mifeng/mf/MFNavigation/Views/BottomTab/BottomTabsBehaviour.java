package com.mifeng.mf.MFNavigation.Views.BottomTab;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;

import com.mifeng.mf.MFNavigation.Views.BehaviourAdapter;
import com.mifeng.mf.MFNavigation.Views.BehaviourDelegate;


public class BottomTabsBehaviour<V extends ViewGroup> extends BehaviourDelegate<V> {
    public BottomTabsBehaviour(BehaviourAdapter<V> delegate) {
        super(delegate);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull View dependency) {
        return dependency instanceof BottomTabs;
    }
}
