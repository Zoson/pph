package com.pengpenghui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Zoson on 15/11/20.
 */
public class Activity_GetGift extends Activity {

    private TextView bt_get;
    private EditText et_code;
    private TextView tv_gift_count;
    private TextView tv_gift_sum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
    }

    private void findView(){
        bt_get = (TextView)findViewById(R.id.bt_get);
        et_code = (EditText)findViewById(R.id.et_code);
        tv_gift_count = (TextView)findViewById(R.id.tv_count);
        tv_gift_sum = (TextView)findViewById(R.id.tv_sum);
    }

}
