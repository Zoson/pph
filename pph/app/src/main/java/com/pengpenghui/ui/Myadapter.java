package com.pengpenghui.ui;

/**
 * Created by 文浩 on 2015/7/19.
 */
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Myadapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> list;
    private int layoutID;
    private String flag[];
    private int ItemIDs[];
    public Myadapter(Context context, List<Map<String, Object>> list,
                     int layoutID, String flag[], int ItemIDs[]) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.layoutID = layoutID;
        this.flag = flag;
        this.ItemIDs = ItemIDs;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(layoutID, null);
        for (int i = 0; i < flag.length; i++) {//备注1
            if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {
                ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);
                iv.setBackgroundResource((Integer) list.get(position).get(
                        flag[i]));
            } else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {
                TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);
                tv.setText((String) list.get(position).get(flag[i]));
            }else{

            }
        }
        addListener(convertView,position);
        return convertView;
    }
    /**
     * 监听事件
     */
    public void addListener(View convertView, final int k) {
        ((ToggleButton)convertView.findViewById(R.id.mTogBtn)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // TODO Auto-generated method stub
                if (isChecked) {
                    Log.d("hehe","hehe"+k);
                } else {
                    //未选中
                    Log.d("aa","aa"+k);
                }
            }
        });// 添加监听事件


    }
}