package com.mifeng.mf.Constant;

import java.util.HashMap;
import java.util.Map;

import com.mifeng.mf.MsgBean.CategoryCatalogCategoryProductBean;
import com.mifeng.mf.MsgBean.HomeCatalogProductBean;
import com.mifeng.mf.MsgBean.HomeCatelogInitProductTagBean;
import com.mifeng.mf.MsgBean.HomeStoreInfoBean;

/**
 * Created by Administrator on 2019/9/5.
 */

public class MFBeanFactory {

    private Map<String,Class> mBeanFactory = new HashMap<>();

    private static volatile MFBeanFactory mInstance;

    public static MFBeanFactory getInstance() {
        if (mInstance == null) {
            synchronized (MFBeanFactory.class) {
                if (mInstance == null) {
                    mInstance = new MFBeanFactory();
                }
            }
        }
        return mInstance;
    }

    MFBeanFactory() {
        mBeanFactory.put(MFMsgDefine.MSG_RECV_CATALOG_HOME_TOP_STORE_INFO, HomeStoreInfoBean.class);
        mBeanFactory.put(MFMsgDefine.MSG_RECV_CATALOG_HOME_INIT_PRODUCT_TAG, HomeCatelogInitProductTagBean.class);
        mBeanFactory.put(MFMsgDefine.MSG_RECV_CATALOG_HOME_PRODUCT_BY_TAG, HomeCatalogProductBean.class);
        mBeanFactory.put(MFMsgDefine.MSG_RECV_CATALOG_CATEGORY_GET_PRODUCT_BY_CATEGORY, CategoryCatalogCategoryProductBean.class);
    }

    public Class getBeanByCmd(String cmd) {
        return mBeanFactory.get(cmd);
    }

}
