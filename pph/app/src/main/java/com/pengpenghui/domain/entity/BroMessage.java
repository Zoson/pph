package com.pengpenghui.domain.entity;

import android.content.ContentValues;

import com.druson.cycle.enity.Enity;
import com.pengpenghui.domain.service.database.DataObjectInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zoson on 6/17/15.
 */
public class BroMessage extends Enity implements DataObjectInterface {
    private long disId;
    private long AdId;
    private int disMoney;
    private String storeName;
    private String name;

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    private String beginDate;
    private String endDate;
    private String type;

    public long getAdid() {
        return AdId;
    }

    public void setAdid(long adid) {
        this.AdId = adid;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
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
        contentValues.put(DataBaseTable.BroMessageTable.BEGINDATA,beginDate);
        contentValues.put(DataBaseTable.BroMessageTable.ENDDATE,endDate);
        contentValues.put(DataBaseTable.BroMessageTable.STORENAME,storeName);
        contentValues.put(DataBaseTable.BroMessageTable.DISID,disId);
        contentValues.put(DataBaseTable.BroMessageTable.DISMONEY,disMoney);
        contentValues.put(DataBaseTable.BroMessageTable.NAME,name);
        return contentValues;
    }
    public void getDataFromDatabase(Map<String,String> map){
        disId = Long.parseLong(map.get(DataBaseTable.BroMessageTable.DISID));
        disMoney = Integer.parseInt(map.get(DataBaseTable.BroMessageTable.DISMONEY));
        storeName = map.get(DataBaseTable.BroMessageTable.STORENAME);
        beginDate = map.get(DataBaseTable.BroMessageTable.BEGINDATA);
        endDate = map.get(DataBaseTable.BroMessageTable.ENDDATE);
        name = map.get(DataBaseTable.BroMessageTable.NAME);
    }

}
