package com.pengpenghui.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_Setting extends Activity {
    private Button bn_re;       //返回按钮
    private Button bn_relogin;  //重新登录
    private ListView listview;
    private String[] itemsNames = {"setting1", "setting2",
            "setting3", "setting4"};
    private int[] pid={R.drawable.it1,R.drawable.it2,
            R.drawable.it3,R.drawable.it4};
    private List<Map<String, Object>> listItems;
    //private ToggleButton mTogBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);

       // mTogBtn=(ToggleButton)findViewById(R.id.mTogBtn);
        listview=(ListView)findViewById(R.id.listview_se);
        bn_re=(Button)findViewById(R.id.bn_re_s);
        bn_relogin=(Button)findViewById(R.id.relogin);   //退出按钮
        listItems  = new ArrayList<Map<String, Object>>();
        for(int i = 0; i <4; i++) {
            Map<String, Object> listem  = new HashMap<String, Object>();
            listem.put("image", pid[i]);
            listem .put("title", itemsNames[i] );
            listItems .add(listem);
        }
        Myadapter adapter = new Myadapter(this,
                listItems,R.layout.list_item_st,
                new String[]{"image","title"},
                new int[]{R.id.itempic_st,R.id.itemText_st});

        listview.setAdapter(adapter);

        bn_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Setting.this.finish();

            }
        });

        bn_relogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注销
                Intent intent = new Intent();
                intent.setClass(Activity_Setting.this, Activity_Login.class);
                finish();
                startActivity(intent);
            }
        });
    }
}

