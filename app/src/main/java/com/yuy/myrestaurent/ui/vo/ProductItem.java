package com.yuy.myrestaurent.ui.vo;


import com.yuy.myrestaurent.bean.Product;

/**
 * Author: yuyang
 * Date:2019/10/16 8:39
 * Description:  菜品数量存储 两种方式， 通常有两种方式： 一种是在Adapter 中创建map key为product ,
 *  第二种方式 可以在bean 中添加字段， 通过字段 控制
 * Version:  为了ui 而创建的类， 为记录 商品 在 商品列表中 用户欲购买的数量
 */
public class ProductItem extends Product {

    public int count;

    public ProductItem(Product product) {

       this.id = product.getId();
       this.name = product.getName();
       this.label = product.getLabel();
       this.description = product.getDescription();
       this.price = product.getPrice();
       this.icon = product.getIcon();
       this.restaurant = product.getRestaurant();
    }

}
