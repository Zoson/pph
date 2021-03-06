package com.pengpenghui.domain.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zoson on 16/1/12.
 */
public class DataProvider {
    private int userState = UNLOGIN;
    public final static int UNLOGIN = 0;
    public final static int LOGIN = 1;
    private boolean isNFCEnable = false;
    private User user;
    private List<AdData> adDatas;
    private AdData current_ad;
    private List<BroMessage> broMessages;
    private List<WxGift> gifts;

    public DataProvider(){
        adDatas = new ArrayList<>();
        broMessages = new ArrayList<>();
        gifts = new ArrayList<>();
    }
    public User genUserByJson(String data){
        User user = new User();
        try {
            user.initByJson(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public List<AdData> getAdDatas(){
        return adDatas;
    }

    public List<BroMessage> getBroMessages(){
        return broMessages;
    }

    public void addBroMessage(BroMessage broMessage){
        this.broMessages.add(broMessage);
    }

    public List<WxGift> getGifts(){
        return gifts;
    }

    public void addGifs(WxGift wxGift){
        gifts.add(wxGift);
    }

    public AdData getCurrentAdData(){
        return current_ad;
    }

    public void setUserState(int state){
        this.userState = state;
    }

    public int getUserState(){
        return userState;
    }

    public boolean addAdDataByJson(String json){
        AdData adData = new AdData();
        try {
            adData.initByJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (adData == null) return false;
        current_ad = adData;
        this.adDatas.add(adData);
        return true;
    }

    public void addBroMessage(Map<String,String> map){

    }

    public List<BroMessage> genBroMessages(List<Map<String,String>> list_map){

        return broMessages;
    }

    public List<BroMessage> genBroMessagesByJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            int count = jsonObject.getInt("count");
            for (int i =broMessages.size() ; i < count ; i++){
                BroMessage broMessage = new BroMessage();
                broMessage.initByJson(jsonObject.getString("" + i));
                if (broMessage == null) continue;
                broMessages.add(broMessage);
            }
        }catch (JSONException e){
            e.printStackTrace();
            broMessages.clear();
        }

        return broMessages;
    }

    public boolean isNFCEnable() {
        return isNFCEnable;
    }

    public void setIsNFCEnable(boolean isNFCEnable) {
        this.isNFCEnable = isNFCEnable;
    }
}
