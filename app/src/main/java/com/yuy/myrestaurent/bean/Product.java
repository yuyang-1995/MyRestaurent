package com.yuy.myrestaurent.bean;

import java.io.Serializable;

/**
 * Author: yuyang
 * Date:2019/10/17 21:58
 * Description:
 * Version:
 */
public class Product  implements Serializable{

    /**
     * id : 1
     * name : 烤鸭
     * label : 烤鸭烤鸭烤鸭烤鸭烤鸭烤鸭烤鸭烤鸭烤鸭
     * description : 烤鸭烤鸭烤鸭烤鸭烤鸭烤鸭烤鸭烤鸭烤鸭
     * icon : resources/image/1.png
     * price : 88
     * restaurant : {"id":1,"icon":"/resources/image/08.jpg","name":"小慕餐厅","description":"美女八折，程序员免费","price":888.8}
     */
    protected int id;
    protected String name;
    protected String label;
    protected String description;
    protected String icon;
    protected float price;
    protected Restaurant restaurant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
