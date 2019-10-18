package com.mifeng.mf.ControllerLogic;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.mifeng.mf.Common.MFEvent;
import com.mifeng.mf.Common.MFPostEvent;
import com.mifeng.mf.UIViewController.FeedbackViewController;

/**
 * Created by Administrator on 2019/9/20.
 */

public class FeedbackViewControllerLogic implements BaseViewControllerLogic{

    private boolean activation = true;
    private FeedbackViewController viewHander;

    public FeedbackViewControllerLogic(FeedbackViewController viewHander) {
        this.register();
        this.viewHander = viewHander;
    }

    public void register() {
        MFEvent.getInstance().register(this);
    }

    public void unRegister() {
        MFEvent.getInstance().unregister(this);
        viewHander = null;
    }

    public void activation(boolean value) {
        activation = value;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvMsg(MFPostEvent messageEvent) {
        String eventCmd = messageEvent.getEventCmd();
    }
}
