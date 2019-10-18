package com.mifeng.mf.UIViewController;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Map;

import com.mifeng.mf.Common.MFAnimation;
import com.mifeng.mf.Component.MFSwipeBackLayout.MFSwipeBackLayout;
import com.mifeng.mf.ControllerLogic.GoodDetailViewControllerLogic;
import com.mifeng.mf.MFNavigation.Controller.ChildViewController;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.MFViewAnimator.ViewAnimator;
import com.mifeng.mf.R;

import static com.mifeng.mf.MFTitleBar.Widget.MFTitleBar.ACTION_LEFT_BUTTON;

/**
 * Created by Administrator on 2019/10/12.
 */

public class GoodDetailViewController extends UIViewController<MFSwipeBackLayout> implements ChildViewController {

    private Map param;
    public GoodDetailViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new GoodDetailViewControllerLogic(this);
        this.param = param;
    }

    @NonNull
    @Override
    protected MFSwipeBackLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createSwipeBackLayout();
        FrameLayout frameLayout = createFrameLayout();

        MFTitleBar mfTitleBar = createNormalTitleBar(inflater);
        mfTitleBar.setAlpha(0.0f);
        mfTitleBar.setMFBackgroundColor(Color.parseColor("#00000000"))
                .setMFStatusBarColor(Color.parseColor("#FFFFFF"))
                .setMFCenterTextColor(Color.parseColor("#000000"))
                .hideBottomLine().setMFCenterText("")
                .setListener(new MFTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == ACTION_LEFT_BUTTON) {
                    Navigator.getInstance().popWithElementAnimator(getId());
                }
            }
        });
        View baseView = inflater.inflate(R.layout.good_detail_layout, view, false);
        frameLayout.addView(baseView);
        frameLayout.addView(mfTitleBar);
        view.addView(frameLayout);
        initView(baseView);
        initPopViewAnimator();
        initPushViewAnimator(mfTitleBar);
        return view;
    }

    public void initView(View baseView) {
         MFAnimation.getInstance().zoomImageFromThumb((ImageView)param.get("imageView"),getActivity(),400,false,(ViewGroup)baseView);
    }

    private void initPushViewAnimator(MFTitleBar mfTitleBar) {
        pushViewAnimator = ViewAnimator.animate(mfTitleBar).alpha(0, 1).startDelay(400).duration(450).toViewAnimator();
    }

    private void initPopViewAnimator() {
        popViewAnimator = ViewAnimator.animate(view).alpha(1, 0).duration(400).toViewAnimator();
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
