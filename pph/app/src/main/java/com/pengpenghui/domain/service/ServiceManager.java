package com.pengpenghui.domain.service;

import android.content.Context;

import com.pengpenghui.domain.service.database.DataBaseOperator;
import com.pengpenghui.domain.service.nfc.NFCService;

import java.util.HashMap;

/**
 * Created by Zoson on 16/1/12.
 */
public class ServiceManager {

    private Context context;
    private HashMap<Service,Object> services;
    public ServiceManager(Context context){
        this.context = context;
        this.services = new HashMap<>();
    }

    public Object getService(Service service){
        return getService(service,null);
    }

    public Object getService(Service service,Context context){
        Object ob = services.get(service);
        if (ob == null){
            switch (service){
                case DATABASE:
                    ob = new DataBaseOperator(this.context);
                    break;
                case HTTP:
                    break;
                case NFC:
                    ob = new NFCService(this.context);
                    break;
                case NETWORDSTATE:
                    ob = new NetWorkState(this.context);
                    break;
                case SHAREDPREFERENCE:
                    ob = new SharedPreference(this.context);
                    break;
            }
        }
        return ob;
    }

    public boolean closeService(Service service){
        return true;
    }

    public enum Service{
        DATABASE,HTTP,NFC,NETWORDSTATE,SHAREDPREFERENCE
    }
}
