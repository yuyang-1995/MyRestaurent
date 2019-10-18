package com.yuy.myrestaurent.bean;

import java.io.Serializable;

/**
 * Author: yuyang
 * Date:2019/10/17 22:00
 * Description:
 * Version:
 */
public class Restaurant  implements Serializable{

    /**
     * id : 1
     * icon : /resources/image/08.jpg
     * name : 小慕餐厅
     * description : 美女八折，程序员免费
     * price : 888.8
     */
    private int id;
    private String icon;
    private String name;
    private String description;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
