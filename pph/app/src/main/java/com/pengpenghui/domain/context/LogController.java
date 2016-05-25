package com.pengpenghui.domain.context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pengpenghui.domain.entity.DataBaseTable;
import com.pengpenghui.domain.entity.DataProvider;
import com.pengpenghui.domain.entity.HttpApi;
import com.pengpenghui.domain.entity.User;

import com.pengpenghui.domain.service.database.DataBaseOperator;
import com.pengpenghui.domain.service.http.HttpFileListener;
import com.pengpenghui.domain.service.http.HttpListener;
import com.pengpenghui.domain.service.SharedPreference;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zoson on 6/19/15.
 */
public class LogController extends PPHContext{
    private SharedPreference sharedPreference;
    private DataBaseOperator dataBaseOperator;
    private User user;
    public static LogController get(){
        return instance;
    }

    private static LogController instance = new LogController();

    protected LogController(){
        sharedPreference = getSharePreference();
        dataBaseOperator = getDataBaseOperator();
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
        sharedPreference.set("account", account);
        sharedPreference.set("password",password);
    }

    public String[] getAccountRecord(){
        String account = sharedPreference.get("account","");
        String password = sharedPreference.get("password","");
        String []arr = {account,password};
        return arr;
    }

    public void tryLog(final String id,final String psw, final ContextCallback contextCallback){
        final User user = new User();
        user.setId(id);
        user.setPassWord(psw);
        HttpApi.log(user, new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                getDataProvider().setUserState(DataProvider.LOGIN);
                try {
                    user.initByJson(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getDataProvider().setUser(user);
                if (user == null){contextCallback.response(ContextCallback.FAIL,"数据处理出错");return;}
                if (isIdExist(user.getId())){
                    String[] attr = {DataBaseTable.UserDataTable.ID};
                    String[] value = {user.getId()};
                    dataBaseOperator.update(user,DataBaseTable.UserDataTable.USER_TABLE_NAME,attr,value);
                }else {
                    dataBaseOperator.insert(user, DataBaseTable.UserDataTable.USER_TABLE_NAME);
                }
                contextCallback.response(ContextCallback.SUCC, message);
                recordAccunt(id, psw);
                getPhoto(user.getImg(),contextCallback);
            }

            @Override
            public void fail(String message) {
                contextCallback.response(ContextCallback.FAIL, "登录失败");

            }
        });
    }

    public void getPhoto(final String img, final ContextCallback callback){
        HttpApi.getFile(img , new HttpFileListener() {
            @Override
            public void succ(String message, byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                User user = getDataProvider().getUser();
                user.setBitmap(bitmap);
                System.out.println("Logcontroller getPhoto "+img);
                callback.response(ContextCallback.SUCC,message);
            }

            @Override
            public void fail(String message) {

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
        final User user = new User();
        user.setPassWord(password);
        user.setId(account);
        user.setNickName(nickname);
        HttpApi.register(user, new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                getDataProvider().setUser(user);
                contextCallback.response(ContextCallback.SUCC,message);
            }

            @Override
            public void fail(String message) {
                contextCallback.response(ContextCallback.FAIL,message);
            }
        });
    }


    public void forceTolog(ContextCallback contextCallback){
        user.setId(sharedPreference.get("account", ""));
        user.setPassWord(sharedPreference.get("password", ""));
        this.tryAutoLog(contextCallback);
    }

}
