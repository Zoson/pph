package com.pengpenghui.domain.entity;

import com.druson.cycle.service.http.Request;
import com.pengpenghui.domain.service.http.HttpListener;
import com.pengpenghui.domain.service.http.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/5/23.
 */
public class HttpApi {

    private static String Address = "http://182.92.100.145/Touch/index.php/Home/Enter";
    private static final String register = "register";
    private static final String log = "log";
    private static final String setPsw = "setPsw";
    private static final String setNickName = "setNickName";
    private static final String setPicture = "setPicture";
    private static final String getAd = "getAd";
    private static final String getDisMessage = "getDisMessage";
    private static final String getDis = "getDis";
    private static final String ownsToGetDis = "ownsToGetDis";
    private static final String deleteOwns = "deleteOwns";
    private static final String tagToGetAd = "tagToGetAd";
    private static final String insertAttention = "insertAttention";
    private static final String deleteAttention = "deleteAttention";
    private static final String getAttention = "getAttention";
    private static final String insertVerityCode = "insertVerityCode";
    private static final String isNotBind = "isNotBind";
    private static final String getMoney = "getMoney";
    private static final String getGiftSum = "getGiftSum";

    private static HttpService httpService = new HttpService();
    public static void register(User user,HttpListener httpListener){
        if (user == null)return;
        Request request = new Request();
        request.putParams("data",user.toJsonString());
        request.api = register;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void log(User user,HttpListener httpListener){
        if (user == null)return;
        Request request = new Request();
        request.putParams("data",user.toJsonString());
        request.api = log;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void setPsw(String id,String oldps ,String newps ,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("password",oldps);
            jsonObject.put("newPsw",newps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.putParams("data",jsonObject.toString());
        request.api = setPsw;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void setNickName(String id ,String nickName,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("newNickName",nickName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.putParams("data",jsonObject.toString());
        request.api = setNickName;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void setPicture(String id,String filepath,HttpListener httpListener){
//        if (filepath == null)return;
//        Request request = new Request();
//        JSONObject jsonObject = new JSONObject();
//        //jsonObject.put("bitmap",filepath)
//        //request.putParams("data",user.toJsonString());
//        request.api = setPicture;
//        request.url = Address;
//        httpService.sendPost(request,httpListener);
    }

    public static void getAd(String AdId,HttpListener httpListener){
        if (AdId == null)return;
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("AdId",AdId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.putParams("data",jsonObject.toString());
        request.api = getAd;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void getDisMessage(String disId,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("disId",disId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.putParams("data", jsonObject.toString());
        request.api = getDisMessage;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void getDis(String id,int disId,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("disId",disId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = getDis;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void ownsToGetDis(String id,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = ownsToGetDis;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void deleteOwns(String id,String disId,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("disId",disId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = deleteOwns;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void tagToGetAd(String tag,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tag",tag);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = ownsToGetDis;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void insertAttention(String id,String AdId,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("AdId",AdId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = insertAttention;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void deleteAttention(String id,String AdId,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("AdId",AdId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = deleteAttention;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void getAttention(String id,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = getAttention;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void insertVerifyCode(String id,String verifyCode,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("verifyCode",verifyCode);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = insertVerityCode;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void isNotBind(String id,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = isNotBind;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void getMoney(String id,double money,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("money",money);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = getMoney;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

    public static void getGiftSum(String id,HttpListener httpListener){
        Request request = new Request();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        request.putParams("data",jsonObject.toString());
        request.api = getGiftSum;
        request.url = Address;
        httpService.sendPost(request,httpListener);
    }

}
