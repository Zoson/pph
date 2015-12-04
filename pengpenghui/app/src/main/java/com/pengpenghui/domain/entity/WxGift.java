package com.pengpenghui.domain.entity;

/**
 * Created by Zoson on 15/11/27.
 */
public class WxGift {
    private boolean isbind;
    private String id;
    private String verifyCode;
    private String gift_sum;
    public WxGift(){

    }
    public boolean isbind() {
        return isbind;
    }

    public void setIsbind(boolean isbind) {
        this.isbind = isbind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getGift_sum() {
        return gift_sum;
    }

    public void setGift_sum(String gift_sum) {
        this.gift_sum = gift_sum;
    }
}
