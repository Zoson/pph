package com.pengpenghui.domain.context;

import com.pengpenghui.domain.entity.HttpApi;
import com.pengpenghui.domain.entity.User;
import com.pengpenghui.domain.service.database.DataBaseOperator;
import com.pengpenghui.domain.service.http.HttpListener;


/**
 * Created by Zoson on 15/11/20.
 */
public class GiftManager extends PPHContext{
    private DataBaseOperator dataBaseOperator;

    private static GiftManager instance = new GiftManager();
    public static GiftManager get(){
        return instance;
    }
    public GiftManager(){
        dataBaseOperator = getDataBaseOperator();
    }

    public void bindWx(String code, final ContextCallback callback){
        HttpApi.insertVerifyCode(getDataProvider().getUser().getId(), code, new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                callback.response(ContextCallback.SUCC,message);
            }

            @Override
            public void fail(String message) {
                callback.response(ContextCallback.FAIL,message);
            }
        });
    }

    public double getGiftCount(final ContextCallback callback){
        HttpApi.getGiftSum(getDataProvider().getUser().getId(), new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                callback.response(ContextCallback.SUCC, data);
            }

            @Override
            public void fail(String message) {
                callback.response(ContextCallback.SUCC, message);
            }
        });
        return 0.0;
    }

    public void getMoney(final ContextCallback callback){
        User user = getDataProvider().getUser();
        double money = getDataProvider().getUser().getMoney();
        if (money == 0.0){
            callback.response(ContextCallback.FAIL,"余额为0,无法提现");
        }else{
            HttpApi.getMoney(user.getId(), money, new HttpListener() {
                @Override
                public void succ(String message, String data, byte[] bytes) {
                    callback.response(ContextCallback.SUCC,message);
                }

                @Override
                public void fail(String message) {
                    callback.response(ContextCallback.FAIL,"提现失败");
                }
            });
        }
    }
}
