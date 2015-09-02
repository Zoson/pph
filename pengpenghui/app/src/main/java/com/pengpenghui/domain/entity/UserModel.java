package com.pengpenghui.domain.entity;

import android.content.ContentValues;
import android.graphics.Bitmap;

import com.pengpenghui.pph_interface.DataObjectInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by 杨超chao on 2015/6/17.
 */
public class UserModel implements DataObjectInterface {
    private String id;
    private String account;
    private String passWord;
    private String nickName;
    private String img;
    private Bitmap bitmap = null;
    private double money = 0;
    private static UserModel instance = null;

    public static UserModel getInstance() {
        if (instance == null) {
            instance = new UserModel();
            return instance;
        }
        return instance;
    }
    protected UserModel(){

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

    public void dealJsonData(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            id = jsonObject.getString("id");
            nickName = jsonObject.getString("nickName");
            passWord = jsonObject.getString("password");
            img = jsonObject.getString("bitmap");
            money = Double.parseDouble(jsonObject.getString("money"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
