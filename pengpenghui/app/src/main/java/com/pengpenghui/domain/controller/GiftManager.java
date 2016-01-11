package com.pengpenghui.domain.controller;

import android.content.Context;

import com.pengpenghui.domain.entity.HttpApi;
import com.pengpenghui.domain.entity.UserModel;
import com.pengpenghui.domain.entity.WxGift;
import com.pengpenghui.pph_interface.HttpListener;
import com.pengpenghui.pph_interface.ViewInterface;
import com.pengpenghui.service.HttpService;

/**
 * Created by Zoson on 15/11/20.
 */
public class GiftManager {
    private ViewInterface viewInterface;
    private Context context;
    private HttpService httpService;
    private UserModel userModel;
    private WxGift wxGift;
    private DataBaseOperator dataBaseOperator;
    public GiftManager(Context context,ViewInterface viewInterface){
        this.viewInterface = viewInterface;
        this.context = context;
        this.httpService = new HttpService();
        userModel = userModel.getInstance();
        dataBaseOperator = new DataBaseOperator(context);
        //wxGift = new WxGift();
    }

    public void bindWx(String code){
        HttpApi.insertVerifyCode(httpService, userModel.getId(),code);
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(String message, String data) {
                viewInterface.requestSuccessfully("bindWx",data);
            }

            @Override
            public void failToRequired(String message, String data) {
                viewInterface.requestUnSuccessfully("bindWx",data);
            }

            @Override
            public void netWorkError() {
                viewInterface.requestError("bindWx",null);
            }
        });
    }

    public double getGiftCount(){
        HttpApi.getGiftSum(httpService,userModel.getId());
        httpService.setListener(new HttpListener() {
            @Override
            public void succToRequired(String message, String data) {
                viewInterface.requestSuccessfully("getGiftCount",data);
            }

            @Override
            public void failToRequired(String message, String data) {
                viewInterface.requestUnSuccessfully("getGiftCount",data);
            }

            @Override
            public void netWorkError() {
                viewInterface.requestError("getGiftCount",null);
            }
        });
        return 0.0;
    }

}
