package com.mifeng.mf.ControllerLogic;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mifeng.mf.Constant.MFBeanItemTypeDefine;
import com.mifeng.mf.Common.MFEvent;
import com.mifeng.mf.Common.MFNetWorkLogic;
import com.mifeng.mf.Common.MFPostEvent;
import com.mifeng.mf.Constant.MFMsgDefine;
import com.mifeng.mf.MsgBean.CategoryCatalogCategoryProductBean;
import com.mifeng.mf.MsgBean.HomeCatalogProductBean;
import com.mifeng.mf.MsgBean.HomeCatelogInitProductTagBean;
import com.mifeng.mf.MsgBean.HomeStoreInfoBean;
import com.mifeng.mf.TabViewController.HomeViewController;

/**
 * Created by Administrator on 2019/9/3.
 */

public class HomeViewControllerLogic implements BaseViewControllerLogic{

    private boolean activation = true;

    HomeStoreInfoBean storeInfoBean = null;
    List<HomeCatalogProductBean> homeCatalogProductBeanList = new ArrayList<>();
    List<CategoryCatalogCategoryProductBean.Product> homeCatelogCategoryProductBeanList = new ArrayList<>();

    List<MultiItemEntity> homeAllDataList = new ArrayList<>();

    HomeViewController viewHander;
    public HomeViewControllerLogic(HomeViewController viewHander) {
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

    public void requestHomeStoreInfo(Map param) {
        MFNetWorkLogic.getInstance().requestData(MFMsgDefine.MSG_SEND_CATALOG_HOME_TOP_STORE_INFO, param, "get");
    }

    public void requestHomeInitProductTag(Map param) {
        MFNetWorkLogic.getInstance().requestData(MFMsgDefine.MSG_SEND_CATALOG_HOME_INIT_PRODUCT_TAG, param, "get");
    }

    public void requestCategoryProductByCategory(Map param) {
        MFNetWorkLogic.getInstance().requestData(MFMsgDefine.MSG_SEND_CATALOG_CATEGORY_GET_PRODUCT_BY_CATEGORY, param, "get");
    }


    public void requestHomeProductByTag(List<Map> param) {
        MFNetWorkLogic.getInstance().requestWithSpread(param);
    }

    public List<Map> formatHomeProductParam(HomeCatelogInitProductTagBean homeCatelogInitProductTagBean) {
        List<Map> spreadParam = new ArrayList<>();
        if (homeCatelogInitProductTagBean.getData() != null && homeCatelogInitProductTagBean.getData().size() > 0) {
            for (int i = 0; i < homeCatelogInitProductTagBean.getData().size(); i++) {
                Map sendOc = new HashMap();
                Map param = new HashMap();
                sendOc.put("sendCmd", MFMsgDefine.MSG_SEND_CATALOG_HOME_PRODUCT_BY_TAG);
                sendOc.put("sendType", "get");
                String type = homeCatelogInitProductTagBean.getData().get(i).get(0);
                param.put("type", type);
                sendOc.put("param", param);
                spreadParam.add(sendOc);
            }
        }
        return spreadParam;
    }


    public void refreshHomeUI(Map param) {
        homeAllDataList.clear();
        if (storeInfoBean != null) {
            homeAllDataList.add(storeInfoBean);
        }
        if (homeCatalogProductBeanList != null && homeCatalogProductBeanList.size() > 0) {
            for (int i = 0; i < homeCatalogProductBeanList.size(); i++) {
                HomeCatalogProductBean homeCatalogProductBean = (HomeCatalogProductBean) homeCatalogProductBeanList.get(i);
                if (homeCatalogProductBean.getData() != null && homeCatalogProductBean.getData().getProducts().size() > 0) {
                    List<HomeCatalogProductBean.Products> productsList = homeCatalogProductBean.getData().getProducts();
                    for (int index = 0; index < productsList.size(); index++) {
                        if (index == 0) {
                            productsList.get(index).setItemType(MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD_WITH_IMAGE);
                            productsList.get(index).setTitle_image(homeCatalogProductBean.getData().getImage());
                            homeAllDataList.add(productsList.get(index));
                        } else if (index == productsList.size() - 1) {
                            productsList.get(index).setItemType(MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD_BOTTOM);
                            homeAllDataList.add(productsList.get(index));
                        } else {
                            productsList.get(index).setItemType(MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD);
                            homeAllDataList.add(productsList.get(index));
                        }
                    }
                }
            }
        }

        if (homeCatelogCategoryProductBeanList.size() > 0) {
            for (int i = 0; i < homeCatelogCategoryProductBeanList.size(); i++) {
                homeAllDataList.add(homeCatelogCategoryProductBeanList.get(i));
            }
        }
        viewHander.refreshHomeUI(homeAllDataList,param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvMsg(MFPostEvent messageEvent) {
        String eventCmd = messageEvent.getEventCmd();
        if (eventCmd == MFMsgDefine.MSG_RECV_CATALOG_HOME_INIT_PRODUCT_TAG) {
            HomeCatelogInitProductTagBean homeCatelogInitProductTagBean = (HomeCatelogInitProductTagBean) messageEvent.getData();
            List<Map> spreadParam = formatHomeProductParam(homeCatelogInitProductTagBean);
            requestHomeProductByTag(spreadParam);
        } else if (eventCmd == MFMsgDefine.MSG_RECV_CATALOG_CATEGORY_GET_PRODUCT_BY_CATEGORY && activation) {
            CategoryCatalogCategoryProductBean categoryCatalogCategoryProductBean = (CategoryCatalogCategoryProductBean) messageEvent.getData();
            List<CategoryCatalogCategoryProductBean.Product> products = categoryCatalogCategoryProductBean.getData().getProduct();
            Map map = (Map) messageEvent.getExtraData();
            int count = categoryCatalogCategoryProductBean.getData().getCount();
            int page = (int) map.get("p");
            if (page == 1) {
                homeCatelogCategoryProductBeanList.clear();
            }
            if (products != null && products.size() > 0) {
                for (int i = 0; i < products.size(); i++) {
                    homeCatelogCategoryProductBeanList.add(products.get(i));
                }
            }
            for (int i = 0; i < homeCatelogCategoryProductBeanList.size(); i++) {
                CategoryCatalogCategoryProductBean.Product product = homeCatelogCategoryProductBeanList.get(i);
                if (i == 0) {
                    product.setTitle_image(categoryCatalogCategoryProductBean.getData().getImage());
                    product.setItemType(MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD_WITH_IMAGE);
                } else if (i == homeCatelogCategoryProductBeanList.size() - 1) {
                    product.setItemType(MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD_BOTTOM);
                } else {
                    product.setItemType(MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD);
                }
            }
            map.put("nextRefresh", true);
            if (homeCatelogCategoryProductBeanList.size() >= count) {
                map.put("nextRefresh", false);
            }
            refreshHomeUI(map);
        } else if (eventCmd == MFMsgDefine.MSG_RECV_CATALOG_HOME_PRODUCT_BY_TAG) {
            homeCatalogProductBeanList = (List<HomeCatalogProductBean>) messageEvent.getData();
            refreshHomeUI(new HashMap());
        }
    }
}
