package com.pengpenghui.domain.entity;

import android.content.ContentValues;

import com.pengpenghui.domain.service.database.DataObjectInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zoson on 6/17/15.
 */
public class BroMessage implements DataObjectInterface {
    private String userId;
    private long disId;
    private long adid;
    private int disMoney;
    private String storeName;
    private String name;
    private Duration duration;

    public long getAdid() {
        return adid;
    }

    public void setAdid(long adid) {
        this.adid = adid;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public Duration getDuration(){
        return duration;
    }

    public void setDuration(String begin,String end){
        this.duration = new Duration(begin,end);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getDisId() {
        return disId;
    }

    public void setDisId(long disId) {
        this.disId = disId;
    }

    public int getDisMoney() {
        return disMoney;
    }

    public void setDisMoney(int disMoney) {
        this.disMoney = disMoney;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        //contentValues.put(DataBaseTable.UserDataTable.ID, User.getInstance().getId());
        contentValues.put(DataBaseTable.BroMessageTable.BEGINDATA,duration.toString_beginDate());
        contentValues.put(DataBaseTable.BroMessageTable.ENDDATE,duration.toString_endDate());
        contentValues.put(DataBaseTable.BroMessageTable.STORENAME,storeName);
        contentValues.put(DataBaseTable.BroMessageTable.DISID,disId);
        contentValues.put(DataBaseTable.BroMessageTable.DISMONEY,disMoney);
        contentValues.put(DataBaseTable.BroMessageTable.NAME,name);
        return contentValues;
    }
    public void getDataFromDatabase(Map<String,String> map){
        disId = Long.parseLong(map.get(DataBaseTable.BroMessageTable.DISID));
        userId = (map.get(DataBaseTable.UserDataTable.ID));
        disMoney = Integer.parseInt(map.get(DataBaseTable.BroMessageTable.DISMONEY));
        storeName = map.get(DataBaseTable.BroMessageTable.STORENAME);
        duration.setBegin(map.get(DataBaseTable.BroMessageTable.BEGINDATA));
        duration.setEnd(map.get(DataBaseTable.BroMessageTable.ENDDATE));
        name = map.get(DataBaseTable.BroMessageTable.NAME);
    }
    public static BroMessage genByJsonData(String userId,String data){
        BroMessage broMessage = new BroMessage();
        JSONObject jsonObject1 = null;
        try {
            jsonObject1 = new JSONObject(data);
            broMessage.disId = jsonObject1.getLong("disId");
            broMessage.disMoney = jsonObject1.getInt("disMoney");
            broMessage.storeName = jsonObject1.getString("storeName");
            broMessage.name = jsonObject1.getString("name");
            broMessage.userId = userId;
            broMessage.duration = new Duration(jsonObject1.getString("beginDate"),jsonObject1.getString("endDate"));
        } catch (JSONException e) {
            e.printStackTrace();
            broMessage = null;
            return broMessage;
        }
        return broMessage;
    }

}
