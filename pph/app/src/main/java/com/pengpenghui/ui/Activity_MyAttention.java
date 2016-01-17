package com.pengpenghui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的关注页面
 * Created by 文浩 on 2016/1/11.
 */
public class Activity_MyAttention extends Activity {
    private ListView myList;
    private Button reButton;

    //预设
    private String[] example={"s1","s2","s3","s4","s5"};
    private int[] pid={R.drawable.yushepic,R.drawable.yushepic,
            R.drawable.yushepic,R.drawable.yushepic,R.drawable.yushepic,};
    // 定义item数量 变量自己定,以上是预设

    // 定义item内容
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myattention);
        findView();
        initData();
        setListener();

    }
    private void initData(){


    }
    private void findView(){
        reButton=(Button)findViewById(R.id.bn_re_attention);
        myList=(ListView)findViewById(R.id.my_list);
        setAdapter();
    }
    private void setListener(){
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.d("list", "你点击了第" + arg2 + "行");
            }
        });
        reButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Activity_MyAttention.this.finish();
            }

        });
    }

    private void setAdapter(){
        List<Map<String, Object>> listems  = new ArrayList<Map<String, Object>>();
        for(int i = 0; i <example.length; i++) {
            Map<String, Object> listem  = new HashMap<String, Object>();
            listem .put("image", pid[i]);
            listem .put("title", example[i] );
            listems .add(listem);
        }

        SimpleAdapter adapter = new SimpleAdapter(this.getApplicationContext(),
                listems,R.layout.list_attention_item,
                new String[]{"title","image"},
                new int[]{R.id.itemText,R.id.itempic});
        myList.setAdapter(adapter);
    }


}
