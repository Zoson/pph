package com.pengpenghui.domain.context;

import android.content.Intent;
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
import com.pengpenghui.domain.service.http.HttpRequest;
import com.pengpenghui.domain.service.nfc.NFCListener;
import com.pengpenghui.domain.service.nfc.NFCService;

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
    private HttpRequest httpRequest;
    private NFCService nfcService;
    private DataProvider contextData;
    private AdData adData;
    public MainController(){
        this.user = getDataProvider().getUser();
        broMessages  = getDataProvider().getBroMessages();
        dataBaseOperator = getDataBaseOperator();
        httpRequest = getHttpService();
        sharedPreference = getSharePreference();
        nfcService = getNFCService();
        contextData = getDataProvider();
    }

    public List<BroMessage> getBroList(){
        return broMessages;
    }

    public User getUser(){
        return user;
    }

    public AdData getCurrentAd(){
        return contextData.getCurrentAdData();
    }

    private List<BroMessage> genBroList(String data){
        return contextData.genBroMessagesByJson(data);
    }

    public void getBro(final ContextCallback contextCallback){
        HttpApi.ownsToGetDis(httpRequest, user.getId(), new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.SUCC, genBroList(data));
            }

            @Override
            public void failToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "获取失败");
            }

            @Override
            public void netWorkError(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "网络错误");
            }
        });
    }

    public void getBroByAd(final ContextCallback contextCallback){

        HttpApi.insertOwns(httpRequest, user.getId(), contextData.getCurrentAdData().getDis_id() + "", new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.SUCC, data);
            }

            @Override
            public void failToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "领取失败");
            }

            @Override
            public void netWorkError(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "网络出错");
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

    public void changePassword(String oldps,String newps, final ContextCallback contextCallback){
        HttpApi.setPsw(httpRequest, user.getId(), oldps, newps, new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.SUCC, "修改成功");
            }

            @Override
            public void failToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "修改失败");
            }

            @Override
            public void netWorkError(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "网络出错");
            }
        });
    }

    public void changeName(String name, final ContextCallback contextCallback){
        HttpApi.setNickName(httpRequest, user.getId(), name, new HttpListener() {
            @Override
            public void succToRequire(String msg, String data) {
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
            public void failToRequire(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "修改失败");
            }

            @Override
            public void netWorkError(String msg, String data) {
                contextCallback.response(ContextCallback.FAIL, "网络出错");
            }
        });
    }

    public void changPicture(String file){
        sharedPreference.set("file", file);
        HttpApi.setPicture(httpRequest, user.getId(), file, new HttpFileListener() {
            @Override
            public void succToFile() {

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
                HttpApi.tagToGetAd(httpRequest, code, new HttpListener() {
                    @Override
                    public void succToRequire(String msg, String data) {
                        if (contextData.addAdDataByJson(data)) {
                            adData = contextData.getAdDatas().get(0);
                            contextCallback.response(ContextCallback.SUCC, adData);
                        } else {
                            contextCallback.response(ContextCallback.SUCC, "数据处理出错");
                        }
                    }

                    @Override
                    public void failToRequire(String msg, String data) {
                        contextCallback.response(ContextCallback.FAIL, "广告获取失败");
                    }

                    @Override
                    public void netWorkError(String msg, String data) {
                        contextCallback.response(ContextCallback.FAIL, "广告获取失败");
                    }
                });
            }
        });
    }

    public boolean checkNFCDevice(){
        contextData.setIsNFCEnable(nfcService.checkNFCFunction());
        return contextData.isNFCEnable();

    }
}
