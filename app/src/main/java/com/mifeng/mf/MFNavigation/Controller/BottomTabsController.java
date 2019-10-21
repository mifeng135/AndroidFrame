package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.mifeng.mf.MFNavigation.Utils.ImageLoader;
import com.mifeng.mf.MFNavigation.Views.BottomTab.BottomTabs;
import com.mifeng.mf.MFNavigation.Views.BottomTab.BottomTabsAttacher;
import com.mifeng.mf.MFNavigation.Views.BottomTab.BottomTabsLayout;
import com.mifeng.mf.MFNavigation.Views.BottomTab.TabSelector;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.mifeng.mf.MFNavigation.Utils.CollectionUtils.forEach;
import static com.mifeng.mf.MFNavigation.Utils.ObjectUtils.perform;

public class BottomTabsController extends ParentController<BottomTabsLayout> implements AHBottomNavigation.OnTabSelectedListener, TabSelector {

    private BottomTabs bottomTabs;
    private List<ViewController> tabs;
    private ImageLoader imageLoader;
    private final BottomTabsAttacher tabsAttacher;


    private String [] bottomTabIcon = {"home","category","cart","my"};
    private String [] bottomTabLable = {"首页","分类","购物车","我的"};


    public BottomTabsController(Activity activity, List<ViewController> tabs, ChildControllersRegistry childRegistry, ImageLoader imageLoader, String id, BottomTabsAttacher tabsAttacher) {
        super(activity, id, childRegistry);
        this.tabs = tabs;
        this.imageLoader = imageLoader;
        this.tabsAttacher = tabsAttacher;
        forEach(tabs, tab -> tab.setParentController(this));
    }


    @NonNull
    @Override
    protected BottomTabsLayout createView() {
        BottomTabsLayout root = new BottomTabsLayout(getActivity());
        bottomTabs = createBottomTabs();
        tabsAttacher.init(root);
        bottomTabs.setOnTabSelectedListener(this);
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        root.addView(bottomTabs, lp);
        bottomTabs.addItems(createTabs());
        tabsAttacher.attach();
        return root;
    }

    @NonNull
    protected BottomTabs createBottomTabs() {
        return new BottomTabs(getActivity());
    }

    @CallSuper
    public void onViewAppeared() {
        super.onViewAppeared();
        initTabOption();
        bottomTabs.superCreateItems();
    }

    @CallSuper
    public void onViewDisappear() {
        super.onViewDisappear();
    }

    @Override
    protected ViewController getCurrentChild() {
        return tabs.get(bottomTabs == null ? 0 : bottomTabs.getCurrentItem());
    }

    @Override
    public boolean onTabSelected(int index, boolean wasSelected) {
        if (wasSelected) return false;
        selectTab(index);
        return false;
    }


    public void initTabOption() {
        for (int i = 0; i < tabs.size(); i++) {
            bottomTabs.setIconActiveColor(i,(Color.parseColor("#7EA854")));
            bottomTabs.setIconInactiveColor(i,(Color.parseColor("#000000")));

            bottomTabs.setTitleActiveColor(i, (Color.parseColor("#7EA854")));
            bottomTabs.setTitleInactiveColor(i, (Color.parseColor("#000000")));
        }
    }
    private List<AHBottomNavigationItem> createTabs() {
        if (tabs.size() > 5) throw new RuntimeException("Too many tabs!");
        List<AHBottomNavigationItem> ahBottomNavigationItemsList = new ArrayList<>();

        for (int i = 0; i < tabs.size(); i++) {
            String itemLable = bottomTabLable[i];
            String itemIcon = bottomTabIcon[i];
            AHBottomNavigationItem ahBottomNavigationItem = new AHBottomNavigationItem(itemLable,imageLoader.loadIcon(getActivity(), itemIcon),"");
            ahBottomNavigationItemsList.add(ahBottomNavigationItem);
        }
        return ahBottomNavigationItemsList;
    }

    public int getSelectedIndex() {
        return bottomTabs.getCurrentItem();
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, ViewGroup child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        perform(findController(child), ViewController::applyBottomInset);
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    public int getBottomInset(ViewController child) {
        int bottomTabsInset = bottomTabs.getHeight();
        return bottomTabsInset + perform(getParentController(), 0, p -> p.getBottomInset(this));
    }

    @Override
    public void applyBottomInset() {
        super.applyBottomInset();
    }

    @NonNull
    @Override
    public Collection<? extends ViewController> getChildControllers() {
        return tabs;
    }

    @Override
    public void destroy() {
        tabsAttacher.destroy();
        super.destroy();
    }

    @Override
    public void selectTab(final int newIndex) {
        tabsAttacher.onTabSelected(tabs.get(newIndex));
        getCurrentView().setVisibility(View.INVISIBLE);
        if (getCurrentView().getChildAt(0) != null) {
            getCurrentView().getChildAt(0).setVisibility(View.GONE);
        }
        bottomTabs.setCurrentItem(newIndex, false);
        getCurrentView().setVisibility(View.VISIBLE);
        if (getCurrentView().getChildAt(0) != null) {
            getCurrentView().getChildAt(0).setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    private ViewGroup getCurrentView() {
        return tabs.get(bottomTabs.getCurrentItem()).getView();
    }

    public BottomTabs getBottomTabs() {
        return bottomTabs;
    }
}
