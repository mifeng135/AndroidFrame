package com.mifeng.mf.MFNavigation.Views.BottomTab;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.mifeng.mf.MFNavigation.Controller.ViewController;

import static com.mifeng.mf.MFNavigation.Utils.CoordinatorLayoutUtils.matchParentWithBehaviour;
import static com.mifeng.mf.MFNavigation.Views.BottomTab.TabsAttachMode.AFTER_INITIAL_TAB;


public abstract class AttachMode {
    protected final ViewGroup parent;
    protected final List<ViewController> tabs;
    final ViewController initialTab;

    public static AttachMode get(ViewGroup parent, List<ViewController> tabs) {
        switch (AFTER_INITIAL_TAB) {
            case AFTER_INITIAL_TAB:
                return new AfterInitialTab(parent, tabs);
            case ON_SWITCH_TO_TAB:
                return new OnSwitchToTab(parent, tabs);
            case UNDEFINED:
            case TOGETHER:
            default:
                return new Together(parent, tabs);
        }
    }

    AttachMode(ViewGroup parent, List<ViewController> tabs) {
        this.parent = parent;
        this.tabs = tabs;
        initialTab = tabs.get(0);
    }

    public abstract void attach();

    public void destroy() {

    }

    public void onTabSelected(ViewController tab) {

    }
    public void attach(ViewController tab) {
        ViewGroup view = tab.getView();
        view.setVisibility(tab == initialTab ? View.VISIBLE : View.INVISIBLE);
        view.getChildCount();
        if ( view.getChildAt(0) != null) {
            view.getChildAt(0).setVisibility(tab == initialTab ? View.VISIBLE : View.INVISIBLE);
        }
        parent.addView(view, matchParentWithBehaviour(new BottomTabsBehaviour(tab.getParentController())));
    }
}
