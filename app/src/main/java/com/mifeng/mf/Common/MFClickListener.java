package com.mifeng.mf.Common;

import android.view.View;

import java.util.Calendar;

public abstract class MFClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onMFClick(v);
        }
    }

    public abstract void onMFClick(View v);
}
