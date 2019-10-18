package com.mifeng.mf.Component.MFSwipeBackLayout;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.FrameLayout;


class MFSwipeBackShadowView extends FrameLayout {
    private static final float WE_CHAT_STYLE_MAX_OFFSET = 0.75f;
    private Activity mActivity;
    private View mShadowView;

    /**
     * 是否显示滑动返回的阴影效果
     */
    private boolean mIsNeedShowShadow = true;

    /**
     * 阴影区域的透明度是否根据滑动的距离渐变
     */
    private boolean mIsShadowAlphaGradient = true;
    /**
     * 是否是微信滑动返回样式
     */
    private boolean mIsWeChatStyle = true;

    private boolean mIsCurrentActivityTranslucent;

    MFSwipeBackShadowView(Activity activity) {
        super(activity);
        mActivity = activity;

        TypedArray typedArray = mActivity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowIsTranslucent
        });
        mIsCurrentActivityTranslucent = typedArray.getBoolean(0, false);
        typedArray.recycle();
    }

    /**
     * 设置是否显示滑动返回的阴影效果。默认值为 true
     */
    void setIsNeedShowShadow(boolean isNeedShowShadow) {
        mIsNeedShowShadow = isNeedShowShadow;
        updateShadow();
    }

    /**
     * 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
     */
    void setShadowResId(@DrawableRes int shadowResId) {
    }

    /**
     * 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
     */
    void setIsShadowAlphaGradient(boolean isShadowAlphaGradient) {
        mIsShadowAlphaGradient = isShadowAlphaGradient;
    }

    /**
     * 设置是否是微信滑动返回样式。默认值为 true
     */
    void setIsWeChatStyle(boolean isWeChatStyle) {
        mIsWeChatStyle = isWeChatStyle;
    }

    private void updateShadow() {
        mShadowView = new View(getContext());
        mShadowView.setBackgroundColor(Color.parseColor("#7d222222"));
        addView(mShadowView, getChildCount(), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
    @Override
    protected void dispatchDraw(final Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mIsCurrentActivityTranslucent) {
            return;
        }
    }


    void setShadowAlpha(float alpha) {
        if (mIsNeedShowShadow && mIsShadowAlphaGradient) {
            mShadowView.setAlpha(alpha);
        }
    }

    void onPanelSlide(float slideOffset) {

    }

    private void onPanelSlide(Activity currentActivity, float slideOffset) {

    }

    void onPanelClosed() {

    }

    private void onPanelClosed(Activity currentActivity) {

    }
}
