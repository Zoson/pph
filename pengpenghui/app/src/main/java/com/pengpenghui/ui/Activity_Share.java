package com.pengpenghui.ui;

import android.app.Activity;
import android.app.LauncherActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by 文浩 on 2015/7/19.
 */
public class Activity_Share extends Activity {


    //优惠价格
    private int disccount_share;
    //优惠券类型
    private String type_share="元优惠券";
    //优惠提供方
    private String address_share;
    //开始时间
    private String startDate;
    private String  endDate;

    private TextView type;
    private TextView price;
    private TextView address;
    private TextView startDate_share;
    private TextView endDate_share;
    private RelativeLayout relativeLayout_sharet;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.yiuhui_share);
        price = (TextView)findViewById(R.id.youhui_price_share);
        type = (TextView)findViewById(R.id.youhui_style_share);
        address = (TextView)findViewById(R.id.youhui_address_share);
        startDate_share = (TextView)findViewById(R.id.start_data_share);
        endDate_share = (TextView)findViewById(R.id.end_data_share);
        //这里修改优惠券的颜色
        relativeLayout_sharet = (RelativeLayout)findViewById(R.id.youhui_bg_share);
        //relativeLayout.setBackground();
        //youhuibg1到youhuibg4

        //测试
        disccount_share=10;
        String price00=disccount_share+"";
        type_share="元优惠券";
        address_share ="晓键书店";
        startDate="2015.06.07";
        endDate  ="2015.09.10";
        price.setText(price00);
        setting(price00, type_share, address_share, startDate, endDate);
    }

    public void setting(String pri,String typ,String add,String  data1,String data2)
    {
        price.setText(pri);
        type.setText(typ);
        address.setText(add);
        startDate_share.setText(data1);
        endDate_share.setText(data2);
    }
}
