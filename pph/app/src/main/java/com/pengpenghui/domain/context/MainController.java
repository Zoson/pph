package com.pengpenghui.domain.context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;

import com.pengpenghui.domain.entity.AdData;
import com.pengpenghui.domain.entity.BroMessage;
import com.pengpenghui.domain.entity.DataBaseTable;
import com.pengpenghui.domain.entity.DataProvider;
import com.pengpenghui.domain.entity.HttpApi;
import com.pengpenghui.domain.entity.User;
import com.pengpenghui.domain.service.database.DataBaseOperator;
import com.pengpenghui.domain.service.http.HttpFileListener;
import com.pengpenghui.domain.service.http.HttpListener;
import com.pengpenghui.domain.service.SharedPreference;

import com.pengpenghui.domain.service.nfc.NFCListener;
import com.pengpenghui.domain.service.nfc.NFCService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zoson on 7/21/15.
 */
public class MainController extends PPHContext {
    private User user;
    private List<BroMessage> broMessages;
    private DataBaseOperator dataBaseOperator;
    private SharedPreference sharedPreference;
    private NFCService nfcService;
    private DataProvider contextData;
    private AdData adData;

    private static MainController instance = new MainController();
    public static MainController get(){
        return instance;
    }
    protected MainController(){
        this.user = getDataProvider().getUser();
        broMessages  = getDataProvider().getBroMessages();
        dataBaseOperator = getDataBaseOperator();
        sharedPreference = getSharePreference();
        nfcService = getNFCService();
        contextData = getDataProvider();

        insertAttention(80,null);
        insertAttention(79,null);
        insertAttention(81,null);
    }

    public List<BroMessage> getBroList(){
        return broMessages;
    }

