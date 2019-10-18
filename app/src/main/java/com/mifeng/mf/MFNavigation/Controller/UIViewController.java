package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayDeque;

import com.mifeng.mf.Component.MFRefreshFooter;
import com.mifeng.mf.Component.MFRefreshHeader;
import com.mifeng.mf.Component.MFSwipeBackLayout.MFKeyboardUtil;
import com.mifeng.mf.Component.MFSwipeBackLayout.MFSwipeBackHelper;
import com.mifeng.mf.Component.MFSwipeBackLayout.MFSwipeBackLayout;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFNavigation.Presenter.ComponentPresenter;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.R;

/**
 * Created by Administrator on 2019/8/31.
 */

public abstract class UIViewController<T extends ViewGroup> extends ChildController<T> {

    private final ComponentPresenter presenter;


    private boolean enableHearderRefresh = false;
    private boolean enableFooterRefresh = false;

    private SmartRefreshLayout smartRefreshLayout;


    public UIViewController(final Activity activity, final String id) {
        super(activity, id, Navigator.getInstance().getChildRegistry());
        this.presenter = new ComponentPresenter();
    }


    @Override
    public void onViewDidLoad() {
        super.onViewDidLoad();
    }

    @Override
    public void onViewDidUnload() {
        super.onViewDidUnload();
    }


    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
    }

    @Override
    public void onViewAppeared() {
        super.onViewAppeared();
    }

    public LinearLayout createLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.parseColor("#F1F1F1"));
        return linearLayout;
    }


    public LinearLayout createLinearLayout(int color) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(color);
        return linearLayout;
    }


    public MFSwipeBackLayout createSwipeBackLayout() {
        MFSwipeBackHelper swipeBackHelper = new MFSwipeBackHelper(getActivity());
        MFSwipeBackLayout swipeBackLayout = swipeBackHelper.initSwipeBackFinish();

        swipeBackLayout.setPanelSlideListener(new MFSwipeBackLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

                if (slideOffset < 0.03) {
                    MFKeyboardUtil.closeKeyboard(getActivity());
                }

                ArrayDeque<ChildController> childControllers = Navigator.getInstance().getChildRegistry().getChildren();
                Object[] objects = childControllers.toArray();
                ViewController preChild = (ViewController) objects[1];
                if (preChild != null) {
                    if (preChild.getView().getTranslationX() == 0) {
                        preChild.getView().setTranslationX(-150f);
                    }
                    ViewCompat.setTranslationX(preChild.getView(), 150 * slideOffset - 150);
                }
            }

            @Override
            public void onPanelOpened(View panel) {
                Navigator.getInstance().popSwipeBack(getId());
            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
        swipeBackHelper.setSwipeBackEnable(true);
        swipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        swipeBackHelper.setSwipeBackThreshold(0.3f);
        swipeBackHelper.setIsNavigationBarOverlap(false);
        swipeBackHelper.setIsNeedShowShadow(true);
        return swipeBackLayout;
    }
    public FrameLayout createFrameLayout() {
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        return frameLayout;
    }

    public MFTitleBar createNormalTitleBar(LayoutInflater inflater) {
        return (MFTitleBar) inflater.inflate(R.layout.common_title_bar, view, false);
    }

    public SmartRefreshLayout createSmartRefreshLayout(LayoutInflater inflater) {
        smartRefreshLayout = (SmartRefreshLayout) inflater.inflate(R.layout.common_refresh_layout, view, false);
        smartRefreshLayout.setEnableRefresh(enableHearderRefresh);
        smartRefreshLayout.setEnableLoadMore(enableFooterRefresh);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.resetNoMoreData();
                ((MFRefreshFooter)smartRefreshLayout.getRefreshFooter()).resetView();
                onRefreshLister();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                onLoadMoreLister();
            }
        });
        smartRefreshLayout.setRefreshHeader(new MFRefreshHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new MFRefreshFooter(getActivity()));
        smartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);
        return smartRefreshLayout;
    }

    public void onRefreshLister() {

    }

    public void onLoadMoreLister() {

    }

    public void finishHearderRefresh() {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
        }
    }


    public void setNoMoreData(boolean value) {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setNoMoreData(value);
        }
    }

    public void finishFooterRefresh() {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishLoadMore();
        }
    }

    public void finishFooterRefresh(boolean success,boolean noMoreData) {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishLoadMore(500,success,noMoreData);
        }
    }
    protected void setEnableHearderRefresh(boolean enableHearderRefresh) {
        this.enableHearderRefresh = enableHearderRefresh;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableRefresh(enableHearderRefresh);
        }
    }

    protected void setEnableFooterRefresh(boolean enableFooterRefresh) {
        this.enableFooterRefresh = enableFooterRefresh;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableLoadMore(enableFooterRefresh);
        }
    }


    @NonNull
    @Override
    public T getView() {
        return (T) super.getView();
    }

    @NonNull
    @Override
    protected abstract T createView();

    @Override
    public int getTopInset() {
        return 0;
    }

    @Override
    public int getBottomInset() {
        return 0;
    }

    @Override
    public void applyTopInset() {
        if (view != null) presenter.applyTopInsets(view, getTopInset());
    }

    @Override
    public void applyBottomInset() {
        if (view != null) presenter.applyBottomInset(view, getBottomInset());
    }

    @Override
    protected WindowInsetsCompat applyWindowInsets(ViewController view, WindowInsetsCompat insets) {
        ViewCompat.onApplyWindowInsets(
                view.getView(),
                insets.replaceSystemWindowInsets(
                        insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        Math.max(insets.getSystemWindowInsetBottom() - getBottomInset(), 0)));
        return insets;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
