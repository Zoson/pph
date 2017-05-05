package com.pengpenghui.domain.entity;

import android.graphics.Bitmap;

import com.druson.cycle.enity.Enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zoson on 6/17/15.
 */
public class AdData extends Enity {
    private int AdId;
    private String AdUrl;
    private int disID;

    public void setAdPicture(Bitmap adPicture) {
        this.adPicture = adPicture;
    }

    private Bitmap adPicture;

    public void setDisID(int disID) {

        this.disID = disID;
    }

    public int getDisID() {

        return this.disID;
    }

    public Bitmap getAdBitmap(){
        return adPicture;
    }


    public int getAdId() {
        return AdId;
    }

    public void setAdId(int adId) {
        AdId = adId;
    }

    public String getAdUrl() {
        return AdUrl;
    }

    public void setAdUrl(String adUrl) {
        AdUrl = adUrl;
    }

    public String getAdOwner() {
        return AdOwner;
    }

    public void setAdOwner(String adOwner) {
        AdOwner = adOwner;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getAdPicture() {
        return AdPicture;
    }

    public void setAdPicture(String adPicture) {
        AdPicture = adPicture;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String AdOwner;
    private int priority;
    private String AdPicture;
    private String info;
    public AdData(){

    }

    public int getAd_id() {
        return AdId;
    }

    public String getAd_url() {
        return AdUrl;
    }

    public String getAd_owner() {
        return AdOwner;
    }

    public int getPriority() {
        return priority;
    }

    public String getAd_picture_url() {
        return AdPicture;
    }


}