    public void deleteAttention(int index, final ContextCallback callback){
        final List<AdData> list = getDataProvider().getAdDatas();
        AdData adData = list.get(index);
        list.remove(index);
        HttpApi.deleteAttention(user.getId(), adData.getAdId(), new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                callback.response(ContextCallback.SUCC, "删除成功");
            }

            @Override
            public void fail(String message) {
            }
        });
    }

    public User getUser(){
        return getDataProvider().getUser();
    }

    public AdData getCurrentAd(){
        return contextData.getCurrentAdData();
    }

    private List<BroMessage> genBroList(String data){
        return contextData.genBroMessagesByJson(data);
    }

    public void getBro(final ContextCallback contextCallback){
        User user  = getDataProvider().getUser();
        if (user == null)return;
        HttpApi.ownsToGetDis(user.getId(), new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                contextCallback.response(ContextCallback.SUCC, genBroList(data));
            }

            @Override
            public void fail(String message) {
                contextCallback.response(ContextCallback.FAIL, message);
            }

        });
    }

    public void getBroByAd(final ContextCallback contextCallback){
        User user  = getDataProvider().getUser();
        if (user == null)return;
        HttpApi.getDis(user.getId(), contextData.getCurrentAdData().getAdId(), new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                contextCallback.response(ContextCallback.SUCC, data);
            }

            @Override
            public void fail(String message) {
                contextCallback.response(ContextCallback.SUCC, message);
            }
        });
    }

    private List<String> getAllBroIdFromDB(){
        String[] cols = {DataBaseTable.BroMessageTable.DISID};
        ArrayList<Map<String,String>> list_map = dataBaseOperator.query(DataBaseTable.BroMessageTable.BROMESSAGE_TABLE_NAME, cols, null, null);
        ArrayList<String> list = new ArrayList<String>();
        for (int i=0;i<list_map.size();i++){
            list.add(list_map.get(i).get(DataBaseTable.BroMessageTable.DISID));
        }
        return list;
    }

    public void changePassword(String oldps, final String newps, final ContextCallback contextCallback){
        final User user  = getDataProvider().getUser();
        if (user == null)return;
        HttpApi.setPsw(user.getId(), oldps, newps, new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                user.setPassWord(newps);
                contextCallback.response(ContextCallback.SUCC, message);
            }

            @Override
            public void fail(String message) {
                contextCallback.response(ContextCallback.FAIL, message);
            }
        });
    }

    public void changeName(String name, final ContextCallback contextCallback){
        final User user  = getDataProvider().getUser();
        if (user == null)return;
        HttpApi.setNickName(user.getId(), name, new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                try {
                    JSONObject js = new JSONObject(data);
                    user.setNickName(js.getString("newNickName"));
                    contextCallback.response(ContextCallback.SUCC, user.getNickName());
                } catch (JSONException e) {
                    e.printStackTrace();
                    contextCallback.response(ContextCallback.FAIL, "数据处理出错");
                }
            }

            @Override
            public void fail(String message) {
                contextCallback.response(ContextCallback.FAIL, message);
            }
        });
    }

    public void changPicture(String file){
        sharedPreference.set("file", file);
        HttpApi.setPicture(user.getId(), file, new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                System.out.println("changPicture "+message+" data "+data);
            }

            @Override
            public void fail(String message) {

            }
        });
    }

    public boolean isUserLogin(){
        return (getDataProvider().getUserState()==DataProvider.LOGIN)?true:false;
    }

    public void exit(){
        getDataProvider().setUserState(DataProvider.UNLOGIN);
    }

    public void handleNFCEvent(Intent intent,final ContextCallback contextCallback){
        if (!NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
            return;
        }
        if (!checkNFCDevice()){
            contextCallback.response(ContextCallback.FAIL,"您的手机不支持NFC");
            return;
        }
        nfcService.getNfcIntent(intent, new NFCListener() {
            @Override
            public void getNfcInfo(String code) {
                HttpApi.tagToGetAd(code, new HttpListener() {
                    @Override
                    public void succ(String message, String data, byte[] bytes) {
                        if (contextData.addAdDataByJson(data)) {
                            adData = contextData.getAdDatas().get(0);
                            contextCallback.response(ContextCallback.SUCC, adData);
                        } else {
                            contextCallback.response(ContextCallback.SUCC, "数据处理出错");
                        }
                    }

                    @Override
                    public void fail(String message) {
                        contextCallback.response(ContextCallback.FAIL, message);
                    }
                });
            }
        });
    }

    public boolean checkNFCDevice(){
        contextData.setIsNFCEnable(nfcService.checkNFCFunction());
        return contextData.isNFCEnable();
    }

    public List<AdData> getAttention(final ContextCallback callback){
        User user  = getDataProvider().getUser();
        final List<AdData> adDatas = getDataProvider().getAdDatas();
        if (user == null)return adDatas;
        HttpApi.getAttention(user.getId(), new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {
                try {
                    JSONArray jsonObject = new JSONArray(data);
                    for (int i = adDatas.size(); i < jsonObject.length(); i++) {
                        String json = jsonObject.getString(i);
                        AdData adData = new AdData();
                        adData.initByJson(json);
                        adDatas.add(adData);
                        getAdPicture(adData,callback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.response(ContextCallback.SUCC, "获取成功");
            }

            @Override
            public void fail(String message) {
                callback.response(ContextCallback.SUCC, message);
            }
        });
        return adDatas;
    }

    public void insertAttention(int id,ContextCallback callback){
        User user  = getDataProvider().getUser();
        if (user == null)return;
        HttpApi.insertAttention(user.getId(), id, new HttpListener() {
            @Override
            public void succ(String message, String data, byte[] bytes) {

            }

            @Override
            public void fail(String message) {

            }
        });
    }

    public void getAdPicture(final AdData adData , final ContextCallback callback) {
        User user  = getDataProvider().getUser();
        if (user == null)return;
        if (adData.getAdBitmap()!=null)return;
        HttpApi.getFile(adData.getAd_picture_url(), new HttpFileListener() {
            @Override
            public void succ(String message, byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                adData.setAdPicture(bitmap);
                callback.response(ContextCallback.SUCC, message);
            }

            @Override
            public void fail(String message) {

            }
        });
    }
}
