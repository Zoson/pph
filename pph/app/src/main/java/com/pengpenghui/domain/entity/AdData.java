package com.pengpenghui.domain.entity;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zoson on 6/17/15.
 */
public class AdData {
    private long ad_id;
    private String ad_url;
    private String ad_owner;
    private int priority;
    private String ad_picture_url;
    private Bitmap ad_picture;
    private String ad_info;
    private long dis_id ;
    private int dis_count;
    public AdData(){

    }

    public static AdData genAdataByJson(String json){
        AdData adData = new AdData();
        try {
            JSONObject jsonObject = new JSONObject(json);
            adData.ad_id = jsonObject.getLong("AdId");
            adData.ad_url = jsonObject.getString("AdUrl");
            adData.ad_owner = jsonObject.getString("AdOwner");
            adData.priority = jsonObject.getInt("priority");
            adData.ad_picture_url = jsonObject.getString("AdPicture");
            adData.ad_info = jsonObject.getString("info");
            adData.dis_id = jsonObject.getLong("disId");
        } catch (JSONException e) {
            adData = null;
            e.printStackTrace();
        }
        return adData;
    }
    public long getAd_id() {
        return ad_id;
    }

    public String getAd_url() {
        return ad_url;
    }

    public String getAd_owner() {
        return ad_owner;
    }

    public int getPriority() {
        return priority;
    }

    public String getAd_picture_url() {
        return ad_picture_url;
    }

    public Bitmap getAd_picture() {
        return ad_picture;
    }

    public String getAd_info() {
        return ad_info;
    }

    public long getDis_id() {
        return dis_id;
    }

    public int getDis_count() {
        return dis_count;
    }

}
