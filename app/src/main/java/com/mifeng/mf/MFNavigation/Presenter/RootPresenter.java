package com.mifeng.mf.MFNavigation.Presenter;

import android.support.design.widget.CoordinatorLayout;

import com.mifeng.mf.MFNavigation.Controller.ViewController;
import com.mifeng.mf.MFNavigation.Utils.CommandListener;
import com.mifeng.mf.MFNavigation.Views.BehaviourDelegate;

import static com.mifeng.mf.MFNavigation.Utils.CoordinatorLayoutUtils.matchParentWithBehaviour;


public class RootPresenter {
    private CoordinatorLayout rootLayout;
    public void setRootContainer(CoordinatorLayout rootLayout) {
        this.rootLayout = rootLayout;
    }
    public void setRoot(ViewController root, CommandListener listener) {
        rootLayout.addView(root.getView(), matchParentWithBehaviour(new BehaviourDelegate(root)));
        animateSetRootAndReportSuccess(root, listener);
    }

    private void animateSetRootAndReportSuccess(ViewController root, CommandListener listener) {
        listener.onSuccess(root.getId());
    }
}
