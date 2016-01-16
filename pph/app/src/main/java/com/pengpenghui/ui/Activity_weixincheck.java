package com.pengpenghui.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.GiftManager;


/**
 * Created by 文浩 on 2015/12/16.
 */
public class Activity_weixincheck extends Activity {
    private LayoutInflater inflaterDl;
    private LinearLayout layout_dia;
    private  Dialog dialog;
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
        inflaterDl = LayoutInflater.from(Activity_weixincheck.this);
        layout_dia =(LinearLayout)inflaterDl.inflate(R.layout.dialog_instru_layout, null);
        dialog = new AlertDialog.Builder(Activity_weixincheck.this).create();
        show_dia();
    }
    private void setListener(){
        bn_sure_weixin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String code = weixin_check.getText().toString();
                if(code.isEmpty()){
                    Toast.makeText(Activity_weixincheck.this,"请绑定码",Toast.LENGTH_SHORT).show();
                }else{
                    giftManager.bindWx(code, new ContextCallback() {
                        @Override
                        public void response(int state, Object object) {
                            if (state == SUCC) {
                                Toast.makeText(Activity_weixincheck.this,"绑定成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Activity_weixincheck.this,""+object,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
                dialog.show();

            }

        });
    }
    private void initData(){
        giftManager = new GiftManager();

    }
    private void show_dia(){
        dialog.show();
        dialog.getWindow().setContentView(layout_dia);
        Button btnOK = (Button) layout_dia.findViewById(R.id.instu_sure);
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }
}

