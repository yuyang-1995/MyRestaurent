package com.yuy.myrestaurent.net;


import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Author: yuyang
 * Date:2019/10/17 10:04
 * Description:处理json 字符串回调, 返回正确时，resultCode 总为0
 * Version:
 */
public abstract class CommonCallback<T> extends StringCallback {

    //获取返回字符串的 类型，是单个对象还是集合等
    Type mType;

    //在构造函数中获取T 的 Type 即获取泛型的 类型
    //获取T 的类型
    public CommonCallback() {
//
        //第一步：先获取 原始类型， 及Class 类型
        Class<? extends CommonCallback> clzz = getClass(); //原始类型

        //第二步： 获取 Type
        Type genericSuperclass = clzz.getGenericSuperclass();

        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Miss Type Params");
        }

        //第三步： 当需描述的是泛型类时， java选择 ParameterizedType 接口作为Type 的实现
        // 将原始类型 强转为ParameterizedType
        Type[] types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();

        //
        mType = types[0];

//        Class<? extends CommonCallback> clzz = getClass();
//
//        Type genericSuperclass = clzz.getGenericSuperclass();
//        if (genericSuperclass instanceof Class) {
//            throw new RuntimeException("Miss Type Params");
//        }
//        //
//        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
//
//        mType=  parameterizedType.getActualTypeArguments()[0];

    }

    @Override
    public void onError(Call call, Exception e, int i) {
        //请求错误
        onError(e);
    }


    /**
     * 将返回的json 分为两部分， 1：resultCode 2:data  resultCode==1 表示成功 回调data
     * @param response
     * @param id
     */
    @Override
    public void onResponse(String response, int id) {

        try {

            JSONObject jsonObject = new JSONObject(response);

            int resoultCode = jsonObject.getInt("resultCode");  //获取json resultCode

            if (resoultCode == 1) {
                //成功
                String jsonData = jsonObject.getString("data");  //获取data  集合

                //回调data 部分即可
                Gson gson = new Gson();

                onSucess((T)gson.fromJson(jsonData, mType));

            }else {
                //返回错误 请求
                onError(new RuntimeException(jsonObject.getString("resultMessage")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onError(e);
        }

    }

    public abstract void onError(Exception e);

    //回调 参数化对象
    public abstract void onSucess(T response);


}
