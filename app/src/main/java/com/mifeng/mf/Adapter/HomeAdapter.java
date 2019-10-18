package com.mifeng.mf.Adapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import com.mifeng.mf.Constant.MFBeanItemTypeDefine;
import com.mifeng.mf.Common.MFUtils;
import com.mifeng.mf.MsgBean.CategoryCatalogCategoryProductBean;
import com.mifeng.mf.MsgBean.HomeCatalogProductBean;
import com.mifeng.mf.R;

public class HomeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    String controllerId;
    Activity activity;

    public HomeAdapter(List data, String controllerId,Activity activity) {
        super(data);
        this.controllerId = controllerId;
        this.activity = activity;
        addItemType(MFBeanItemTypeDefine.HOME_TOP_STORE_INFO, R.layout.home_top_store_info_layout);
        addItemType(MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD, R.layout.home_recommond_good_layout);
        addItemType(MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD_WITH_IMAGE, R.layout.home_recommond_good_with_image_layout);
        addItemType(MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD_BOTTOM, R.layout.home_recommond_good_bottom_layout);


        addItemType(MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD, R.layout.home_recommond_good_layout);
        addItemType(MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD_WITH_IMAGE, R.layout.home_recommond_good_with_image_layout);
        addItemType(MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD_BOTTOM, R.layout.home_recommond_good_bottom_layout);


    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD:
                assignHomeRecommondGood(helper, item);
                break;
            case MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD_WITH_IMAGE:
                assignHomeRecommondGoodWithTitleImage(helper, item);
                break;
            case MFBeanItemTypeDefine.HOME_RECOMMOND_GOOD_BOTTOM:
                assignHomeRecommondGood(helper, item);
                break;

            case MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD:
                assignHomeCatalogCategoryGood(helper, item);
                break;
            case MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD_WITH_IMAGE:
                assignHomeCatalogCategoryWithTitleImage(helper, item);
                break;
            case MFBeanItemTypeDefine.HOME_CATALOG_CATEGORY_GOOD_BOTTOM:
                assignHomeCatalogCategoryGood(helper, item);
                break;
            case MFBeanItemTypeDefine.HOME_TOP_STORE_INFO:
                break;
        }
    }


    public void assignHomeRecommondGood(BaseViewHolder helper, MultiItemEntity item) {
        HomeCatalogProductBean.Products products = (HomeCatalogProductBean.Products) item;
        ImageView reCommondImage = helper.getView(R.id.home_recommond_image);
        helper.addOnClickListener(R.id.home_recommond_image);
        MFUtils.getInstance().loadImage(products.getImage(), reCommondImage);

        helper.setText(R.id.home_recommond_product_name, products.getName());
        helper.setText(R.id.home_recommond_product_dis, products.getDescription());
        helper.setText(R.id.home_recommond_product_price, "¥" + products.getPlatform_price());
        helper.setText(R.id.home_recommond_good_sale_count, "已售" + products.getSale_num());
    }

    public void assignHomeRecommondGoodWithTitleImage(BaseViewHolder helper, MultiItemEntity item) {
        HomeCatalogProductBean.Products products = (HomeCatalogProductBean.Products) item;
        ImageView titleImage = helper.getView(R.id.home_recommond_good_title_image);
        MFUtils.getInstance().loadImage(products.getTitle_image(), titleImage);

        ImageView reCommondImage = helper.getView(R.id.home_recommond_image);
        helper.addOnClickListener(R.id.home_recommond_image);
        MFUtils.getInstance().loadImage(products.getImage(), reCommondImage);

        helper.setText(R.id.home_recommond_product_name, products.getName());
        helper.setText(R.id.home_recommond_product_dis, products.getDescription());
        helper.setText(R.id.home_recommond_product_price, "¥" + products.getPlatform_price());
        helper.setText(R.id.home_recommond_good_sale_count, "已售" + products.getSale_num());
    }



    public void assignHomeCatalogCategoryGood(BaseViewHolder helper, MultiItemEntity item) {
        CategoryCatalogCategoryProductBean.Product products = (CategoryCatalogCategoryProductBean.Product) item;
        ImageView reCommondImage = helper.getView(R.id.home_recommond_image);
        helper.addOnClickListener(R.id.home_recommond_image);
        MFUtils.getInstance().loadImage(products.getImage(), reCommondImage);

        helper.setText(R.id.home_recommond_product_name, products.getName());
        helper.setText(R.id.home_recommond_product_dis, products.getDescription());
        helper.setText(R.id.home_recommond_product_price, "¥" + products.getPlatform_price());
        helper.setText(R.id.home_recommond_good_sale_count, "已售" + products.getSale_num());
    }

    public void assignHomeCatalogCategoryWithTitleImage(BaseViewHolder helper, MultiItemEntity item) {
        CategoryCatalogCategoryProductBean.Product products = (CategoryCatalogCategoryProductBean.Product) item;
        ImageView titleImage = helper.getView(R.id.home_recommond_good_title_image);
        MFUtils.getInstance().loadImage(products.getTitle_image(), titleImage);

        ImageView reCommondImage = helper.getView(R.id.home_recommond_image);
        helper.addOnClickListener(R.id.home_recommond_image);
        MFUtils.getInstance().loadImage(products.getImage(), reCommondImage);

        helper.setText(R.id.home_recommond_product_name, products.getName());
        helper.setText(R.id.home_recommond_product_dis, products.getDescription());
        helper.setText(R.id.home_recommond_product_price, "¥" + products.getPlatform_price());
        helper.setText(R.id.home_recommond_good_sale_count, "已售" + products.getSale_num());
    }



    public void assignHomeTopStoreInfo(BaseViewHolder helper, MultiItemEntity item) {

    }
}
