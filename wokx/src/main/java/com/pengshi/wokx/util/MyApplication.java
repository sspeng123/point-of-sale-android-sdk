package com.pengshi.wokx.util;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * 程序的完美退出类 MyApplication.getInstance().addActivity(this); MyApplication.getInstance().exit();
 * @author shipeng
 * 1/11/24
 */
public class MyApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;

    private MyApplication() {
    }

    // 单例模式中获取唯一的MyApplication实例
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}

