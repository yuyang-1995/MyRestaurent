package com.yuy.myrestaurent.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuy.myrestaurent.R;


/**
 * Author: yuyang
 * Date:2019/9/24 10:38
 * Description: Toast 工具类 Toast分为两类： 一：带自定义布局，Toast格式由布局定义;二：普通Toast
 *  版本差异：低版本系统 普通Toast "串行"显示即一个接一个显示， 高版本系统可 "并行"显示 即一个界面可有多个Toast
 *  各厂商对Toast 有处理，可能会导致显示异常
 * Version:1.0
 */
public final class ToastUtils {

    //自定义toast对象
    private static Toast toast;

    /**
     * 设置Toast背景为自定义布局， 自定义布局分别包含一图片元素和一文字元素, 此方法内部调用重载方法
     *
     * @param context  上下文
     * @param layoutId 布局id
     * @param ivId     图片id
     * @param str      文字字符串
     */
    public static void toastByView(Context context, int layoutId, int ivId, String str) {
        toastByView(context, layoutId, ivId, str, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }


    /**
     * @param context  上下文
     * @param layoutId 布局id
     * @param ivId     图片id
     * @param str      文本
     * @param duration 延时
     * @param gravity  重心
     */
    public static void toastByView(Context context, int layoutId, int ivId, String str, int duration, int gravity) {
       toastByView(context,layoutId,ivId,str,duration,gravity,0,0);
    }

    /**
     * @param context  上下文
     * @param layoutId 布局id
     * @param ivId     图片id
     * @param str      文本
     * @param duration 延时
     * @param gravity  重心
     * @param xOffSet  x偏移量 x<0 显示偏左
     * @param yOffset  y偏移量 y<0 显示偏上
     */
    public static void toastByView(Context context, int layoutId, int ivId, String str, int duration, int gravity, int xOffSet, int yOffset) {
        if (null == toast) {
            toast = new Toast(context);
        }

        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layoutId, null);
        TextView textView = view.findViewById(R.id.t_text);
        textView.setText(str);
        ImageView imageView = view.findViewById(R.id.iv);
        imageView.setImageResource(ivId);

        toast.setView(view);
        toast.setDuration(duration);
        toast.setGravity(gravity, xOffSet, yOffset);
        toast.show();
    }



    /**
     * 此方法内部调用 重载方法
     * @param context 上下文
     * @param message 信息
     */
    public static void toastByNormal(Context context, String message) {
        toastByNormal(context, message, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }


    /**
     * 此方法内部调用 重载方法
     * @param context 上下文
     * @param message  信息
     * @param duration  延时
     * @param gravity  重心
     */
    public static void toastByNormal(Context context, String message, int duration, int gravity) {
        toastByNormal(context, message, duration, gravity, 0, 0);
    }


    /**
     *普通Toast
     * @param context  上下文
     * @param message  信息
     * @param duration  延时
     * @param gravity  重心
     * @param xOffset  x偏移量
     * @param yOffset  y偏移量
     */
    public static void toastByNormal(Context context, String message, int duration, int gravity, int xOffset, int yOffset) {
        if (toast != null) {
            toast = null;
        }
        toast = Toast.makeText(context,message,duration);
        toast.setGravity(gravity,xOffset,yOffset);
        toast.show();
    }


    /**
     * 取消显示Toast
     */
    public static void cancelToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

}
