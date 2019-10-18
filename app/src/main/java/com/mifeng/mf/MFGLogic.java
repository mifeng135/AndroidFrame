package com.mifeng.mf;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.mifeng.mf.Common.MFEvent;
import com.mifeng.mf.Common.MFPostEvent;
import com.mifeng.mf.Constant.MFMsgDefine;


/**
 * Created by Administrator on 2019/9/3.
 */

public class MFGLogic {

    private Context context;
    private Window window;
    PopupWindow loadingView;
    private int netRefCount = 0;

    MFGLogic(Context context, Window window) {
        this.context = context;
        this.window = window;
        MFEvent.getInstance().register(this);
    }

    private void showNetLoading(String cmd) {
        netRefCount = netRefCount + 1;
        if (netRefCount == 1) {
            if (loadingView == null) {
                loadingView = new PopupWindow();
                loadingView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                loadingView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                loadingView.setFocusable(true);
                View view = LayoutInflater.from(context).inflate(R.layout.common_loading_layout, null);
                loadingView.setContentView(view);
                loadingView.showAtLocation(window.getDecorView(), Gravity.CENTER, 0, 0);
            } else {
                loadingView.showAtLocation(window.getDecorView(), Gravity.CENTER, 0, 0);
            }
        }
    }

    private void hideNetLoading() {
        netRefCount = netRefCount - 1;
        if (loadingView != null && netRefCount <= 0) {
            loadingView.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvMsg(MFPostEvent messageEvent) {
        if (messageEvent.getEventCmd() == MFMsgDefine.G_SHOW_NET_LOADING) {
            showNetLoading(messageEvent.getEventCmd());
        } else if (messageEvent.getEventCmd() == MFMsgDefine.G_HIDE_NET_LOADING) {
            hideNetLoading();
        }
    }
}
