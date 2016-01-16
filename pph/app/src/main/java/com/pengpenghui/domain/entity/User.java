package com.pengpenghui.domain.entity;

import android.content.ContentValues;
import android.graphics.Bitmap;

import com.pengpenghui.service.database.DataObjectInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by 杨超chao on 2015/6/17.
 */
public class User implements DataObjectInterface {
    private String id;
    private String account = null;
    private String passWord;
    private String nickName;
    private String img;
    private Bitmap bitmap = null;
    private double money = 0;
    private static User instance = null;

    protected User(){

    }
    public String getAccount(){
        return account;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseTable.UserDataTable.ID,id);
        contentValues.put(DataBaseTable.UserDataTable.PASSWORD,passWord);
        contentValues.put(DataBaseTable.UserDataTable.NICKNAME,nickName);
        contentValues.put(DataBaseTable.UserDataTable.MONEY,money);
        contentValues.put(DataBaseTable.UserDataTable.IMG,img);
        return contentValues;
    }

    public void getDataFromDatabase(Map<String,String> map){
        id = map.get(DataBaseTable.UserDataTable.ID);
        passWord = map.get(DataBaseTable.UserDataTable.PASSWORD);
        nickName = map.get(DataBaseTable.UserDataTable.NICKNAME);
        money = Double.parseDouble(map.get(DataBaseTable.UserDataTable.MONEY));
        img = map.get(DataBaseTable.UserDataTable.IMG);
    }

    public static User dealJsonData(String data){
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(data);
            user.id = jsonObject.getString("id");
            user.nickName = jsonObject.getString("nickName");
            user.passWord = jsonObject.getString("password");
            user.img = jsonObject.getString("bitmap");
            user.money = Double.parseDouble(jsonObject.getString("money"));
        } catch (JSONException e) {
            e.printStackTrace();
            user = null;
        }
        return user;
    }
}
