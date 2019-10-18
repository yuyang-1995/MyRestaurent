package com.yuy.myrestaurent.bean;

import com.yuy.myrestaurent.utils.ToastUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: yuyang
 * Date:2019/10/17 21:49
 * Description: 订单实体类
 * Version:
 */
public class Order implements Serializable {

    //id: 1411,
    //user: {},
    //date: "Oct 16, 2019 11:55:38 PM",
    //productsStr: "2_1",
    //restaurant: {},
    //count: 1,
    //price: 12.3,
    //ps: []

    //某件商品的 个数
    public static class ProductVo implements Serializable {
        public Product product;
        public int count;
    }

    private int id;//
    private Date date;//
    public List<ProductVo> ps;// 某件商品 对应的数量
    private Restaurant restaurant;//
    private int count;//  商品总数量
    private float price;//

    public Map<Product, Integer> mProductIntegerMap = new HashMap<>();// 订单中某件商品的数量

    /**
     * 添加商品 及商品数量
     * @param product
     */
    public void addProduct(Product product) {
        Integer count = mProductIntegerMap.get(product);

        if (count == null || count <= 0) {
            mProductIntegerMap.put(product, 1);
        }else{
            mProductIntegerMap.put(product, count + 1);
        }

    }

    /**
     * 移除商品
     *
     * @return
     */
    public void removeProduct(Product product) {

        Integer count = mProductIntegerMap.get(product); //获取某商品的数量

        if (count <= 0 || count == null) {
            return;
        }else {

            mProductIntegerMap.put(product, count - 1);

        }
    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
