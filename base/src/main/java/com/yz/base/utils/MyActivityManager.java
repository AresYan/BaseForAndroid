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
        return instance;
    }

    // 添加Activity
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    // 移除Activity
    public void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    // 清空Activity
    public void finishAllActivity(boolean iskillProcess) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
                if (i == activityStack.size() - 1 && iskillProcess) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            }
        }
        activityStack.clear();
    }
}