package com.pengpenghui.domain.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.pengpenghui.domain.entity.DataBaseTable;
import com.pengpenghui.domain.entity.HttpApi;
import com.pengpenghui.domain.entity.UserModel;
import com.pengpenghui.pph_interface.HttpImgListener;
import com.pengpenghui.pph_interface.HttpListener;
import com.pengpenghui.service.HttpService;
import com.pengpenghui.service.SharedPreference;
import com.pengpenghui.pph_interface.ViewInterface;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zoson on 6/19/15.
 */
public class LogController {
    private ViewInterface viewInterface;
    private UserModel userModel;
    private Handler handler;
    private DataBaseOperator dataBaseOperator;
    private SharedPreference sharedPreference;
    private HttpService httpService;
    private Context context;
    public LogController(final Context context,ViewInterface viewInterface){
        this.viewInterface = viewInterface;
        this.context = context;
        userModel = UserModel.getInstance();
        sharedPreference = new SharedPreference(context);
        httpService = new HttpService();
        dataBaseOperator = new DataBaseOperator(context);
    }
    public void AutoLog(){
        sharedPreference.set("isAuto", "true");
    }
    public void tryAutoLog(){
        String[] arr = getAccountRecord();
        tryLog(arr[0], arr[1]);
    }
    public boolean isAutoLog(){
        if (Boolean.parseBoolean(sharedPreference.get("isAuto", "false"))){
            return true;
        }
        return false;
    }
    public void recordAccunt(String account,String password){
        sharedPreference.set("account",account);
        sharedPreference.set("password",password);
    }
    public String[] getAccountRecord(){
        String account = sharedPreference.get("account","");
        String password = sharedPreference.get("password","");
        String []arr = {account,password};
        return arr;
    }
    public void tryLog(final String id,final String psw){
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(final String message, final String data) {
                userModel.dealJsonData(data);
                httpService.setImgListener(new HttpImgListener() {

                    @Override
                    public void successToGetImg(Bitmap bitmap) {
                        userModel.setBitmap(bitmap);
                        viewInterface.requestSuccessfully(message,data);
                    }
                });
                httpService.getPhoto(userModel.getImg(),0);
                if (isIdExist(userModel.getId())){
                    String[] attr = {DataBaseTable.UserDataTable.ID};
                    String[] value = {userModel.getId()};
                    dataBaseOperator.update(userModel,DataBaseTable.UserDataTable.USER_TABLE_NAME,attr,value);
                }else {
                    dataBaseOperator.insert(userModel, DataBaseTable.UserDataTable.USER_TABLE_NAME);
                }
                viewInterface.requestSuccessfully(message,"");
                recordAccunt(id,psw);
            }

            @Override
            public void failToRequired(String message, String data) {
                viewInterface.requestUnSuccessfully(message, data);
            }

            @Override
            public void netWorkError() {
                viewInterface.requestError("error","error");
            }
        });
        HttpApi.log(httpService, id, psw);
    }
    public String readinfo(){
        String[] cols = {"id","account","password"};
        ArrayList<Map<String,String>> map = dataBaseOperator.query(DataBaseTable.UserDataTable.USER_TABLE_NAME, cols, null, null);
        String str = "";
        for (int i = 0;i<map.size();i++){
            str += map.get(i).get("account");
        }
        return str;
    }
    public boolean isIdExist(String id){
        String[] cols = {DataBaseTable.UserDataTable.ID};
        ArrayList<Map<String,String>> map = dataBaseOperator.query(DataBaseTable.UserDataTable.USER_TABLE_NAME,cols,null,null) ;
        for (int i =0;i<map.size();i++){
            if (map.get(i).get(DataBaseTable.UserDataTable.ID).equals(id)){
                return true;
            }
        }
        return false;
    }

    public void tryReg(final String account, final String password, final String nickname){
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(String message, String data) {
                httpService.setListener(new HttpListener() {
                    @Override
                    public void succToRequired(String message, String data) {
                        viewInterface.requestSuccessfully(message, data);
                    }

                    @Override
                    public void failToRequired(String message, String data) {
                        viewInterface.requestUnSuccessfully(message, data);
                    }

                    @Override
                    public void netWorkError() {
                        viewInterface.requestError("", "");
                    }
                });
               HttpApi.register(httpService,account,password,nickname);
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
        HttpApi.isSuited(httpService,account);
    }

    public void forceTolog(){
        sharedPreference = new SharedPreference(context);
        userModel.setAccount(sharedPreference.get("account",""));
        userModel.setPassWord(sharedPreference.get("password", ""));
        this.tryAutoLog();
    }

}
