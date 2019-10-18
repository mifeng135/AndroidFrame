package com.mifeng.mf.MFNavigation.Animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import com.mifeng.mf.Common.MFAnimation;
import com.mifeng.mf.MFNavigation.Controller.ChildController;
import com.mifeng.mf.MFNavigation.Controller.ViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFViewAnimator.ViewAnimator;


@SuppressWarnings("ResourceType")
public class NavigationAnimator extends BaseAnimator {

    private Map<View, Animator> runningPushAnimations = new HashMap<>();

    public NavigationAnimator(Context context) {
        super(context);
    }

    public void push(ViewController viewController) {
        ViewAnimator viewAnimator = viewController.getPushViewAnimator();
        if (viewAnimator != null) {
            viewAnimator.start();
        }
    }

    public void pop(ViewController viewController,Runnable onAnimationEnd) {
        ViewAnimator viewAnimator = viewController.getPopViewAnimator();
        MFAnimation.getInstance().zoomImageFromThumbReverse();
        if (viewAnimator != null) {
            viewAnimator.onStop(()-> {
                onAnimationEnd.run();
            }).start();
        }
    }

    public void push(ViewGroup view, Runnable onAnimationEnd) {
        view.setAlpha(.0f);
        AnimatorSet set = getDefaultPushAnimation(view);
        set.addListener(new AnimatorListenerAdapter() {
            private boolean isCancelled;

            @Override
            public void onAnimationCancel(Animator animation) {
                isCancelled = true;
                runningPushAnimations.remove(view);
                onAnimationEnd.run();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isCancelled) {
                    runningPushAnimations.remove(view);
                    onAnimationEnd.run();
                }
            }
        });
        runningPushAnimations.put(view, set);
        set.start();
    }

    public void pop(ViewGroup view, Runnable onAnimationEnd) {
        if (runningPushAnimations.containsKey(view)) {
            runningPushAnimations.get(view).cancel();
            onAnimationEnd.run();
            return;
        }
        AnimatorSet set = getDefaultPopAnimation(view);
        set.addListener(new AnimatorListenerAdapter() {
            private boolean cancelled;

            @Override
            public void onAnimationCancel(Animator animation) {
                this.cancelled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!cancelled) onAnimationEnd.run();
            }
        });
        set.start();


        ArrayDeque<ChildController> childControllers = Navigator.getInstance().getChildRegistry().getChildren();
        Object [] objects = childControllers.toArray();
        ViewController preChild = (ViewController)objects[1];
        if (preChild != null) {
            AnimatorSet test = getPreViewAnimation(preChild.getView());
            test.start();
        }
    }

    public void cancelPushAnimations() {
        for (View view : runningPushAnimations.keySet()) {
            runningPushAnimations.get(view).cancel();
            runningPushAnimations.remove(view);
        }
    }
}
