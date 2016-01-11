package com.pengpenghui.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pengpenghui.domain.controller.LogController;
import com.pengpenghui.pph_interface.ViewInterface;

/**
 * Created by 文浩 on 2015/6/17.
 */

public class Activity_Register extends Activity implements ViewInterface {
	
	private Button bn_register_s;
    private EditText et_account;
    private EditText et_password;
    private EditText et_password_repeat;
    private EditText et_nickName;
    private LogController logController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        logController = new LogController(getApplicationContext(),this);
        findView();
        setListener();
    }

    public void setListener(){
        bn_register_s.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                String password_repeat = et_password_repeat.getText().toString();
                String nickName = et_nickName.getText().toString();
                if (account.isEmpty()||password.isEmpty()||password_repeat.isEmpty()||nickName.isEmpty()){
                    Toast.makeText(Activity_Register.this,"请填写完整的信息",Toast.LENGTH_SHORT).show();
                }else{
                    if (password.equals(password_repeat)){
                        logController.tryReg(account,password,nickName);
                    }else{
                        Toast.makeText(Activity_Register.this,"密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void findView(){
        bn_register_s=(Button)findViewById(R.id.bt_regi);
        et_account = (EditText)findViewById(R.id.et_person_id);
        et_password = (EditText)findViewById(R.id.et_preson_ps);
        et_password_repeat = (EditText)findViewById(R.id.et_preson_rps);
        et_nickName = (EditText)findViewById(R.id.et_person_name);
    }
    @Override
    public void requestSuccessfully(String msg, String data) {
        Toast.makeText(this, "注册成功" + msg, Toast.LENGTH_SHORT).show();
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        LinearLayout layout =(LinearLayout)inflaterDl.inflate(R.layout.dialog_instru_layout, null);
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        Button btnOK = (Button) layout.findViewById(R.id.instu_sure);
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setClass(Activity_Register.this, Activity_Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void requestUnSuccessfully(String msg, String data) {
        Toast.makeText(this,"注册失败"+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestError(String msg, String data) {
        Toast.makeText(this,"网络错误"+msg, Toast.LENGTH_SHORT).show();
    }
}