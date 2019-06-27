package com.yz.base.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by YZ on 2016/12/28.
 */

public class MyMainHandler {

    public static Handler post(Runnable runnable){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(runnable);
        return handler;
    }

    public static Handler postDelayed(Runnable runnable, int delayed){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,delayed);
        return handler;
    }

}
