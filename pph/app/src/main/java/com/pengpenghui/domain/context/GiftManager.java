package com.pengpenghui.domain.context;

import com.pengpenghui.domain.entity.HttpApi;
import com.pengpenghui.domain.service.database.DataBaseOperator;
import com.pengpenghui.domain.service.http.HttpListener;
import com.pengpenghui.domain.service.http.HttpRequest;


/**
 * Created by Zoson on 15/11/20.
 */
public class GiftManager extends PPHContext{
    private HttpRequest httpService;
    private DataBaseOperator dataBaseOperator;
    public GiftManager(){
        this.httpService = getHttpService();
        dataBaseOperator = getDataBaseOperator();
    }

    public void bindWx(String code, final ContextCallback callback){
        HttpApi.insertVerifyCode(httpService,getDataProvider().getUser().getId(), code, new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
                callback.response(ContextCallback.DEFAULT,msg);
            }

            @Override
            public void failToRequire(String msg, String data) {
                callback.response(ContextCallback.FAIL,msg);
            }

            @Override
            public void netWorkError(String msg, String data) {
                callback.response(ContextCallback.ERROR,msg);
            }
        });
    }

    public double getGiftCount(final ContextCallback contextCallback){
        HttpApi.getGiftSum(httpService,getDataProvider().getUser().getId(), new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.DEFAULT,data);
            }

            @Override
            public void failToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL,data);
            }

            @Override
            public void netWorkError(String msg, String data) {
                contextCallback.response(ContextCallback.ERROR,msg);
            }
        });
        return 0.0;
    }
}
