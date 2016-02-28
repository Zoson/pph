package com.pengpenghui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.LogController;
import com.pengpenghui.ui.R;

/**
 * Created by zoson on 6/26/15.
 */
public class WelcomeActivity extends Activity{

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
        logController = new LogController();
        ll_welcome.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (logController.isAutoLog()) {
                    logController.tryAutoLog(new ContextCallback() {
                        @Override
                        public void response(int state, Object object) {
                            if (state == SUCC){
                                startMainActivity();
                            }else{
                                startLogActivity();
                            }
                        }
                    });
                } else {
                    startLogActivity();
                }
            }
        }, 1000);
    }
    private void startLogActivity(){
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
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
}
