package com.pengpenghui.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.nfc.NfcAdapter;
import android.os.IBinder;

/**
 * Created by Zoson on 16/1/12.
 */
public class RecIntentService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case ConnectivityManager.CONNECTIVITY_ACTION:
                break;
            case NfcAdapter.ACTION_NDEF_DISCOVERED:
                break;

        }
    }
}
