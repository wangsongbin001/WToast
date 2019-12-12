package com.wang.wtoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wang.lib.ui.toast.WApp;
import com.wang.lib.ui.toast.WToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MApp.getInstance().addToTask(this);
    }

    public void onClick(View view){
        WToast.show("hello world");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MApp.getInstance().exitFromTask(this);
    }
}
