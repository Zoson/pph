package com.pengpenghui.domain.context;

import android.content.Context;

import com.pengpenghui.domain.entity.DataProvider;
import com.pengpenghui.service.database.DataBaseOperator;
import com.pengpenghui.service.http.HttpRequest;
import com.pengpenghui.service.nfc.NFCService;
import com.pengpenghui.service.NetWorkState;
import com.pengpenghui.service.ServiceManager;
import com.pengpenghui.service.SharedPreference;

/**
 * Created by Zoson on 16/1/12.
 */
public class PPHContext {

    public static PPHContext BaseContext;
    public Context context;
    private DataProvider mDataProvider;
    private ServiceManager mServiceManager;

    public PPHContext(){

    }
    public PPHContext(Context context){
        this.context = context;
        mDataProvider = new DataProvider();
        mServiceManager = new ServiceManager(context);
    }

    public DataBaseOperator getDataBaseOperator(){
        return (DataBaseOperator)BaseContext.mServiceManager.getService(ServiceManager.Service.DATABASE);
    }

    public HttpRequest getHttpService(){
        return (HttpRequest)BaseContext.mServiceManager.getService(ServiceManager.Service.HTTP);
    }

    public NetWorkState getNetWorkState(){
        return (NetWorkState)BaseContext.mServiceManager.getService(ServiceManager.Service.NETWORDSTATE);
    }

    public SharedPreference getSharePreference(){
        return (SharedPreference)BaseContext.mServiceManager.getService(ServiceManager.Service.SHAREDPREFERENCE);
    }

    public NFCService getNFCService(){
        return (NFCService)BaseContext.mServiceManager.getService(ServiceManager.Service.NFC);
    }

    public DataProvider getDataProvider() {
        return BaseContext.mDataProvider;
    }
}
