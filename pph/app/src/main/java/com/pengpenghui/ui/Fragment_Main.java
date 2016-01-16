package com.pengpenghui.ui;

import android.app.LauncherActivity;
import android.os.Bundle;
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

import java.util.Date;
import java.util.List;

public class Fragment_Main extends android.support.v4.app.Fragment{
    private View rootView;
	private Button re_bn;
    private RadioGroup mRadioGroup;
	private ListView listview;
    private TextView hongbaoAllCost;
    private TextView bro_sum;
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
        }

        return rootView;
    }
    public void findView(){
        re_bn = (Button) rootView.findViewById(R.id.bn_re_s);
        listview = (ListView) rootView.findViewById(R.id.listview_youhui);
//        alldc = (RadioButton) rootView.findViewById(R.id.youhui_text);
        //deadlineldc = (RadioButton) rootView.findViewById(R.id.guoqi_text);
        //这里修改优惠券的颜色
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.youhui_bg);
        hongbaoAllCost=(TextView) rootView.findViewById(R.id.hongbao_cost);
        bro_sum = (TextView)rootView.findViewById(R.id.bro_sum);
        //relativeLayout.setBackground();
        //youhuibg1到youhuibg4

    }
    public void initData(){
        mainPageController = new MainController();
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
                    bro_sum.setText("" + list.size() + " 张");
                }else {
                    Toast.makeText(getActivity(), (String) object, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}