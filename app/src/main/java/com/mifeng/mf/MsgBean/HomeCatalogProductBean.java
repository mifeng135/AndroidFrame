package com.mifeng.mf.MsgBean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2019/9/4.
 */

public class HomeCatalogProductBean {
    private int code;
    private String message;
    private Data data;

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


    public static class Data {

        private String image;
        private List<Products> products;
        private String count;
        private int pageSize;
        private String type;

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public void setProducts(List<Products> products) {
            this.products = products;
        }

        public List<Products> getProducts() {
            return products;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCount() {
            return count;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public static class Products implements MultiItemEntity {

        private String name;
        private String sku;
        private String _id;
        private String image;
        private String share_image;
        private int sale_num;
        private int month_sale_num;
        private int platform_price;
        private String thumbnails_image;
        private String attr_group_id;
        private String product_id;
        private String description;
        private List<String> category_id;
        private String address;
        private int is_add_store;
        private int itemType = 0;

        public String getTitle_image() {
            return title_image;
        }

        public void setTitle_image(String title_image) {
            this.title_image = title_image;
        }

        private String title_image = "";
        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int type) {
            itemType = type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getSku() {
            return sku;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String get_id() {
            return _id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public void setShare_image(String share_image) {
            this.share_image = share_image;
        }

        public String getShare_image() {
            return share_image;
        }

        public void setSale_num(int sale_num) {
            this.sale_num = sale_num;
        }

        public int getSale_num() {
            return sale_num;
        }

        public void setMonth_sale_num(int month_sale_num) {
            this.month_sale_num = month_sale_num;
        }

        public int getMonth_sale_num() {
            return month_sale_num;
        }

        public void setPlatform_price(int platform_price) {
            this.platform_price = platform_price;
        }

        public int getPlatform_price() {
            return platform_price;
        }

        public void setThumbnails_image(String thumbnails_image) {
            this.thumbnails_image = thumbnails_image;
        }

        public String getThumbnails_image() {
            return thumbnails_image;
        }

        public void setAttr_group_id(String attr_group_id) {
            this.attr_group_id = attr_group_id;
        }

        public String getAttr_group_id() {
            return attr_group_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setCategory_id(List<String> category_id) {
            this.category_id = category_id;
        }

        public List<String> getCategory_id() {
            return category_id;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setIs_add_store(int is_add_store) {
            this.is_add_store = is_add_store;
        }

        public int getIs_add_store() {
            return is_add_store;
        }


    }
}
