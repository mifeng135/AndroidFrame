package com.mifeng.mf.Common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import static com.mifeng.mf.MFNavigation.Utils.StatusBarUtils.getStatusBarHeight;

/**
 * Created by Administrator on 2019/10/12.
 */

public class MFAnimation {

    private Animator mCurrentAnimator;
    private ImageView mImageZoom;
    private AnimatorSet set1;
    private Rect mStartBounds;
    private long mDuration;
    private float mStartScale;
    private ImageView mThumbView;
    private Activity mActivity;

    private static volatile MFAnimation mInstance;

    public static MFAnimation getInstance() {
        if (mInstance == null) {
            synchronized (MFAnimation.class) {
                if (mInstance == null) {
                    mInstance = new MFAnimation();
                }
            }
        }
        return mInstance;
    }

    public void zoomImageFromThumb(final View thumbView, Activity activity, long duration, boolean centerCrop, ViewGroup parentView) {

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        mDuration = duration;
        mThumbView = (ImageView) thumbView;
        mActivity = activity;
        ContentFrameLayout container = mActivity.findViewById(android.R.id.content);
        mImageZoom = new ImageView(thumbView.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
        mImageZoom.setLayoutParams(params);
        mImageZoom.setImageDrawable(((ImageView) thumbView).getDrawable());
        mImageZoom.setVisibility(View.GONE);
        parentView.addView(mImageZoom);

        mImageZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        int imageOrgHeight = thumbView.getHeight();

        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
        final float startScale;
        float set;
        float startScaleFinal;
        if ((float) finalBounds.width() / (float) finalBounds.height() > (float) startBounds.width() / (float) startBounds.height()) {
            startScale = (float) startBounds.height() / (float) finalBounds.height();
            set = startScale * (float) finalBounds.width();
            startScaleFinal = (set - (float) startBounds.width()) / 2.0F;
            startBounds.left = (int) ((float) startBounds.left - startScaleFinal);
            startBounds.right = (int) ((float) startBounds.right + startScaleFinal);
        } else {
            startScale = (float) startBounds.width() / (float) finalBounds.width();
            set = startScale * (float) finalBounds.height();
            startScaleFinal = (set - (float) startBounds.height()) / 2.0F;
            startBounds.top = (int) ((float) startBounds.top - startScaleFinal);
            startBounds.bottom = (int) ((float) startBounds.bottom + startScaleFinal);
        }
        mImageZoom.setPivotX(0.0F);
        mImageZoom.setPivotY(0.0F);
        set1 = new AnimatorSet();
        set1.setDuration(duration);
        mStartBounds = startBounds;
        mStartScale = startScale;

        float finalHeight = 1 / startScale * imageOrgHeight;
        float animatorTranY = (-1) * ((finalBounds.bottom / 2 - finalHeight / 2) - getStatusBarHeight(activity));
        if (centerCrop) {
            animatorTranY = finalBounds.top;
        }
        set1.play(ObjectAnimator.ofFloat(mImageZoom, View.X, new float[]{(float) startBounds.left, (float) finalBounds.left}))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.Y, new float[]{(float) startBounds.top, animatorTranY}))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.SCALE_X, new float[]{startScale, 1.0F}))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.SCALE_Y, new float[]{startScale, 1.0F}));


        set1.setInterpolator(new DecelerateInterpolator());
        set1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mImageZoom.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
                mImageZoom.setVisibility(View.GONE);
            }
        });
        set1.start();
        mCurrentAnimator = set1;
    }

    public void zoomImageFromThumbReverse() {

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        if (mImageZoom == null || mActivity == null) {
            return;
        }
        if (mImageZoom.getVisibility() == View.GONE) {
            return;
        }
        mImageZoom.setBackgroundColor(0);
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(mImageZoom, View.X, new float[]{(float) mStartBounds.left}))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.Y, new float[]{(float) mStartBounds.top}))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.SCALE_X, new float[]{mStartScale}))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.SCALE_Y, new float[]{mStartScale}));
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mDuration);
        set.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                mThumbView.setAlpha(1.0F);
                mImageZoom.setVisibility(View.GONE);
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }
                mCurrentAnimator = null;
            }

            public void onAnimationCancel(Animator animation) {
                mThumbView.setAlpha(1.0F);
                mImageZoom.setVisibility(View.GONE);
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;
    }
}

