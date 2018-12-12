package com.dikai.chenghunjiclient.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/11/2.
 */

public class ActivityManager {

    private static ActivityManager activityManager;
    public static List<Activity> activities = new ArrayList<Activity>();

    public static ActivityManager getInstance() {
        if (activityManager == null) {
            synchronized (ActivityManager.class) {
                activityManager = new ActivityManager();
            }
        }
        return activityManager;
    }

    public  void addActivity(Activity activity) {

        activities.add(activity);
    }

    public  void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public  void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
