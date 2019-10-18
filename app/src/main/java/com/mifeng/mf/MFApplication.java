package com.mifeng.mf;

import android.app.Application;

public class MFApplication extends Application {

    public static MFApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
