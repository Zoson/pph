package com.pengpenghui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.MainController;
import com.pengpenghui.domain.context.MyAttentionAdapter;
import com.pengpenghui.domain.entity.AdData;
import com.pengpenghui.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的关注页面
 * Created by 文浩 on 2016/1/11.
 */
public class MyAttentionActivity extends Activity {
    private ListView myList;
    private Button reButton;
    private Button delButton;
    private MainController mainController;
    private MyAttentionAdapter madapter;
    //预设
    private String[] example={"莲花超市(洗护产品)","中山灯具",};
    private String[] example2={"广州市番禺区","中山市某某路"};
    private int[] pid={R.drawable.attention_example,R.drawable.attention_example2};
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
        mainController = MainController.get();
        List<AdData> list = mainController.getAttention(new ContextCallback() {
            @Override
            public void response(int state, Object object) {
                notifyMyAdapter();
            }
        });
        madapter= new MyAttentionAdapter(this,list,R.layout.list_attention_item);
        myList.setAdapter(madapter);
    }

    public void notifyMyAdapter(){
        if (madapter == null)return;
        myList.removeAllViewsInLayout();
        madapter.notifyDataSetChanged();
        System.out.println("MyAttentionActivity notifyMyAdapter");
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

        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAttentionActivity.this.finish();
            }

        });
    }

    private void setAdapter(){
//        List<Map<String, Object>> listems  = new ArrayList<Map<String, Object>>();
//        for(int i = 0; i <example.length; i++) {
//            Map<String, Object> listem  = new HashMap<String, Object>();
//            listem .put("image", pid[i]);
//            listem .put("title", example[i] );
//            listem .put("text", example2[i] );
//            listems .add(listem);
//        }
    }


}
