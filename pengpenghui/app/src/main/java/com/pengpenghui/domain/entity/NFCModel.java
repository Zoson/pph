package com.pengpenghui.domain.entity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Parcelable;


import com.pengpenghui.pph_interface.NFCListener;

import java.io.UnsupportedEncodingException;

/**
 * Created by zoson on 6/17/15.
 */
public class NFCModel {
    String code;
    private NfcAdapter nfcAdapter;
    private IntentFilter[] mFilters;
    private boolean isFirst = true;
    private IntentFilter ndef;
    private String[][] mTechLists;
    private Activity activity;
    private NFCListener nfcListener;
    private PendingIntent pendingIntent;
    public int ifNFCUse(){
        if (nfcAdapter == null){
            return -1;
        }else if (!nfcAdapter.isEnabled()){
            return 0;
        }
        return 1;
    }
    public NFCModel(Activity activity,NFCListener nfcListener){
        this.activity = activity;
        this.nfcListener = nfcListener;
        init();
    }

    protected void init() {
        pendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity.getApplicationContext(),activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[] { ndef };
        mTechLists = new String[][] { new String[] { NfcA.class.getName() },
                new String[] { NfcF.class.getName() },
                new String[] { NfcB.class.getName() },
                new String[] { NfcV.class.getName() } };
        if (isFirst) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(activity.getIntent()
                    .getAction())) {
                if (readFromTag(activity.getIntent())) {
                    nfcListener.getNfcInfo(code);
                } else {
                    nfcListener.getNfcInfo("-1");
                }
            }
            isFirst = false;
        }
    }
    public boolean readFromTag(Intent intent) {
        Parcelable[] rawArray = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawArray != null) {
            NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
            NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
            try {
                if (mNdefRecord != null) {
                    code = new String(mNdefRecord.getPayload(), "UTF-8");
                    return true;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }
    public void endbleForegroundDispatch(){
        nfcAdapter.enableForegroundDispatch(activity,pendingIntent,mFilters,mTechLists);
    }
    public void disableForegroundDispatch(){
        nfcAdapter.disableForegroundDispatch(activity);
    }
    public void getNfcIntent(Intent intent){
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            if (readFromTag(intent)) {
                nfcListener.getNfcInfo(code);
            } else {
                nfcListener.getNfcInfo("-1");
            }
        }
    }

}
