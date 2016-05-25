package com.pengpenghui.domain.entity;

import android.content.ContentValues;
import android.graphics.Bitmap;

import com.druson.cycle.enity.Enity;
import com.pengpenghui.domain.service.database.DataObjectInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by 杨超chao on 2015/6/17.
 */
public class User extends Enity implements DataObjectInterface {
    private String id;
    private String password;
    private String nickName;
    private String bitmap;
    private Bitmap img = null;
    private double money = 0;
    private static User instance = null;

    public User(){

    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassWord() {
        return password;
    }

    public void setPassWord(String passWord) {
        this.password = passWord;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return bitmap;
    }

    public void setImg(String img) {
        this.bitmap= img;
    }


    public Bitmap getBitmap() {
        return img;
    }

    public void setBitmap(Bitmap bitmap) {
        this.img = bitmap;
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
        contentValues.put(DataBaseTable.UserDataTable.PASSWORD,password);
        contentValues.put(DataBaseTable.UserDataTable.NICKNAME,nickName);
        contentValues.put(DataBaseTable.UserDataTable.MONEY,money);
        contentValues.put(DataBaseTable.UserDataTable.IMG,bitmap);
        return contentValues;
    }

    public void getDataFromDatabase(Map<String,String> map){
        id = map.get(DataBaseTable.UserDataTable.ID);
        password = map.get(DataBaseTable.UserDataTable.PASSWORD);
        nickName = map.get(DataBaseTable.UserDataTable.NICKNAME);
        money = Double.parseDouble(map.get(DataBaseTable.UserDataTable.MONEY));
        bitmap = map.get(DataBaseTable.UserDataTable.IMG);
    }

}
