package com.pengpenghui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.MainController;
import com.pengpenghui.domain.entity.User;
import com.pengpenghui.ui.R;


public class PersonFragment extends Fragment {
    private View rootView;
	private ImageView personPicture;
    private TextView personName;
    private TextView phoneNum;
    private ListView listview;
    private TextView tv_login;
    private User user;
    private MainController mainPageController;
    private AdapterView.OnItemClickListener listener;
    private String[] itemsNames = {"个人信息", "我的关注",
            "分享","微信验证"};
    private int[] pid={R.drawable.it1,R.drawable.it2,
           R.drawable.it4,R.drawable.it5};
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

    @Override
    public void onResume() {
        super.onResume();
        if (!mainPageController.isUserLogin())return;
        updateInfo();
    }

    private void initData(){
        mainPageController = new MainController();
        if(!mainPageController.isUserLogin()){
            tv_login.setText("登录");
            return;
        }
        tv_login.setText("退出登录");
        user = mainPageController.getUser();
        personName.setText(user.getNickName());
        phoneNum.setText(user.getId());
        personPicture.setImageBitmap(user.getBitmap());
    }
    private void setAdapter(){
        List<Map<String, Object>> listems  = new ArrayList<Map<String, Object>>();
        for(int i = 0; i <4; i++) {
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
        tv_login = (TextView)rootView.findViewById(R.id.tv_login);
        personPicture= (ImageView) rootView.findViewById(R.id.userpic);
        personName= (TextView) rootView.findViewById(R.id.uesrname_show);
        phoneNum= (TextView) rootView.findViewById(R.id.phone);
        listview= (ListView) rootView.findViewById(R.id.listview);
    }
    private void setListener(){
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainPageController.isUserLogin()){
                    mainPageController.exit();
                }
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        listview.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                if (!mainPageController.isUserLogin()){
                    Toast.makeText(getActivity(),"您还未登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (position) {
                    case 0: {
                        Intent intent=new Intent(getActivity(),RepersonActivity.class);
                        getActivity().startActivityForResult(intent, Activity.RESULT_OK);
                        break;
                    }
                    case 1: {
                        Intent intent=new Intent(getActivity(),MyAttentionActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 2: {
                        Intent intent=new Intent(getActivity(),ShareActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent=new Intent(getActivity(),WeixinCheckActivity.class);
                        startActivity(intent);
                        //getActivity().finish();

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
        mainPageController.fromNfcTagToGetAd("0000", new ContextCallback() {
            @Override
            public void response(int state, Object object) {
                GetBroDialog getBroDialog = new GetBroDialog(getActivity(),mainPageController);
                getBroDialog.show();
            }
        });
    }

    public void updateInfo(){
        personPicture.setImageBitmap(user.getBitmap());
        personName.setText(user.getNickName());
    }


}