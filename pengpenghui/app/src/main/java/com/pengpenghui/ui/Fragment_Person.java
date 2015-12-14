package com.pengpenghui.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.pengpenghui.domain.controller.MainPageController;
import com.pengpenghui.domain.entity.UserModel;
import com.pengpenghui.pph_interface.ViewInterface;
import com.pengpenghui.ui.component.GetBroDialog;
import com.pengpenghui.ui.component.RenameDialog;


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
            "红包","分享","微信验证"};
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

    @Override
    public void onResume() {
        super.onResume();
        updateInfo();
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
                        Toast.makeText(getActivity(),"领取成功",Toast.LENGTH_SHORT).show();break;
                    case "changeInfo":
                        personName.setText(userModel.getNickName());
                        personPicture.setImageBitmap(userModel.getBitmap());
                        break;
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
                        Intent intent=new Intent(getActivity(),Activity_Reperson.class);
                        getActivity().startActivityForResult(intent, Activity.RESULT_OK);
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(getActivity(),Activity_GetGift.class);
                        getActivity().startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent=new Intent(getActivity(),Activity_Share.class);
                        startActivity(intent);
                        break;
                    }
                    case 4: {
//                        Intent intent=new Intent(getActivity(),Activity_Setting.class);
//                        startActivity(intent);
//                        getActivity().finish();
                        LayoutInflater inflaterDl = LayoutInflater.from(getActivity());
                        LinearLayout layout =(LinearLayout)inflaterDl.inflate(R.layout.dialog_instru_layout, null );
                        final Dialog dialog = new AlertDialog.Builder(getActivity()).create();
                        dialog.show();
                        dialog.getWindow().setContentView(layout);
                        Button btnOK = (Button) layout.findViewById(R.id.instu_sure);
                        btnOK.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
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

    public void updateInfo(){
        personPicture.setImageBitmap(userModel.getBitmap());
        personName.setText(userModel.getNickName());
    }


}