package com.pengpenghui.activity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pengpenghui.domain.context.BroMessageListAdapter;
import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.GiftManager;
import com.pengpenghui.domain.context.MainController;
import com.pengpenghui.domain.entity.BroMessage;
import com.pengpenghui.ui.R;

import java.util.Date;
import java.util.List;

public class MainFragment extends android.support.v4.app.Fragment{
    private View rootView;
	private Button re_bn;
    private Button ToMoneyList;
    private RadioGroup mRadioGroup;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listview;
    private TextView hongbaoAllCost;
    private TextView bro_sum;
    private int bro_show;
    //private int[] bnbg={R.drawable.bn_sw_dis,R.color.white};
	private LauncherActivity.ListItem listItems;
	//全部优惠券
	private RadioButton alldc;
	//即将过期的优惠券
	private RadioButton deadlineldc;
	//优惠价格
	private int disccount;
	//优惠券类型
	private String type1="元优惠券";
	//优惠提供方
	private String address;
	//开始时间
	private Date startDate;
	private Date endDate;
    private RelativeLayout relativeLayout;

	//结束时间
    private MainController mainPageController;
    private BroMessageListAdapter broMessageListAdapter;
    private GiftManager giftManager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //在这里定义Fragment的布局
        if (rootView == null){
            rootView = inflater.inflate(R.layout.activity_youhui,
                    container, false);
            findView();
            initData();
            setListener();
        }

        return rootView;
    }
    public void findView(){
        re_bn = (Button) rootView.findViewById(R.id.bn_re_s);
        ToMoneyList=(Button)rootView.findViewById(R.id.hongbaoButton);
        listview = (ListView) rootView.findViewById(R.id.listview_youhui);
//        alldc = (RadioButton) rootView.findViewById(R.id.youhui_text);
        //deadlineldc = (RadioButton) rootView.findViewById(R.id.guoqi_text);
        //这里修改优惠券的颜色
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.youhui_bg);
        hongbaoAllCost=(TextView) rootView.findViewById(R.id.youhui_money);
        bro_sum = (TextView)rootView.findViewById(R.id.youhui_cost);
        //relativeLayout.setBackground();
        //youhuibg1到youhuibg4




//        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container_youhui);
//        //设置刷新时动画的颜色，可以设置4个
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
//                                                   android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//
//            @Override
//            public void onRefresh() {
//                Log.v("s", "正在刷新");
//                // TODO Auto-generated method stub
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        Log.v("s", "刷新完成");
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 6000);
//            }
//        });
    }
    public void setListener(){
        ToMoneyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),HongbaoRecActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initData(){
        mainPageController = MainController.get();
        if (!mainPageController.isUserLogin()){
            return;
        }

        giftManager = new GiftManager();
        giftManager.getGiftCount(new ContextCallback() {
            @Override
            public void response(int state, Object object) {
                if (state == SUCC) {
                    hongbaoAllCost.setText((String) object);
                } else {
                    Toast.makeText(getActivity(), "" + object, Toast.LENGTH_SHORT).show();
                }
            }
        });
        mainPageController.getBro(new ContextCallback() {
            @Override
            public void response(int state, Object object) {
                if (state == SUCC){
                    List<BroMessage> list = (List<BroMessage>)object;
                    broMessageListAdapter = new BroMessageListAdapter(getActivity().getLayoutInflater(), list);
                    listview.setAdapter(broMessageListAdapter);
                    bro_show=list.size();
                    if(bro_show<10)
                        bro_sum.setText("0"+list.size()+"");
                    else
                        bro_sum.setText(list.size()+"");

                }else {
                    Toast.makeText(getActivity(), (String) object, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}