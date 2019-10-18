package com.mifeng.mf.MsgBean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2019/8/22.
 */

public class HomeStoreInfoBean implements MultiItemEntity{


    private int code;
    private String message;
    private Data data;
    private int itemType = 0;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int type) {
        itemType = type;
    }

    public static class Data {

        private StoreData storeData;
        private List<CategoryData> categoryData;
        private String noneCategoryCount;

        public void setStoreData(StoreData storeData) {
            this.storeData = storeData;
        }

        public StoreData getStoreData() {
            return storeData;
        }

        public void setCategoryData(List<CategoryData> categoryData) {
            this.categoryData = categoryData;
        }

        public List<CategoryData> getCategoryData() {
            return categoryData;
        }

        public void setNoneCategoryCount(String noneCategoryCount) {
            this.noneCategoryCount = noneCategoryCount;
        }

        public String getNoneCategoryCount() {
            return noneCategoryCount;
        }

    }

    public static class CategoryData {

        private int id;
        private int store_id;
        private String name;
        private String product_count;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setProduct_count(String product_count) {
            this.product_count = product_count;
        }

        public String getProduct_count() {
            return product_count;
        }

    }

    public static class StoreData {
        private int id;
        private String store_name;
        private int theme;
        private String store_logo;
        private String image;
        private String inviteCode;
        private int is_identification;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setTheme(int theme) {
            this.theme = theme;
        }

        public int getTheme() {
            return theme;
        }


        public void setStore_logo(String store_logo) {
            this.store_logo = store_logo;
        }

        public String getStore_logo() {
            return store_logo;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setIs_identification(int is_identification) {
            this.is_identification = is_identification;
        }

        public int getIs_identification() {
            return is_identification;
        }

    }
}


