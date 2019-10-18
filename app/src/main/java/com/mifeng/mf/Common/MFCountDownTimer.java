package com.mifeng.mf.Common;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.lang.ref.WeakReference;


/**
 * Created by Administrator on 2019/9/20.
 */

public class MFCountDownTimer {
    private String color;
    WeakReference<TextView> tvCodeWr;
    private CountDownTimer timer;
    Drawable finishDrawable;
    Drawable startDrawable;
    int tick;
    public MFCountDownTimer(TextView tvCode, String color, Drawable finishDrawable,Drawable startDrawable,int tick) {
        super();
        this.tvCodeWr = new WeakReference(tvCode);
        this.color = color;
        this.finishDrawable = finishDrawable;
        this.startDrawable = startDrawable;
        this.tick = tick;
    }

    public void start() {
        timer = new CountDownTimer(tick * 1000 - 1, 1000) {
            @Override
            public void onFinish() {
                if (tvCodeWr.get() != null) {
                    tvCodeWr.get().setText("重新获取");
                    tvCodeWr.get().setTextColor(Color.parseColor(color));
                    tvCodeWr.get().setClickable(true);
                    tvCodeWr.get().setEnabled(true);
                    tvCodeWr.get().setBackground(finishDrawable);
                }
                cancel();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                if (tvCodeWr.get() != null) {
                    tvCodeWr.get().setClickable(false);
                    tvCodeWr.get().setEnabled(false);
                    tvCodeWr.get().setText("重新发送" + "(" + millisUntilFinished / 1000 + ")");
                    tvCodeWr.get().setBackground(startDrawable);
                }
            }
        }.start();
    }
    public void cancle() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
