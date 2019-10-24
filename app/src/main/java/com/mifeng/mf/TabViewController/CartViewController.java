package com.mifeng.mf.TabViewController;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mifeng.mf.Adapter.TestAdapter;
import com.mifeng.mf.Component.MFExpandableListView.ExpandableLayoutListView;
import com.mifeng.mf.ControllerLogic.CartViewControllerLogic;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Controller.TabViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.MsgBean.TestBean;
import com.mifeng.mf.R;

/**
 * Created by Administrator on 2019/8/21.
 */


public class CartViewController extends UIViewController<LinearLayout> implements TabViewController {



    public CartViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new CartViewControllerLogic(this);
    }

    @Override
    protected LinearLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createLinearLayout();
        MFTitleBar mfTitleBar = createNormalTitleBar(inflater);
        mfTitleBar.setMFCenterTextColor(Color.parseColor("#FFFFFF"));
        mfTitleBar.hideLeftIcon();
        View baseView = inflater.inflate(R.layout.cart, view, false);
        view.addView(mfTitleBar);
        view.addView(baseView);

        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) baseView.findViewById(R.id.listview);
        List<TestBean> testBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> strings = new ArrayList<>();
            for (int index = 0; index < i + 1; index ++) {
                strings.add(index + "mmmm");
            }
            TestBean testBean = new TestBean(i + "parent",strings);
            testBeans.add(testBean);
        }

        TestAdapter testAdapter = new TestAdapter(testBeans,getActivity());
        expandableLayoutListView.setAdapter(testAdapter);
        return view;
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


    @Override
    public int getBottomInset() {
        return Navigator.getInstance().getBottomTabsController().getBottomTabs().getHeight();
    }
}
