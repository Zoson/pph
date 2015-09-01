package com.pengpenghui.ui;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pengpenghui.domain.controller.BroMessageListAdapter;
import com.pengpenghui.domain.controller.MainPageController;
import com.pengpenghui.pph_interface.ViewInterface;

import java.util.Date;

public class Fragment_Main extends android.support.v4.app.Fragment implements ViewInterface {
    private View rootView;
	private Button re_bn;
    private RadioGroup mRadioGroup;
	private ListView listview;
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
    private MainPageController mainPageController;
    private BroMessageListAdapter broMessageListAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //在这里定义Fragment的布局
        if (rootView == null){
            rootView = inflater.inflate(R.layout.activity_youhui,
                    container, false);
            findView();
            initData();
            getDiscount();
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
        //relativeLayout.setBackground();
        //youhuibg1到youhuibg4

    }
    public void initData(){

        mainPageController = new MainPageController(getActivity(),this);

    }
    private void initListView(){
        broMessageListAdapter = new BroMessageListAdapter(getActivity().getLayoutInflater(),mainPageController.getBroList());
        listview.setAdapter(broMessageListAdapter);
    }
    public void getDiscount(){
        mainPageController.getBro();
    }
    @Override
    public void requestSuccessfully(String msg, String data) {
        initListView();
    }

    @Override
    public void requestUnSuccessfully(String msg, String data) {

    }

    @Override
    public void requestError(String msg, String data) {

    }

}