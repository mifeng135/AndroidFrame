package com.mifeng.mf.TabViewController;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import com.mifeng.mf.ControllerLogic.HomeViewControllerLogic;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mifeng.mf.Adapter.HomeAdapter;
import com.mifeng.mf.Common.MFEvent;
import com.mifeng.mf.Common.MFPostEvent;
import com.mifeng.mf.Constant.MFMsgDefine;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Controller.TabViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFNavigation.Utils.ImageLoader;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.R;
import com.mifeng.mf.UIViewController.GoodDetailViewController;

import static com.mifeng.mf.MFTitleBar.Widget.MFTitleBar.ACTION_RIGHT_BUTTON;


public class HomeViewController extends UIViewController<LinearLayout> implements TabViewController {


    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    int page = 0;

    public HomeViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new HomeViewControllerLogic(this);
    }

    @Override
    protected LinearLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createLinearLayout();

        MFTitleBar titleBar = createTitleBar(inflater);
        titleBar.setMFSearchText("请输入搜索内容").setListener(new MFTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == ACTION_RIGHT_BUTTON) {

                }
            }
        });
        titleBar.setRightIcon(new ImageLoader().loadIcon(getActivity(), "message_icon"), 30, 30);
        titleBar.setRightBadge(3);
        SmartRefreshLayout smartRefreshLayout = createSmartRefreshLayout(inflater);
        setEnableHearderRefresh(true);
        setEnableFooterRefresh(true);
        view.addView(titleBar);
        view.addView(smartRefreshLayout);

        View baseView = inflater.inflate(R.layout.home, view, false);
        smartRefreshLayout.addView(baseView);

        recyclerView = baseView.findViewById(R.id.home_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(new ArrayList(), getId(), getActivity());
        recyclerView.setAdapter(homeAdapter);

        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.home_recommond_image:
                        Drawable drawable = ((ImageView) view).getDrawable();

                        Map param = new HashMap();
                        param.put("animatorDrawable", drawable);
                        param.put("imageView", view);
                        GoodDetailViewController goodDetailViewController = new GoodDetailViewController(getActivity(), "GoodDetailViewController", param);
                        Navigator.getInstance().pushWithElementAnimator(getId(), goodDetailViewController);
                        break;
                }
            }
        });

        return view;
    }


    public MFTitleBar createTitleBar(LayoutInflater inflater) {
        return (MFTitleBar) inflater.inflate(R.layout.home_title_bar, view, false);
    }

    @Override
    public void onViewDidLoad() {
        super.onViewDidLoad();
        Map map = new HashMap();
        ((HomeViewControllerLogic) baseLogicHander).requestHomeInitProductTag(new HashMap<>());
    }

    @Override
    public void onViewAppeared() {
        super.onViewAppeared();
        MFEvent.getInstance().register(this);
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        MFEvent.getInstance().unregister(this);
    }

    public void onViewDidUnload() {
        super.onViewDidUnload();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvMsg(MFPostEvent messageEvent) {
        String eventCmd = messageEvent.getEventCmd();
    }

    @Override
    public int getBottomInset() {
        return Navigator.getInstance().getBottomTabsController().getBottomTabs().getHeight();
    }

    @Override
    public void onRefreshLister() {
        ((HomeViewControllerLogic) baseLogicHander).requestHomeInitProductTag(new HashMap<>());
    }

    @Override
    public void onLoadMoreLister() {
        Map param = new HashMap();
        param.put("categoryId", "all");
        param.put("p", page + 1);

        Map sortAttrs = new HashMap();
        sortAttrs.put("type", "");
        sortAttrs.put("sort", "");
        param.put("sortAttrs", sortAttrs);
        ((HomeViewControllerLogic) baseLogicHander).requestCategoryProductByCategory(param);
    }

    public void refreshHomeUI(List<MultiItemEntity> dataList, Map extra) {
        homeAdapter.setNewData(dataList);
        if (extra.get("p") != null) {
            page = (int) extra.get("p");
        } else {
            page = 0;
            finishHearderRefresh();
        }
        if (extra.get("nextRefresh") != null) {
            boolean nextRefresh = (boolean) extra.get("nextRefresh");
            if (nextRefresh == false) {
                finishFooterRefresh(false, true);
            } else {
                finishFooterRefresh();
            }
        }
        homeAdapter.setNewData(dataList);
    }
}