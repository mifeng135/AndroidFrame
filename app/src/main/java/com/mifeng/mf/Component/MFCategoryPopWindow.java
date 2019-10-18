package com.mifeng.mf.Component;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.mifeng.mf.R;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by Administrator on 2019/9/18.
 */

public class MFCategoryPopWindow extends BasePopupWindow {

    public MFCategoryPopWindow(Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_layout);
    }

    @Override
    public void showPopupWindow() {
        initAnimate();
        super.showPopupWindow();
    }

    @Override
    public void showPopupWindow(View anchorView) {
        initAnimate();
        super.showPopupWindow(anchorView);
    }

    private void initAnimate() {

        int gravity = getPopupGravity();

        float in_fromX = 0;
        float in_toX = 0;
        float in_fromY = 0;
        float in_toY = 0;

        float exit_fromX = 0;
        float exit_toX = 0;
        float exit_fromY = 0;
        float exit_toY = 0;

        switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.TOP:
                in_fromY = 1f;
                exit_toY = 1f;
                break;
            case Gravity.BOTTOM:
                in_fromY = -1f;
                exit_toY = -1f;
                break;
            case Gravity.CENTER_VERTICAL:
                break;
            default:
                break;
        }
        setShowAnimation(createTranslateAnimate(in_fromX, in_toX, in_fromY, in_toY));
        setDismissAnimation(createTranslateAnimate(exit_fromX, exit_toX, exit_fromY, exit_toY));
    }

    public Animation createTranslateAnimate(float fromX, float toX, float fromY, float toY) {
        Animation result = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                fromX,
                Animation.RELATIVE_TO_PARENT,
                toX,
                Animation.RELATIVE_TO_PARENT,
                fromY,
                Animation.RELATIVE_TO_PARENT,
                toY);
        result.setDuration(250);
        return result;
    }
}
