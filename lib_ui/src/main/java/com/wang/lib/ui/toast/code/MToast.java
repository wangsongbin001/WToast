package com.wang.lib.ui.toast.code;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.lib.ui.R;
import com.wang.lib.ui.toast.WApp;

public class MToast implements IToast {

    String content;
    int gravity = -1;
    View contentView;

    public MToast(Builder builder){
        this.content = builder.content;
        this.contentView = builder.contentView;
        this.gravity = builder.gravity;
    }

    @Override
    public void show(WApp wApp) {
        if (wApp == null || wApp.getContext() == null) {
            Log.e("WToast", "MToast context can not be null");
            return;
        }
        if (contentView == null && TextUtils.isEmpty(content)) {
            Log.e("WToast", "MToast content can not be null ");
            return;
        }
        View view = contentView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(wApp.getContext());
            view = inflater.inflate(R.layout.libui_toast, null);
            TextView textView = view.findViewById(R.id.libui_id_title);
            textView.setText(content);
        }
        Toast toast = new Toast(wApp.getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(gravity != -1 ? gravity : Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }

    @Override
    public void dismiss() {
    }
}
