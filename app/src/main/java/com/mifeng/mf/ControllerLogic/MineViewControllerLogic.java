package com.mifeng.mf.ControllerLogic;

import com.mifeng.mf.Common.MFEvent;
import com.mifeng.mf.Common.MFPostEvent;
import com.mifeng.mf.TabViewController.MineViewController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2019/9/3.
 */

public class MineViewControllerLogic implements BaseViewControllerLogic{

    private boolean activation = true;
    private MineViewController viewHander;

    public MineViewControllerLogic(MineViewController viewHander) {
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
