package com.pengpenghui.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pengpenghui.domain.controller.GiftManager;
import com.pengpenghui.domain.controller.LogController;
import com.pengpenghui.pph_interface.ViewInterface;


/**
 * Created by 文浩 on 2015/12/16.
 */
public class Activity_weixincheck extends Activity {
    //确认
    private Button bn_sure_weixin;
    //返回
    private Button bn_return_weixin;
    //帮助
    private Button bn_question_weixin;
    //用户id
    private TextView user_id;
    //微信验证码
    private EditText weixin_check;

    private GiftManager giftManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weixincheck);
        findView();
        setListener();
        initData();
    }

    private void findView(){
        bn_sure_weixin=(Button) findViewById(R.id.bt_sure_weixin);
        bn_return_weixin=(Button) findViewById(R.id.bn_re_weixin);
        bn_question_weixin=(Button) findViewById(R.id.question_weixin);
        user_id=(TextView) findViewById(R.id.userid_weixin);
        weixin_check = (EditText)findViewById(R.id.weixin_check);

    }
    private void setListener(){
        bn_sure_weixin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String code = weixin_check.getText().toString();
                if(code.isEmpty()){
                    Toast.makeText(Activity_weixincheck.this,"请绑定码",Toast.LENGTH_SHORT).show();
                }else{
                    giftManager.bindWx(code);
                }
            }

        });
        bn_return_weixin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Activity_weixincheck.this.finish();
            }

        });
        bn_question_weixin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater inflaterDl = LayoutInflater.from(Activity_weixincheck.this);
                LinearLayout layout =(LinearLayout)inflaterDl.inflate(R.layout.dialog_instru_layout, null);
                final Dialog dialog = new AlertDialog.Builder(Activity_weixincheck.this).create();
                dialog.show();
                dialog.getWindow().setContentView(layout);
                Button btnOK = (Button) layout.findViewById(R.id.instu_sure);
                btnOK.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
            }

        });
    }
    private void initData(){
        giftManager = new GiftManager(this, new ViewInterface() {
            @Override
            public void requestSuccessfully(String msg, String data) {
                Toast.makeText(Activity_weixincheck.this,"绑定成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestUnSuccessfully(String msg, String data) {
                Toast.makeText(Activity_weixincheck.this,"绑定码不存在或错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestError(String msg, String data) {
                Toast.makeText(Activity_weixincheck.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

