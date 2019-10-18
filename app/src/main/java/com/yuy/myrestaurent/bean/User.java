package com.yuy.myrestaurent.bean;

import java.io.Serializable;

/**
 * Author: yuyang
 * Date:2019/10/17 9:12
 * Description:
 * Version:
 */
public class User  {
    /**
     * resultCode : 1
     * data : {"id":1043,"username":"yuy","password":"123"}
     */

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
     *
     *
     * 错误登录
     * {
     * resultCode: 0,
     * resultMessage: "账号或者密码错误！"
     * }
     */

    private int resultCode;
    private DataBean data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1043
         * username : yuy
         * password : 123
         */
        private int id;
        private String username;
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }








}
