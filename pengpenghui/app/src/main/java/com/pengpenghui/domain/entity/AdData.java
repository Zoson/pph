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
    private long[] dis_id ;
    private int dis_count;
    public AdData(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            ad_id = jsonObject.getLong("AdId");
            ad_url = jsonObject.getString("AdUrl");
            ad_owner = jsonObject.getString("AdOwner");
            priority = jsonObject.getInt("priority");
            ad_picture_url = jsonObject.getString("AdPicture");
            ad_info = jsonObject.getString("info");
            JSONObject jsonObject1 = jsonObject.getJSONObject("disId");
            dis_count = jsonObject1.getInt("count");
            dis_id = new long[dis_count];
            for (int i = 0;i<dis_count;i++){
                dis_id[i] = jsonObject1.getLong(""+i);
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
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

    public long[] getDis_id() {
        return dis_id;
    }

    public int getDis_count() {
        return dis_count;
    }

}
