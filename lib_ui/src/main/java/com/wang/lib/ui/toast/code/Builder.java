package com.wang.lib.ui.toast.code;

import android.view.View;

public class Builder {
    String content;
    int duration  = -1;
    int gravity = -1;
    View contentView;

    public String getContent() {
        return content;
    }

    public Builder setContent(String content) {
        this.content = content;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public Builder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public int getGravity() {
        return gravity;
    }

    public Builder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public View getContentView() {
        return contentView;
    }

    public Builder setContentView(View contentView) {
        this.contentView = contentView;
        return this;
    }

    public IToast create(boolean isNotificationEnabled){
        //todo 有通知权限使用Toast，没通知权限使用PopupWindow
        if(isNotificationEnabled) {
            return new MToast(this);
        }else{
            return MPopupWindowToast.getInstance().addToast(this);
        }
    }
}
