package com.pengpenghui.domain.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pengpenghui.domain.entity.BroMessageModel;
import com.pengpenghui.ui.R;
import java.util.List;

/**
 * Created by zoson on 6/25/15.
 */
public class BroMessageListAdapter extends BaseAdapter {
    private List<BroMessageModel> list_bromessage;
    private LayoutInflater layoutInflater;
    public BroMessageListAdapter(LayoutInflater layoutInflater,List<BroMessageModel> list_bromessage){
        this.layoutInflater = layoutInflater;
        this.list_bromessage = list_bromessage;
    }
    @Override
    public int getCount() {
        return list_bromessage.size();
    }

    @Override
    public Object getItem(int position) {
        return list_bromessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.youhui_item,null);
        ViewHolder viewHolder = new ViewHolder();
        findView(viewHolder, convertView);
        initData(viewHolder,position);
        return convertView;
    }
    private void initData(ViewHolder viewHolder,int position){
        viewHolder.tv_dis_money.setText(String.valueOf(list_bromessage.get(position).getDisMoney()));
        viewHolder.tv_date_begin.setText(list_bromessage.get(position).getDuration().toString_beginDate());
        viewHolder.tv_date_end.setText(list_bromessage.get(position).getDuration().toString_endDate());
        viewHolder.tv_address.setText(list_bromessage.get(position).getStoreName());
        viewHolder.tv_style.setText("元优惠券");
    }
    private void findView(ViewHolder viewHolder,View convertView){
        viewHolder.tv_address = (TextView)convertView.findViewById(R.id.youhui_address);
        viewHolder.tv_date_begin = (TextView)convertView.findViewById(R.id.start_data);
        viewHolder.tv_date_end = (TextView)convertView.findViewById(R.id.end_data);
        viewHolder.tv_dis_money = (TextView)convertView.findViewById(R.id.youhui_price);
        viewHolder.tv_style = (TextView)convertView.findViewById(R.id.youhui_style);
    }
    private class ViewHolder{
        public TextView tv_date_begin;
        public TextView tv_date_end;
        public TextView tv_dis_money;
        public TextView tv_address;
        public TextView tv_style;
    }
}
