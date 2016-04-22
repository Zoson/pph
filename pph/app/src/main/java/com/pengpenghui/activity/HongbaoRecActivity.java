package com.pengpenghui.activity;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pengpenghui.ui.R;

public class HongbaoRecActivity extends Activity {
    //返回按钮
    private Button rebn_hongbao;
    //一键领取
    private Button getMoney;
    //红包金额总数
    private TextView hongbao_allcost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hongbao_list);
        findView();
        setListener();
    }

    private void findView(){
        rebn_hongbao=(Button)this.findViewById(R.id.bn_re_hongbao);
        getMoney=(Button)this.findViewById(R.id.bt_sure_hongbao);
        hongbao_allcost=(TextView)findViewById(R.id.hongbao_allcost);

    }
    private void setListener(){
        rebn_hongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HongbaoRecActivity.this.finish();
            }
        });
        getMoney.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("提现", "yijiantixian");
            }
        }));
    }
    private void initData(){

    }
}
