package com.mifeng.mf.ControllerLogic;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mifeng.mf.Common.MFEvent;
import com.mifeng.mf.Common.MFNetWorkLogic;
import com.mifeng.mf.Common.MFPostEvent;
import com.mifeng.mf.Constant.MFMsgDefine;
import com.mifeng.mf.MsgBean.CategoryCatalogCategoryProductBean;
import com.mifeng.mf.TabViewController.CartViewController;
import com.mifeng.mf.TabViewController.CategoryViewController;

/**
 * Created by Administrator on 2019/9/3.
 */

public class CategoryViewControllerLogic implements BaseViewControllerLogic{

    private boolean activation = true;
    private CategoryViewController viewHander;

    List<CategoryCatalogCategoryProductBean.Product> categoryProductBeanList = new ArrayList<>();

    public CategoryViewControllerLogic(CategoryViewController viewHander) {
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
