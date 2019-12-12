package com.wang.lib.ui.toast;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.wang.lib.ui.toast.code.Builder;
import com.wang.lib.ui.utils.UIUtil;

public class WToast {

    public static WApp wApp;

    public static void init(WApp wwApp){
        wApp = wwApp;
    }

    public static final String tag = "WToast";

    public static void show(final String msg){
        if(wApp == null){
            Log.e(tag, "WApp cannot be null");
            return;
        }
        if(wApp.getContext() == null){
            Log.e(tag, "wApp.getContext cannot be null");
            return;
        }
        final boolean isNotificationEnabled = UIUtil.isNotificationEnabled(wApp.getContext());
//        if(Looper.myLooper() == Looper.getMainLooper()) {
//            WToast.build().setContent(msg).create(isNotificationEnabled).show(wApp);
//        }else{
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    WToast.build().setContent(msg).create(isNotificationEnabled).show(wApp);
                }
            });
//        }
    }

    private static Builder build(){
        return new Builder();
    }

}
