package com.pengpenghui.domain.context;

import android.content.Context;

import com.pengpenghui.domain.entity.DataProvider;
import com.pengpenghui.domain.service.database.DataBaseOperator;
import com.pengpenghui.domain.service.NetWorkState;
import com.pengpenghui.domain.service.ServiceManager;
import com.pengpenghui.domain.service.SharedPreference;
import com.pengpenghui.domain.service.nfc.NFCService;

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
