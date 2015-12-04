package com.pengpenghui.domain.entity;

import com.pengpenghui.service.HttpService;

/**
 * Created by zoson on 6/19/15.
 */
public class HttpApi {

    private final static String isSuited = "isSuited.php";
    private final static String setUserIdPassword = "setUserIdPassword.php";
    private final static String log = "log.php";
    private final static String register = "register.php";
    private final static String setPsw = "setPsw.php";
    private final static String setNickName = "setNickName.php";
    private final static String setPicture = "setPicture.php";
    private final static String tagToGetAd = "tagToGetAd.php";
    private final static String deleteOwns = "deleteOwns.php";
    private final static String ownsToGetDis = "ownsToGetDis.php";
    private final static String insertOwns = "insertOwns.php";
    private final static String test = "test.php";
    private final static String insertVerifyCode = "yinsertVerifyCode.php";
    private final static String deleteVerifyCode = "deleteVerifyCode.php";
    private final static String insertGif = "insertGift.php";
    private final static String getGiftSum = "getGifySum.php";

    public static void tagToGetAd(HttpService httpService,String adid){
        httpService.sendGet(tagToGetAd,"tag="+adid);
    }
    public static void deleteOwns(HttpService httpService,String id,String disid){
        String param = "id="+id+"&"+"disId="+disid;
        httpService.sendPost(deleteOwns, param);
    }
    public static void insertOwns(HttpService httpService,String id,String disid){
        String param = "id="+id+"&"+"disId="+disid;
        httpService.sendPost(insertOwns,param);
    }

    public static void isSuited(HttpService httpService,String id){
        String param = "id="+id;
        httpService.sendGet(isSuited, param);
    }
    public static void setUserIdPassword(HttpService httpService,String account,String password){
        String param = "id=" + account + "&" + "password=" + password;
        httpService.sendPost(setUserIdPassword, param);
    }
    public static void log(HttpService httpService,String id,String password){
        String param = "id="+id+"&"+"password="+password;
        httpService.sendPost(log,param);
    }
    public static void register(HttpService httpService,String id,String password,String nickname){
        String param = "id="+id+"&"+"password="+password+"&"+"nickName="+nickname;
        httpService.sendPost(register,param);
    }
    public static void setPsw(HttpService httpService,String id,String password,String newpsw){
        String param = "id=" + id + "&" +"passWord="+password+"&"+"newPsw="+newpsw;
        httpService.sendPost(setPsw,param);
    }
    public static void setNickName(HttpService httpService,String id,String nickName){
        String param = "id="+id+"&"+"newNickName="+nickName;
        httpService.sendGet(setNickName, param);
    }
    public static void setPicture(HttpService httpService,String id,String filepath){
        String param = "id="+id;
        httpService.sendFileByPost(setPicture, param, filepath);
    }
    public static void ownsToGetDis(HttpService httpService,String id){
        String param = "id="+id;
        httpService.sendGet(ownsToGetDis, param);
    }

    public static void test(HttpService httpService){
        String param = "test=哈哈哈";
        httpService.sendGet(test,param);
    }

    public static void insertVerifyCode(HttpService httpService,String id,String verifyCode){
        String param = "id="+id+"&"+"verifyCode="+verifyCode;
        httpService.sendGet(insertVerifyCode,param);
    }

    public static void deleteVerifyCode(HttpService httpService,String id){
        String param = "id="+id;
        httpService.sendGet(deleteVerifyCode,param);
    }

    public static void insertGift(HttpService httpService,String id,String money){
        String param = "id="+id+"&"+"money="+money;
        httpService.sendGet(insertGif,param);
    }

    public static void getGiftSum(HttpService httpService,String id){
        String param = "id="+id;
        httpService.sendGet(getGiftSum,param);
    }
}
