package com.yuy.myrestaurent;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Author: yuyang
 * Date:2019/10/14 20:03
 * Description:
 * Version:
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //这里要处理登陆， 要保存服务端返回的sessionid 其实就是cookie
        //注意 PersistentCookieStore 会持久化 保存Cookie
        CookieJarImpl cookieJar = new CookieJarImpl(
                new PersistentCookieStore(getApplicationContext()));


        //OkHttpUtils okHttpClient 初始化设置
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)  //设置cookie
                .build();

        //okhttp 提供的cookieJar 方法
        OkHttpUtils.initClient(okHttpClient);

    }

}
