package com.pengpenghui.domain.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.pengpenghui.domain.entity.AdData;
import com.pengpenghui.domain.entity.BroMessageModel;
import com.pengpenghui.domain.entity.DataBaseTable;
import com.pengpenghui.domain.entity.HttpApi;
import com.pengpenghui.domain.entity.UserModel;
import com.pengpenghui.pph_interface.HttpListener;
import com.pengpenghui.service.HttpService;
import com.pengpenghui.service.SharedPreference;
import com.pengpenghui.pph_interface.ViewInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zoson on 7/21/15.
 */
public class MainPageController {
    private ViewInterface viewInterface;
    private Context context;
    private UserModel userModel;
    private List<BroMessageModel> broMessageModels;
    private AdData adData;
    private DataBaseOperator dataBaseOperator;
    private SharedPreference sharedPreference;
    private HttpService httpService;
    public MainPageController(Context context,ViewInterface viewInterface){
        this.viewInterface = viewInterface;
        this.context = context;
        this.userModel = UserModel.getInstance();
        httpService = new HttpService();
        broMessageModels  = new ArrayList<BroMessageModel>();
        dataBaseOperator = new DataBaseOperator(context);
    }

    public List<BroMessageModel> getBroList(){
        return broMessageModels;
    }

    private void setBroList(String data){
        List<String> list = getAllBroIdFromDB();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int count = jsonObject.getInt("count");
            for (int i =0 ; i < count ; i++){
                BroMessageModel broMessageModel = new BroMessageModel();
                broMessageModel.dealJsonData(jsonObject.getString(""+i));
                if (broMessageModel == null) continue;
                broMessageModels.add(broMessageModel);
                boolean isInsertable = true;
                for (int j = 0;j<list.size();j++){
                    System.out.println("list_siez"+j+"++++++++++++++++++++++++++====");
                    if (broMessageModel.getDisId()==Integer.parseInt(list.get(j))){
                        isInsertable = false;
                        break;
                    }
                }
                if (isInsertable == true){
                    dataBaseOperator.insert(broMessageModel, DataBaseTable.BroMessageTable.BROMESSAGE_TABLE_NAME);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getBro(){
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(String message, String data) {
                setBroList(data);
                viewInterface.requestSuccessfully(message,data);
            }

            @Override
            public void failToRequired(String message, String data) {
                viewInterface.requestUnSuccessfully(message,data);
            }

            @Override
            public void netWorkError() {
                viewInterface.requestError("error","error");
            }
        });
        HttpApi.ownsToGetDis(httpService, userModel.getId());
    }

    public void fromNfcTagToGetAd(String tag){
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(String message, String data) {
                adData = new AdData(data);
                viewInterface.requestSuccessfully("0", "succ");
            }

            @Override
            public void failToRequired(String message, String data) {

            }

            @Override
            public void netWorkError() {

            }
        });
        HttpApi.tagToGetAd(httpService, tag);
    }

    public void getBroByAd(int i){
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(String message, String data) {
                System.out.println("getBro= "+data);
                viewInterface.requestSuccessfully("1",data);
            }

            @Override
            public void failToRequired(String message, String data) {

            }

            @Override
            public void netWorkError() {

            }
        });
        HttpApi.insertOwns(httpService, userModel.getId(), adData.getDis_id()[i] + "");
    }

    public AdData getAdData(){
        return adData;
    }

    private List<String> getAllBroIdFromDB(){
        String[] cols = {DataBaseTable.BroMessageTable.DISID};
        ArrayList<Map<String,String>> list_map = dataBaseOperator.query(DataBaseTable.BroMessageTable.BROMESSAGE_TABLE_NAME, cols, null, null);
        ArrayList<String> list = new ArrayList<String>();
        for (int i=0;i<list_map.size();i++){
            list.add(list_map.get(i).get(DataBaseTable.BroMessageTable.DISID));
        }
        return list;
    }

    public void changePassword(String oldps,String newps){
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(String message, String data) {
                viewInterface.requestSuccessfully("changeps",data);
            }

            @Override
            public void failToRequired(String message, String data) {
                viewInterface.requestUnSuccessfully(message,data);
            }

            @Override
            public void netWorkError() {
                viewInterface.requestError("","");
            }
        });
        HttpApi.setPsw(httpService,userModel.getId(),oldps,newps);
    }

    public void changeName(String name){
        userModel.setNickName(name);
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(String message, String data) {
                try {
                    JSONObject js = new JSONObject(data);
                    userModel.setNickName(js.getString("newNickName"));
                    viewInterface.requestSuccessfully("changename", js.getString("newNickName"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failToRequired(String message, String data) {
                viewInterface.requestUnSuccessfully(message,data);
            }

            @Override
            public void netWorkError() {
                viewInterface.requestError("","");
            }
        });
        HttpApi.setNickName(httpService, userModel.getId(), name);
        System.out.println("uid=" + userModel.getId());
    }

    public void changPicture(String file){
        sharedPreference = new SharedPreference(context);
        sharedPreference.set("file", file);
        HttpApi.setPicture(httpService, userModel.getId(), file);
        viewInterface.requestSuccessfully("changeInfo", "");
    }
}
