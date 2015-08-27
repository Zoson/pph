package com.pengpenghui.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.pengpenghui.domain.controller.MainPageController;
import com.pengpenghui.domain.entity.UserModel;
import com.pengpenghui.pph_interface.ViewInterface;
import com.pengpenghui.ui.component.GetBroDialog;


public class Fragment_Person extends Fragment {
    private View rootView;
	private ImageView personPicture;
    private TextView personName;
    private TextView phoneNum;
    private ListView listview;
    private UserModel userModel;
    private MainPageController mainPageController;
    private AdapterView.OnItemClickListener listener;
    private String[] itemsNames = {"个人信息", "我的关注",
            "分享", "设置","获取优惠"};
    private int[] pid={R.drawable.it1,R.drawable.it2,
            R.drawable.it3,R.drawable.it4,R.drawable.ic_launcher};
    private List<Map<String, Object>> listItems;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.activity_owner,
                    container, false);
            findView();
            setAdapter();
            setListener();
            initData();
        }

		return rootView;
    }
    private void initData(){
        mainPageController = new MainPageController(getActivity(),new ViewInterface() {
            @Override
            public void requestSuccessfully(String msg, String data) {
                switch (msg){
                    case "0":
                        GetBroDialog getBroDialog = new GetBroDialog(getActivity(),mainPageController);
                        getBroDialog.show();
                        break;
                    case "1":
                        Toast.makeText(getActivity(),"领取成功",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void requestUnSuccessfully(String msg, String data) {

            }

            @Override
            public void requestError(String msg, String data) {

            }
        });
        userModel = UserModel.getInstance();
        personName.setText(UserModel.getInstance().getNickName());
        phoneNum.setText(UserModel.getInstance().getId());
        personPicture.setImageBitmap(userModel.getBitmap());
    }
    private void setAdapter(){
        List<Map<String, Object>> listems  = new ArrayList<Map<String, Object>>();
        for(int i = 0; i <5; i++) {
            Map<String, Object> listem  = new HashMap<String, Object>();
            listem .put("image", pid[i]);
            listem .put("title", itemsNames[i] );
            listems .add(listem);
        }

        SimpleAdapter adapter = new SimpleAdapter(this.getActivity().getApplicationContext(),
                listems,R.layout.list_item,
                new String[]{"title","image"},
                new int[]{R.id.itemText,R.id.itempic});

        listview.setAdapter(adapter);
    }
    private void findView(){
        personPicture= (ImageView) rootView.findViewById(R.id.userpic);
        personName= (TextView) rootView.findViewById(R.id.uesrname_show);
        phoneNum= (TextView) rootView.findViewById(R.id.phone);
        listview= (ListView) rootView.findViewById(R.id.listview);
    }
    private void setListener(){
        listview.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                switch (position) {
                    case 0: {
                        Toast.makeText(getActivity(), "您�?�择了标题：" + position, Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getActivity(),Activity_Reperson.class);
                        getActivity().startActivity(intent);
                        break;
                    }
                    case 1: {
                        Toast.makeText(getActivity(), "您�?�择了标题：" + position, Toast.LENGTH_LONG).show();


                        break;
                    }
                    case 2: {
                        //Toast.makeText(getActivity(), "您�?�择了标题：" + position, Toast.LENGTH_LONG).show();
                        //这里是测试页�?
                        Intent intent=new Intent(getActivity(),Activity_Share.class);
                        getActivity().startActivity(intent);
                        break;
                    }
                    case 3: {
                        //Toast.makeText(getActivity(), "您�?�择了标题：" + position, Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getActivity(),Activity_Setting.class);
                        getActivity().startActivity(intent);
                        break;
                    }
                    case 4:{
                        TestToGetAd();

                        break;
                    }
                    default: {

                        break;
                    }

                }
            }
        });
    }
    private void TestToGetAd(){
        mainPageController.fromNfcTagToGetAd("1234");
    }


}