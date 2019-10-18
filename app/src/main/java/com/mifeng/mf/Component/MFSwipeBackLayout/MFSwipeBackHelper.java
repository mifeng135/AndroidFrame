

package com.mifeng.mf.Component.MFSwipeBackLayout;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;


public class MFSwipeBackHelper {
    private Activity mActivity;
    private Delegate mDelegate;
    private MFSwipeBackLayout mSwipeBackLayout;

    public MFSwipeBackHelper(Activity activity) {
        mActivity = activity;

    }

    public MFSwipeBackLayout initSwipeBackFinish() {
        mSwipeBackLayout = new MFSwipeBackLayout(mActivity);
        mSwipeBackLayout.attachToActivity(mActivity);
        return mSwipeBackLayout;
    }

    public MFSwipeBackHelper setSwipeBackEnable(boolean swipeBackEnable) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setSwipeBackEnable(swipeBackEnable);
        }
        return this;
    }

    public MFSwipeBackHelper setIsOnlyTrackingLeftEdge(boolean isOnlyTrackingLeftEdge) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsOnlyTrackingLeftEdge(isOnlyTrackingLeftEdge);
        }
        return this;
    }


    public MFSwipeBackHelper setIsWeChatStyle(boolean isWeChatStyle) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsWeChatStyle(isWeChatStyle);
        }
        return this;
    }

    public MFSwipeBackHelper setShadowResId(@DrawableRes int shadowResId) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setShadowResId(shadowResId);
        }
        return this;
    }


    public MFSwipeBackHelper setIsNeedShowShadow(boolean isNeedShowShadow) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsNeedShowShadow(isNeedShowShadow);
        }
        return this;
    }

    public MFSwipeBackHelper setIsShadowAlphaGradient(boolean isShadowAlphaGradient) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsShadowAlphaGradient(isShadowAlphaGradient);
        }
        return this;
    }

    public MFSwipeBackHelper setSwipeBackThreshold(@FloatRange(from = 0.0f, to = 1.0f) float threshold) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setSwipeBackThreshold(threshold);
        }
        return this;
    }

    public MFSwipeBackHelper setIsNavigationBarOverlap(boolean overlap) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsNavigationBarOverlap(overlap);
        }
        return this;
    }

    public boolean isSliding() {
        if (mSwipeBackLayout != null) {
            return mSwipeBackLayout.isSliding();
        }
        return false;
    }

    /**
     * 执行跳转到下一个 Activity 的动画
     */
    public void executeForwardAnim() {
        executeForwardAnim(mActivity);
    }

    /**
     * 执行回到到上一个 Activity 的动画
     */
    public void executeBackwardAnim() {
        executeBackwardAnim(mActivity);
    }

    /**
     * 执行滑动返回到到上一个 Activity 的动画
     */
    public void executeSwipeBackAnim() {
        executeSwipeBackAnim(mActivity);
    }

    /**
     * 执行跳转到下一个 Activity 的动画。这里弄成静态方法，方便在 Fragment 中调用
     */
    public static void executeForwardAnim(Activity activity) {

    }

    /**
     * 执行回到到上一个 Activity 的动画。这里弄成静态方法，方便在 Fragment 中调用
     */
    public static void executeBackwardAnim(Activity activity) {

    }

    /**
     * 执行滑动返回到到上一个 Activity 的动画。这里弄成静态方法，方便在 Fragment 中调用
     */
    public static void executeSwipeBackAnim(Activity activity) {

    }

    /**
     * 跳转到下一个 Activity，并且销毁当前 Activity
     *
     * @param cls 下一个 Activity 的 Class
     */
    public void forwardAndFinish(Class<?> cls) {
        forward(cls);
        mActivity.finish();
    }

    /**
     * 跳转到下一个 Activity，不销毁当前 Activity
     *
     * @param cls 下一个 Activity 的 Class
     */
    public void forward(Class<?> cls) {
        MFKeyboardUtil.closeKeyboard(mActivity);
        mActivity.startActivity(new Intent(mActivity, cls));
        executeForwardAnim();
    }

    /**
     * 跳转到下一个 Activity，不销毁当前 Activity
     *
     * @param cls         下一个 Activity 的 Class
     * @param requestCode 请求码
     */
    public void forward(Class<?> cls, int requestCode) {
        forward(new Intent(mActivity, cls), requestCode);
    }

    /**
     * 跳转到下一个 Activity，销毁当前 Activity
     *
     * @param intent 下一个 Activity 的意图对象
     */
    public void forwardAndFinish(Intent intent) {
        forward(intent);
        mActivity.finish();
    }

    /**
     * 跳转到下一个 Activity,不销毁当前 Activity
     *
     * @param intent 下一个 Activity 的意图对象
     */
    public void forward(Intent intent) {
        MFKeyboardUtil.closeKeyboard(mActivity);
        mActivity.startActivity(intent);
        executeForwardAnim();
    }

    /**
     * 跳转到下一个 Activity,不销毁当前 Activity
     *
     * @param intent      下一个 Activity 的意图对象
     * @param requestCode 请求码
     */
    public void forward(Intent intent, int requestCode) {
        MFKeyboardUtil.closeKeyboard(mActivity);
        mActivity.startActivityForResult(intent, requestCode);
        executeForwardAnim();
    }

    /**
     * 回到上一个 Activity，并销毁当前 Activity
     */
    public void backward() {
        MFKeyboardUtil.closeKeyboard(mActivity);
        mActivity.finish();
        executeBackwardAnim();
    }

    /**
     * 滑动返回上一个 Activity，并销毁当前 Activity
     */
    public void swipeBackward() {
        MFKeyboardUtil.closeKeyboard(mActivity);
        mActivity.finish();
        executeSwipeBackAnim();
    }

    /**
     * 回到上一个 Activity，并销毁当前 Activity（应用场景：欢迎、登录、注册这三个界面）
     *
     * @param cls 上一个 Activity 的 Class
     */
    public void backwardAndFinish(Class<?> cls) {
        MFKeyboardUtil.closeKeyboard(mActivity);
        mActivity.startActivity(new Intent(mActivity, cls));
        mActivity.finish();
        executeBackwardAnim();
    }

    public interface Delegate {
        /**
         * 是否支持滑动返回
         *
         * @return
         */
        boolean isSupportSwipeBack();

        /**
         * 正在滑动返回
         *
         * @param slideOffset 从 0 到 1
         */
        void onSwipeBackLayoutSlide(float slideOffset);

        /**
         * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
         */
        void onSwipeBackLayoutCancel();

        /**
         * 滑动返回执行完毕，销毁当前 Activity
         */
        void onSwipeBackLayoutExecuted();
    }
}
