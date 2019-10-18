package com.yuy.myrestaurent.biz;

import com.yuy.myrestaurent.bean.User;
import com.yuy.myrestaurent.config.Config;
import com.yuy.myrestaurent.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Author: yuyang
 * Date:2019/10/17 10:03
 * Description: 用户实体业务层， 功能1： 用户注册； 功能2：
 * Version:
 */
public class UserBiz {
    /**
     * 登录成功
     * {
     * resultCode: 1,
     * data: {
     * id: 1043,
     * username: "yuy",
     * password: "123"
     * }
     * }
     * <p>
     * <p>
     * 错误登录
     * {
     * resultCode: 0,
     * resultMessage: "账号或者密码错误！"
     * }
     */


    public void login(String name, String password, CommonCallback<User> callback) {

        OkHttpUtils
                .post()
                .url(Config.baseUrl + "user_login")
                .addParams("username",name)
                .addParams("password", password)
                .tag(this)
                .build()
                .execute(callback);

    }


    public void register(String name, String password, CommonCallback<User> callback) {

        OkHttpUtils
                .post()
                .url(Config.baseUrl + "user_register")
                .addParams("username",name)
                .addParams("password", password)
                .tag(this)
                .build()
                .execute(callback);

    }


    public void onDestory(){

        OkHttpUtils.getInstance().cancelTag(this);
    }


}
