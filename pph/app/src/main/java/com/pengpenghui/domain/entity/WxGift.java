package com.pengpenghui.domain.entity;

import com.druson.cycle.enity.Enity;

/**
 * Created by Zoson on 15/11/27.
 */
public class WxGift extends Enity{
    private int giftId;
    private String id;
    private String time;
    private int Money;

    public static WxGift instance = new WxGift();
    public static WxGift getInstance(){
        return instance;
    }

    protected WxGift(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime(){
        return time;
    }

    public int getGift_sum() {
        return Money;
    }

    public void setGift_sum(int gift_sum) {
        this.Money = gift_sum;
    }

}
