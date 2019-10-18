package com.mifeng.mf.MFNavigation.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


import com.mifeng.mf.MFNavigation.Utils.UiUtils;

import static android.view.View.ALPHA;
import static android.view.View.TRANSLATION_X;

class BaseAnimator {

    private static final int DURATION = 300;
    private static final TimeInterpolator DECELERATE = new DecelerateInterpolator();
    private static final TimeInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();

    private float translationX;

    BaseAnimator(Context context) {
        translationX = UiUtils.getWindowWidth(context);
    }

    @NonNull
    AnimatorSet getDefaultPushAnimation(ViewGroup view) {
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(DECELERATE);
        set.setDuration(DURATION);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, TRANSLATION_X, this.translationX, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, ALPHA, 0, 1);
        set.playTogether(translationY, alpha);
        return set;
    }

    @NonNull
    AnimatorSet getDefaultPopAnimation(ViewGroup view) {
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(ACCELERATE_DECELERATE);
        set.setDuration(DURATION);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, TRANSLATION_X, 0, this.translationX);
        set.playTogether(translationY);
        return set;
    }


    @NonNull
    AnimatorSet getPreViewAnimation(ViewGroup view) {
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(ACCELERATE_DECELERATE);
        set.setDuration(DURATION);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, TRANSLATION_X, - 200, 0);
        set.playTogether(translationY);
        return set;
    }
}
