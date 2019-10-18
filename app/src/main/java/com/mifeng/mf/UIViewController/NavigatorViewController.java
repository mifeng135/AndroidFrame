package com.mifeng.mf.UIViewController;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mifeng.mf.Component.MFSwipeBackLayout.MFSwipeBackLayout;
import com.mifeng.mf.ControllerLogic.NavigatorViewControllerLogic;
import com.mifeng.mf.MFNavigation.Controller.ChildViewController;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFNavigation.Utils.CompatUtils;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.R;

import java.util.HashMap;
import java.util.Map;

import static com.mifeng.mf.MFTitleBar.Widget.MFTitleBar.ACTION_LEFT_BUTTON;

/**
 * Created by Administrator on 2019/10/18.
 */

public class NavigatorViewController extends UIViewController<MFSwipeBackLayout> implements ChildViewController{

    private Map param;
    public NavigatorViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new NavigatorViewControllerLogic(this);
        this.param = param;
    }


    @NonNull
    @Override
    protected MFSwipeBackLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createSwipeBackLayout();
        LinearLayout linearLayout = createLinearLayout();
        MFTitleBar mfTitleBar = createNormalTitleBar(inflater);
        String title = (String)param.get("title");
        mfTitleBar.setMFCenterText(title);
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
        View baseView = inflater.inflate(R.layout.navigator_layout, view, false);
        linearLayout.addView(mfTitleBar);
        linearLayout.addView(baseView);
        view.addView(linearLayout);
        initButtonTouchEvent(baseView);
        return view;
    }



    private void initButtonTouchEvent(View baseView) {
        TextView pushView = baseView.findViewById(R.id.push_button);
        pushView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map param = new HashMap();
                param.put("title","PUSH");
                NavigatorViewController navigatorViewController = new NavigatorViewController(getActivity(),CompatUtils.generateViewId() + "",param);
                Navigator.getInstance().push(getId(),navigatorViewController);
            }
        });
        TextView popView = baseView.findViewById(R.id.pop_button);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.getInstance().pop(getId());
            }
        });
        TextView replaceView = baseView.findViewById(R.id.replace_button);
        replaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map param = new HashMap();
                param.put("title","REPLACE");
                NavigatorViewController navigatorViewController = new NavigatorViewController(getActivity(), CompatUtils.generateViewId() + "",param);
                Navigator.getInstance().replace(getId(),navigatorViewController);
            }
        });
        TextView popToRoot = baseView.findViewById(R.id.popToRoot_button);
        popToRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.getInstance().popToRoot(getId());
            }
        });
        TextView popTo = baseView.findViewById(R.id.popTo_button);
        popTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.getInstance().popTo("NavigatorViewController");
            }
        });
        TextView switchTab = baseView.findViewById(R.id.switchTab_button);
        switchTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.getInstance().switchTab(getId(),0);
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
}
