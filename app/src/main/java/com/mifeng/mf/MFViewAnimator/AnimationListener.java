package com.mifeng.mf.MFViewAnimator;

import android.view.View;

public class AnimationListener {

    private AnimationListener(){}

    public interface Start{
        void onStart();
    }

    public interface Stop{
        void onStop();
    }

    public interface Update<V extends View>{
        void update(V view, float value);
    }
    public interface Cancel{
        void onCancel();
    }
}
