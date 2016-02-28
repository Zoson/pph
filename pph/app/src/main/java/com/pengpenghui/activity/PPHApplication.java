package com.pengpenghui.activity;

import android.app.Application;

import com.pengpenghui.domain.context.PPHContext;

/**
 * Created by Zoson on 16/1/12.
 */
public class PPHApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PPHContext.BaseContext = new PPHContext(getBaseContext());
    }
}
