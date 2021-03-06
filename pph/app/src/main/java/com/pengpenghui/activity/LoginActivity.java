package com.pengpenghui.activity;
/*
测试下行不行
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.LogController;
import com.pengpenghui.ui.R;


/**
 * Created by 肖文浩    on 2015/6/17.
 */
public class LoginActivity extends Activity {
	private Button bn_login;
	private TextView bn_register;
    private EditText et_account;
    private EditText et_password;
    private LogController logController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        findView();
        setListener();
        initData();
    }
    private void findView(){
        bn_login=(Button) findViewById(R.id.login);
        bn_register=(TextView) findViewById(R.id.resigter);
        et_account = (EditText)findViewById(R.id.et_account);
        et_password = (EditText)findViewById(R.id.et_password);
    }
    private void setListener(){
        bn_login.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                if(account.isEmpty()||password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    logController.tryLog(account, password, new ContextCallback() {
                        @Override
                        public void response(int state, Object object) {
                            if (state == SUCC){
                                logController.AutoLog();
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this,""+object,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

        });
        bn_register.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }

        });
    }
    private void initData(){
        logController = LogController.get();
        setRecord();
    }
    private void setRecord(){
        String []arr = logController.getAccountRecord();
        et_account.setText(arr[0]);
        et_password.setText(arr[1]);
    }
   
}
