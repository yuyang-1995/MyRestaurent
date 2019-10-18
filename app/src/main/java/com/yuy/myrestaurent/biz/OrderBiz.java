package com.yuy.myrestaurent.biz;

import com.yuy.myrestaurent.bean.Order;
import com.yuy.myrestaurent.bean.Product;
import com.yuy.myrestaurent.config.Config;
import com.yuy.myrestaurent.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;
import java.util.Map;

/**
 * Author: yuyang
 * Date:2019/10/17 23:46
 * Description: 订单业务类： 查询订单； 提交订单；
 * Version:
 */
public class OrderBiz {

    /**
     *查询已有的订单
     * @param currentPage
     * @param callback
     */
    public void listByPage(int currentPage, CommonCallback<List<Order>>  callback) {

        OkHttpUtils.get()
                .url(Config.baseUrl +"order_find")
                .addParams("currentPage", currentPage + "")
                .tag(this)
                .build()
                .execute(callback);
    }

    /**
     * 提交订单
     *
     * @param order
     */
    public void add(Order order, CommonCallback<String> callback) {
        //order_add
        //res_id
        //product_str, id_数量| id_数量|
        //count
        //price
        //获取 订单实例中的 map
        Map<Product, Integer> productIntegerMap = order.mProductIntegerMap;

        StringBuilder sb = new StringBuilder();

        /**
         * 遍历 map 的key 即 商品 组成 product_str
         */
        for (Product p : productIntegerMap.keySet()) {
            sb.append(p.getId() + "_" + productIntegerMap.get(p));
            sb.append("|");
        }

        sb.deleteCharAt(sb.length() - 1);

        //
        OkHttpUtils.post()
                .url(Config.baseUrl + "order_add")
                .addParams("res_id", order.getRestaurant().getId() + "")
                .addParams("product_str", sb.toString())
                .addParams("count", order.getCount() + "")
                .addParams("price", order.getPrice() + "")
                .tag(this)
                .build()
                .execute(callback);
    }


    public void onDestory(){
        OkHttpUtils.getInstance().cancelTag(this);

    }

}
