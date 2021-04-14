package com.yz.base.utils;

import android.app.Activity;

import java.util.Stack;

public class MyActivityManager {

    private static Stack<Activity> activityStack;
    private static MyActivityManager instance;

    public MyActivityManager() {
    }

    public static MyActivityManager getInstance() {
        if (instance == null) {
            instance = new MyActivityManager();
        }
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        return instance;
    }

    // 添加Activity
    public void addActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.add(activity);
        }
    }

    // 移除Activity
    public void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    // 清空Activity
    public void finishAllActivity() {
        if(activityStack!=null){
            int size=activityStack.size();
            for (int i = 0; i < size; i++) {
                Activity activity=activityStack.get(i);
                if (activity!=null) {
                    activity.finish();
                }
            }
            activityStack.clear();
        }
    }
}
