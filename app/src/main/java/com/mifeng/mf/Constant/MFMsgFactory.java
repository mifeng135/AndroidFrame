package com.mifeng.mf.Constant;

import java.util.HashMap;
import java.util.Map;

import com.mifeng.mf.Common.MFNetWorkLogic;


public class MFMsgFactory {

    private Map<String, String> mMsgSendFactory = new HashMap<>();
    private Map<String, String> mMsgRecvFactory = new HashMap<>();


    private static volatile MFMsgFactory mInstance;

    public static MFMsgFactory getInstance() {
        if (mInstance == null) {
            synchronized (MFNetWorkLogic.class) {
                if (mInstance == null) {
                    mInstance = new MFMsgFactory();
                }
            }
        }
        return mInstance;
    }

    MFMsgFactory() {
        mMsgSendFactory.put(MFMsgDefine.MSG_SEND_CATALOG_HOME_TOP_STORE_INFO, MFMsgUrl.CATALOG_HOME_TOP_STORE_DATA);
        mMsgSendFactory.put(MFMsgDefine.MSG_SEND_CATALOG_HOME_INIT_PRODUCT_TAG, MFMsgUrl.CATALOG_HOME_INIT_PRODUCT_TAG);
        mMsgSendFactory.put(MFMsgDefine.MSG_SEND_CATALOG_HOME_PRODUCT_BY_TAG, MFMsgUrl.CATALOG_HOME_PRODUCT_BY_TAG);
        mMsgSendFactory.put(MFMsgDefine.MSG_SEND_CATALOG_CATEGORY_GET_PRODUCT_BY_CATEGORY, MFMsgUrl.CATALOG_CATEGOYR_GET_PRODUCT_BY_CATEGORY);
        mMsgSendFactory.put(MFMsgDefine.MSG_SEND_APP_PROGRAM_MOBILE_LOGIN, MFMsgUrl.APP_PROGRAM_MOBILE_LOGIN);


        mMsgRecvFactory.put(MFMsgUrl.CATALOG_HOME_TOP_STORE_DATA, MFMsgDefine.MSG_RECV_CATALOG_HOME_TOP_STORE_INFO);
        mMsgRecvFactory.put(MFMsgUrl.CATALOG_HOME_INIT_PRODUCT_TAG, MFMsgDefine.MSG_RECV_CATALOG_HOME_INIT_PRODUCT_TAG);
        mMsgRecvFactory.put(MFMsgUrl.CATALOG_HOME_PRODUCT_BY_TAG, MFMsgDefine.MSG_RECV_CATALOG_HOME_PRODUCT_BY_TAG);
        mMsgRecvFactory.put(MFMsgUrl.CATALOG_CATEGOYR_GET_PRODUCT_BY_CATEGORY, MFMsgDefine.MSG_RECV_CATALOG_CATEGORY_GET_PRODUCT_BY_CATEGORY);
        mMsgRecvFactory.put(MFMsgUrl.APP_PROGRAM_MOBILE_LOGIN, MFMsgDefine.MSG_RECV_APP_PROGRAM_MOBILE_LOGIN);

    }

    public Map getSendMsgFactory() {
        return mMsgSendFactory;
    }

    public Map getRecvMsgFactory() {
        return mMsgRecvFactory;
    }
}
