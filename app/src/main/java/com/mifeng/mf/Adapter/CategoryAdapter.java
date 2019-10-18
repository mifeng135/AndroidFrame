package com.mifeng.mf.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.mifeng.mf.Common.MFUtils;
import com.mifeng.mf.MsgBean.CategoryCatalogCategoryProductBean;
import com.mifeng.mf.R;

/**
 * Created by Administrator on 2019/9/18.
 */

public class CategoryAdapter extends BaseQuickAdapter<CategoryCatalogCategoryProductBean.Product, BaseViewHolder> {

    Context context;
    String controllerId;

    public CategoryAdapter(Context context, int layoutResId, @Nullable List<CategoryCatalogCategoryProductBean.Product> data, String controllerId) {
        super(layoutResId, data);
        this.context = context;
        this.controllerId = controllerId;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CategoryCatalogCategoryProductBean.Product item) {
        ImageView categoryImage = helper.getView(R.id.category_good_image);
        MFUtils.getInstance().loadImage(item.getImage(), categoryImage);

        helper.setText(R.id.category_good_name, item.getName());
        helper.setText(R.id.category_good_dis, item.getDescription());
        helper.setText(R.id.category_good_price, "¥" + item.getPlatform_price());
        helper.setText(R.id.category_good_sale_count, "已售" + item.getSale_num());
    }
}
