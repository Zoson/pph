package com.pengpenghui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.nfc.NfcAdapter;
import android.os.IBinder;
import android.widget.Toast;

import com.pengpenghui.domain.context.MainController;

/**
 * Created by Zoson on 16/1/12.
 */
public class RecIntentService extends BroadcastReceiver {

    MainController mainController;
    @Override
    public void onReceive(Context context, Intent intent) {
        initData();
        System.out.println("rec ++++++++++++++++++++++=");
        String action = intent.getAction();
        switch (action){
            case ConnectivityManager.CONNECTIVITY_ACTION:
                break;
            case NfcAdapter.ACTION_NDEF_DISCOVERED:

                break;

        }
    }

    private void initData(){
        mainController = new MainController();

    }
}
