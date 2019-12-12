package com.wang.lib.ui.toast.code;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wang.lib.ui.R;
import com.wang.lib.ui.toast.WApp;
import com.wang.lib.ui.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class MPopupWindowToast implements IToast {

    private final static int DELAY_DEFAULT = 2000;
    private final static int WHAT_RETRY = 1001;
    private final static int WHAT_DISMISS = 1002;

    private static MPopupWindowToast mPopupWindowToast;
    private List<Builder> mQueue = new ArrayList<>();

    ToastPopupWindow mWindow;
    WApp wApp;
    Builder builder;

    Handler uiHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_RETRY:
                    show(wApp);
                    break;
                case WHAT_DISMISS:
                    dismiss();
                    break;
            }
        }
    };

    public static MPopupWindowToast getInstance(){
        if(mPopupWindowToast == null){
            mPopupWindowToast = new MPopupWindowToast();
        }
        return mPopupWindowToast;
    }

    public MPopupWindowToast addToast(Builder builder){
        mQueue.add(builder);
        this.builder = builder;
        return this;
    }

    @Override
    public void show(final WApp wApp) {
        Log.i("qttTag", "MPopupWindowToast.show");
        if(wApp == null){
            return;
        }
        this.wApp = wApp;
        Activity currenActivity = wApp.getTaskTop();
        if(currenActivity != null){
            if(!currenActivity.isFinishing()){
                if(mWindow != null){
                    mWindow.dismiss();
                }
                mWindow = new ToastPopupWindow(currenActivity);
                mWindow.setContent(builder.getContent());
                mWindow.showAtLocation(currenActivity.findViewById(android.R.id.content), Gravity.CENTER, 0, UIUtil.dip2px(wApp.getContext(), 100));
                uiHandler.removeMessages(WHAT_DISMISS);
                uiHandler.sendEmptyMessageDelayed(WHAT_DISMISS, 2000);
            }else{
                uiHandler.sendEmptyMessageDelayed(WHAT_RETRY, 1000);
            }
        }
    }

    @Override
    public void dismiss() {
        if(mWindow != null){
            mWindow.dismiss();
        }
    }

    public static class ToastPopupWindow extends PopupWindow {

        TextView tvTitle;

        public ToastPopupWindow(Context context){
            super(context);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            setOutsideTouchable(false);
            setFocusable(false);
            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            View contentView = LayoutInflater.from(context).inflate(R.layout.libui_toast,
                    null, false);
            setContentView(contentView);
            tvTitle = contentView.findViewById(R.id.libui_id_title);
        }

        public void setContent(String content){
            if(tvTitle != null){
                tvTitle.setText(content);
            }
        }

    }
}
