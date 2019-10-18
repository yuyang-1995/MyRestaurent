package com.yuy.myrestaurent.biz;

import com.yuy.myrestaurent.bean.Product;
import com.yuy.myrestaurent.config.Config;
import com.yuy.myrestaurent.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Author: yuyang
 * Date:2019/10/17 22:16
 * Description: 查询
 * Version:
 */
public class ProductBiz {



    public void listByPage(int currentPage, CommonCallback<List<Product>> callback) {

        OkHttpUtils.post()
                .url(Config.baseUrl + "product_find")
                .addParams("currentPage", currentPage + "")
                .tag(this)
                .build()
                .execute(callback);


    }

    public void onDestory() {
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
