package com.pengpenghui.domain.context;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pengpenghui.ui.R;

import java.util.List;
import java.util.Map;

/**
 * Created by 文浩 on 2016/4/20.
 */
public class MyAttentionAdapter extends BaseAdapter {
    //没有定义实体，我就先这么写了
    private LayoutInflater mInflater;
    private List<Map<String, Object>> list;
    private int layoutID;

    public MyAttentionAdapter(Context context, List<Map<String, Object>> list,
                     int layoutID) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.layoutID = layoutID;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        //未定义
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView( int position, View convertView, ViewGroup parent) {
        AttentionViewHolder viewHolder;
        if(convertView==null){
            convertView = mInflater.inflate(layoutID, null);
            viewHolder=new AttentionViewHolder();
            findView(viewHolder,convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(AttentionViewHolder)convertView.getTag();
        }
        initData(viewHolder,position);
        addListener(convertView, position);
        return convertView;
    }
    private void findView(AttentionViewHolder viewHolder,View convertView){
        viewHolder.date_image = (ImageView)convertView.findViewById(R.id.itemAttentionimage);
        viewHolder.data_tittle = (TextView)convertView.findViewById(R.id.itemAttentionName);
        viewHolder.data_address = (TextView)convertView.findViewById(R.id.itemAttentionText);
        viewHolder.bu_delete = (Button)convertView.findViewById(R.id.itemAttentionbutton);

    }
    private void initData(AttentionViewHolder viewHolder,int position){
        viewHolder.date_image.setBackgroundResource((int) list.get(position).get("image"));
        viewHolder.data_tittle.setText((String)list.get(position).get("title"));
        viewHolder.data_address.setText((String)list.get(position).get("text"));
    }
    public void addListener(View convertView, final int k) {
        ((Button)convertView.findViewById(R.id.itemAttentionbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("shanchu","shanchu"+k);
            }
        });
    }
    private class AttentionViewHolder{
        public ImageView date_image;
        public TextView  data_tittle;
        public TextView  data_address;
        public Button    bu_delete;

    }
}

