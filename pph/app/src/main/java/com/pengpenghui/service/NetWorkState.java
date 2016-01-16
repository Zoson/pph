package com.pengpenghui.service;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoson on 15/8/29.
 */
public class NetWorkState{
    private List<Observer> observers;
    private Context context;
    private int state = 0;
    /*
        还没定义几个状态
     */
    public NetWorkState(Context context){
        this.context = context;
        observers = new ArrayList<Observer>();
    }
    public  boolean isConnecting(){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            return true;
        }else{
            return false;
        }
    }
    public boolean isWifiOn(){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return wifi;
    }

    public boolean isMobileNetworkOn(){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return internet;
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void remoteObserver(Observer observer){
        int i = observers.indexOf(observer);
        observers.remove(i);
    }

    public void notifyObserver(){
        for(int i=0;i<observers.size();i++){
            observers.get(i).responseState(state);
        }
    }

    public interface Observer{
        public void responseState(int state);
    }
}
