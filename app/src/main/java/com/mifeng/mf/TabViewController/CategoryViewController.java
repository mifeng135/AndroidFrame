package com.mifeng.mf.TabViewController;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mifeng.mf.ControllerLogic.CategoryViewControllerLogic;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import com.mifeng.mf.Common.MFEvent;
import com.mifeng.mf.Common.MFPostEvent;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Controller.TabViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFNavigation.Utils.Text;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.R;
import com.mifeng.mf.UIViewController.NavigatorViewController;

/**
 * Created by Administrator on 2019/8/20.
 */

public class CategoryViewController extends UIViewController<LinearLayout> implements TabViewController {


    public CategoryViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new CategoryViewControllerLogic(this);
    }

    @Override
    protected LinearLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createLinearLayout(Color.parseColor("#EEEEEE"));
        MFTitleBar mfTitleBar = createTitleBar(inflater);
        mfTitleBar.setMFSearchText("千万好货供你选");

        View baseView = inflater.inflate(R.layout.category, view, false);
        TextView textView = baseView.findViewById(R.id.push_button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map param = new HashMap();
                param.put("title","Navigator");
                NavigatorViewController navigatorViewController = new NavigatorViewController(getActivity(),"NavigatorViewController",param);
                Navigator.getInstance().push(getId(),navigatorViewController);
            }
        });
        view.addView(mfTitleBar);
        view.addView(baseView);
        return view;
    }


    public MFTitleBar createTitleBar(LayoutInflater inflater) {
        return (MFTitleBar) inflater.inflate(R.layout.category_title_bar_layout, view, false);
    }

    @Override
    public void onViewDidLoad() {
        super.onViewDidLoad();
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

}
