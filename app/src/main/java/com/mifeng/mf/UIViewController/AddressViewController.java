package com.mifeng.mf.UIViewController;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import com.mifeng.mf.Common.MFClickListener;
import com.mifeng.mf.Component.MFPopupWindows;
import com.mifeng.mf.Component.MFSwipeBackLayout.MFSwipeBackLayout;
import com.mifeng.mf.ControllerLogic.AddressViewControllerLogic;
import com.mifeng.mf.MFNavigation.Controller.ChildViewController;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.R;

import static com.mifeng.mf.MFTitleBar.Widget.MFTitleBar.ACTION_LEFT_BUTTON;

/**
 * Created by Administrator on 2019/9/4.
 */


public class AddressViewController extends UIViewController<MFSwipeBackLayout> implements ChildViewController{



    public AddressViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new AddressViewControllerLogic(this);
    }

    @Override
    protected MFSwipeBackLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createSwipeBackLayout();



        LinearLayout linearLayout = createLinearLayout();

        MFTitleBar mfTitleBar = createNormalTitleBar(inflater);
        mfTitleBar.setMFCenterText("地址管理");
        mfTitleBar.setMFBackgroundColor(Color.parseColor("#FFFFFF"));
        mfTitleBar.setMFCenterTextColor(Color.parseColor("#000000"));
        mfTitleBar.setListener(new MFTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == ACTION_LEFT_BUTTON) {
                    Navigator.getInstance().pop(getId());
                }
            }
        });
        View baseView = inflater.inflate(R.layout.address_layout, view, false);

        linearLayout.addView(mfTitleBar);
        linearLayout.addView(baseView);


        TextView textView = baseView.findViewById(R.id.popup_window);
        textView.setOnClickListener(new MFClickListener() {
            @Override
            public void onMFClick(View v) {
                new MFPopupWindows(getActivity()).showPopupWindow();
            }
        });
        view.addView(linearLayout);
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
}