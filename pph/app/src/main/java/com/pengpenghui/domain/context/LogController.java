package com.pengpenghui.domain.context;
import android.graphics.Bitmap;

import com.pengpenghui.domain.entity.DataBaseTable;
import com.pengpenghui.domain.entity.HttpApi;
import com.pengpenghui.domain.entity.User;

import com.pengpenghui.service.database.DataBaseOperator;
import com.pengpenghui.service.http.HttpImgListener;
import com.pengpenghui.service.http.HttpListener;
import com.pengpenghui.service.SharedPreference;
import com.pengpenghui.service.http.HttpRequest;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zoson on 6/19/15.
 */
public class LogController extends PPHContext{
    private SharedPreference sharedPreference;
    private DataBaseOperator dataBaseOperator;
    private HttpRequest httpRequest;
    private User user;
    public LogController(){
        sharedPreference = getSharePreference();
        dataBaseOperator = getDataBaseOperator();
        httpRequest = getHttpService();
        user = getDataProvider().getUser();
    }

    public void AutoLog(){
        sharedPreference.set("isAuto", "true");
    }

    public void tryAutoLog(ContextCallback contextCallback){
        String[] arr = getAccountRecord();
        tryLog(arr[0], arr[1], contextCallback);
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

    public void tryLog(final String id,final String psw, final ContextCallback contextCallback){
        HttpApi.log(httpRequest,id, psw, new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
                user = getDataProvider().genUserByJson(data);
                if (user == null){contextCallback.response(ContextCallback.FAIL,"数据处理出错");return;}
                if (isIdExist(user.getId())){
                    String[] attr = {DataBaseTable.UserDataTable.ID};
                    String[] value = {user.getId()};
                    dataBaseOperator.update(user,DataBaseTable.UserDataTable.USER_TABLE_NAME,attr,value);
                }else {
                    dataBaseOperator.insert(user, DataBaseTable.UserDataTable.USER_TABLE_NAME);
                }
                contextCallback.response(ContextCallback.SUCC, msg);
                recordAccunt(id, psw);
                getPhoto(user.getImg());
            }

            @Override
            public void failToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "登录失败");
            }

            @Override
            public void netWorkError(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "网络错误");
            }

        });
    }

    public void getPhoto(String img){
        HttpApi.getPhoto(httpRequest,img, new HttpImgListener() {
            @Override
            public void succToImg(Bitmap bitmap) {
                user.setBitmap(bitmap);
            }
        });
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

    public void tryReg(final String account, final String password, final String nickname, final ContextCallback contextCallback){
        HttpApi.isSuited(httpRequest,account, new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
                regAccount(account,password,nickname,contextCallback);
                contextCallback.response(ContextCallback.SUCC,msg);
            }

            @Override
            public void failToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL,"账号不存在");
            }

            @Override
            public void netWorkError(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL,"网络错误");
            }
        });
    }

    public void regAccount(String account,String password,String nickname, final ContextCallback contextCallback){
        HttpApi.register(httpRequest,account, password, nickname, new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.SUCC,msg);
            }

            @Override
            public void failToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL,"注册失败");
            }

            @Override
            public void netWorkError(String msg, String data) {
                contextCallback.response(ContextCallback.ERROR,"网络错误");
            }
        });
    }

    public void forceTolog(ContextCallback contextCallback){
        user.setAccount(sharedPreference.get("account",""));
        user.setPassWord(sharedPreference.get("password", ""));
        this.tryAutoLog(contextCallback);
    }

}
