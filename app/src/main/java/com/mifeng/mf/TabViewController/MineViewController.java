package com.mifeng.mf.TabViewController;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import com.mifeng.mf.Common.MFPostEvent;
import com.mifeng.mf.ControllerLogic.MineViewControllerLogic;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Controller.TabViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.R;
import com.mifeng.mf.UIViewController.LoginViewController;

/**
 * Created by Administrator on 2019/8/26.
 */

public class MineViewController extends UIViewController<LinearLayout> implements TabViewController {

    public MineViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new MineViewControllerLogic(this);
    }

    @Override
    protected LinearLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createLinearLayout();
        View baseView = inflater.inflate(R.layout.mine_layout,view,false);
        view.addView(baseView);
        initVIiewTouch(baseView);
        return view;
    }

    private void initVIiewTouch(View baseView) {
        TextView login = baseView.findViewById(R.id.mine_push_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginViewController loginViewController = new LoginViewController(getActivity(),"loginViewController",new HashMap());
                Navigator.getInstance().push(getId(),loginViewController);
            }
        });
    }

    @Override
    public void onViewDidLoad() {
        super.onViewDidLoad();
    }
    @Override
    public void onViewAppeared() {
        super.onViewAppeared();
    }
    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
    }
    @Override
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
        finishHearderRefresh();
    }

    @Override
    public void onLoadMoreLister() {
        finishFooterRefresh();
    }

}
