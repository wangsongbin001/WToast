package com.wang.wtoast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.wang.lib.ui.toast.WApp;
import com.wang.lib.ui.toast.WToast;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class MApp extends Application implements WApp {

    private static MApp mApp;

    /**
     * activity 队列
     */
    private List<SoftReference<Activity>> mTask;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mApp = this;
        mTask = new ArrayList<>();
        WToast.init(this);
    }

    public static MApp getInstance(){
        return mApp;
    }

    @Override
    public Context getContext() {
        return this;
    }

    /** 维系队列 start **/
    public void addToTask(Activity activity) {
        if (mTask != null) {
            mTask.add(new SoftReference<>(activity));
        }
    }

    public void exitFromTask(Activity activity) {
        if (activity == null) {
            return;
        }
        if (mTask == null || mTask.isEmpty()) {
            return;
        }
        for (int i = mTask.size() - 1; i >= 0; i--) {
            SoftReference<Activity> item = mTask.get(i);
            if (item == null) {
                continue;
            }
            Activity itemActivity = item.get();
            if (itemActivity != null && itemActivity == activity) {
                mTask.remove(i);
            }
        }
    }

    @Override
    public Activity getTaskTop() {
        if (mTask == null || mTask.isEmpty()) {
            return null;
        }
        for (int i = mTask.size() - 1; i >= 0; i--) {
            SoftReference<Activity> last = mTask.get(i);
            if (last == null) {
                continue;
            }
            Activity activity = last.get();
            if (activity != null && activity.isFinishing()) {
                continue;
            }
            return activity;
        }
        return null;
    }
/** 维系队列 end **/
}
