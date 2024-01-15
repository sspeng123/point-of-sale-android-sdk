package com.pengshi.wokx.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.pengshi.wokx.util.MyApplication;

/**
 * Created by karlp on 6/4/2017.
 */

public abstract class  BaseActivity extends Activity {
    public Window _window;
    public Resources res;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBase();
        MyApplication.getInstance().addActivity(this);
        initContentView(savedInstanceState);
        findViews();
        initViews();
    }

    private void setBase() {
        _window = getWindow();
        res = this.getResources();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        _window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = _window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        _window.setAttributes(params);
    }

    // 初始化UI，setContentView等
    protected abstract void initContentView(Bundle savedInstanceState);
    protected abstract void findViews();
    protected abstract void initViews();

}
