package com.pengpenghui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.pengpenghui.domain.controller.LogController;

/**
 * Created by zoson on 6/26/15.
 */
public class Activity_welcome extends PPHActivity{

    private LinearLayout ll_welcome;
    private LogController logController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findView();
        init();
    }

    private void findView(){
        ll_welcome = (LinearLayout)findViewById(R.id.ll_welcome);
    }
    private void init(){
        logController = new LogController(this,this);
        ll_welcome.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (logController.isAutoLog()) {
                    logController.tryAutoLog();
                } else {
                    startLogActivity();
                }
            }
        }, 1000);
    }
    private void startLogActivity(){
        Intent intent = new Intent(Activity_welcome.this, Activity_Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.none, R.anim.opacity_1_0);
        finish();
    }
    private void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.none, R.anim.opacity_1_0);
        finish();
    }
    @Override
    public void requestSuccessfully(String msg, String data) {
        startMainActivity();
    }

    @Override
    public void requestUnSuccessfully(String msg, String data) {
        startLogActivity();
    }

    @Override
    public void requestError(String msg, String data) {
        startLogActivity();
    }
}
