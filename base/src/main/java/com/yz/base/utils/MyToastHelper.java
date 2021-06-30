package com.yz.base.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.yz.base.toast.SafeToastContext;

import java.lang.reflect.Field;

import es.dmoral.toasty.Toasty;

/**
 * @ClassName: MyToastHelper
 * @Description: java类作用描述
 * @Author: YZ-PC
 * @Date: 2020/7/3 10:51
 */
public class MyToastHelper {

    public static final int TYPE_ERROR=1;
    public static final int TYPE_INFO=2;
    public static final int TYPE_NORMAL=3;
    public static final int TYPE_SUCCESS=4;
    public static final int TYPE_WARNING=5;

    public static void info(String text){
        MyEventBus.post(new MyToasty(text, TYPE_INFO));
    }

    public static void warning(String text){
        MyEventBus.post(new MyToasty(text, TYPE_WARNING));
    }

    public static void error(String text){
        MyEventBus.post(new MyToasty(text, TYPE_ERROR));
    }

    public static void success(String text){
        MyEventBus.post(new MyToasty(text, TYPE_SUCCESS));
    }

    public static void normal(String text){
        MyEventBus.post(new MyToasty(text, TYPE_NORMAL));
    }

    public static void info(String text, int delay){
        MyEventBus.postDelayed(new MyToasty(text, TYPE_INFO),delay);
    }

    public static void warning(String text, int delay){
        MyEventBus.postDelayed(new MyToasty(text, TYPE_WARNING),delay);
    }

    public static void error(String text, int delay){
        MyEventBus.postDelayed(new MyToasty(text, TYPE_ERROR),delay);
    }

    public static void success(String text, int delay){
        MyEventBus.postDelayed(new MyToasty(text, TYPE_SUCCESS),delay);
    }

    public static void normal(String text, int delay){
        MyEventBus.postDelayed(new MyToasty(text, TYPE_NORMAL),delay);
    }

    public static class MyToasty {

        private String text;
        private int tpye;

        public MyToasty(String text, int tpye){
            this.text=text;
            this.tpye=tpye;
        }

        public void show(Activity activity){
            if (activity == null||activity.isFinishing()) {
                return;
            }
            Toast toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
            switch(tpye){
                case TYPE_ERROR:
                    toast=Toasty.error(activity, text, Toast.LENGTH_SHORT,true);
                    break;
                case TYPE_INFO:
                    toast= Toasty.info(activity, text, Toast.LENGTH_SHORT,true);
                    break;
                case TYPE_NORMAL:
                    toast=Toasty.normal(activity, text, Toast.LENGTH_SHORT);
                    break;
                case TYPE_SUCCESS:
                    toast=Toasty.success(activity, text, Toast.LENGTH_SHORT,true);
                    break;
                case TYPE_WARNING:
                    toast=Toasty.warning(activity, text, Toast.LENGTH_SHORT,true);
                    break;
            }
            setContextCompat(toast.getView(), new SafeToastContext(activity, toast));
            toast.show();
        }

        private void setContextCompat(@NonNull View view, @NonNull Context context) {
            if (Build.VERSION.SDK_INT == 25) {
                try {
                    Field field = View.class.getDeclaredField("mContext");
                    field.setAccessible(true);
                    field.set(view, context);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }
}
