package com.pengpenghui.ui;
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

import com.pengpenghui.domain.controller.LogController;
import com.pengpenghui.pph_interface.ViewInterface;

/**
 * Created by 肖文浩    on 2015/6/17.
 */
public class Activity_Login extends Activity implements ViewInterface {
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
                    Toast.makeText(Activity_Login.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    logController.tryLog(account,password);
                }
            }

        });
        bn_register.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_Login.this, Activity_Register.class);
                startActivity(intent);
            }

        });
    }
    private void initData(){
        logController = new LogController(getApplicationContext(),this);
        setRecord();
    }
    private void setRecord(){
        String []arr = logController.getAccountRecord();
        et_account.setText(arr[0]);
        et_password.setText(arr[1]);
    }
    @Override
    public void requestSuccessfully(String msg, String data) {
        Toast.makeText(this,"登录成功"+data,Toast.LENGTH_LONG).show();
        logController.AutoLog();
        Intent intent = new Intent();
        intent.setClass(Activity_Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void requestUnSuccessfully(String msg, String data) {
        Toast.makeText(this,"登录失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void requestError(String msg, String data) {
        Toast.makeText(this,"请求错误",Toast.LENGTH_LONG).show();
    }
   
}
