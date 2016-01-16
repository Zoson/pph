package com.pengpenghui.domain.entity;


import com.pengpenghui.domain.context.PPHContext;
import com.pengpenghui.service.http.HttpFileListener;
import com.pengpenghui.service.http.HttpImgListener;
import com.pengpenghui.service.http.HttpListener;
import com.pengpenghui.service.http.HttpRequest;

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
    private final static String insertVerifyCode = "insertVerifyCode.php";
    private final static String deleteVerifyCode = "deleteVerifyCode.php";
    private final static String insertGif = "insertGift.php";
    private final static String getGiftSum = "getGifySum.php";

    public static void tagToGetAd(HttpRequest httpRequest,String adid,HttpListener httpListener){
        httpRequest.sendGet(tagToGetAd,"tag="+adid,httpListener);
    }
    public static void deleteOwns(HttpRequest httpRequest,String id,String disid,HttpListener httpListener){
        String param = "id="+id+"&"+"disId="+disid;
        httpRequest.sendPost(deleteOwns, param, httpListener);
    }
    public static void insertOwns(HttpRequest httpRequest,String id,String disid,HttpListener httpListener){
        String param = "id="+id+"&"+"disId="+disid;
        httpRequest.sendPost(insertOwns,param,httpListener);
    }

    public static void isSuited(HttpRequest httpRequest,String id,HttpListener httpListener){
        String param = "id="+id;
        httpRequest.sendGet(isSuited, param, httpListener);
    }
    public static void setUserIdPassword(HttpRequest httpRequest,String account,String password,HttpListener httpListener){
        String param = "id=" + account + "&" + "password=" + password;
        httpRequest.sendPost(setUserIdPassword, param, httpListener);
    }
    public static void log(HttpRequest httpRequest,String id,String password,HttpListener httpListener){
        String param = "id="+id+"&"+"password="+password;
        httpRequest.sendPost(log,param,httpListener);
    }
    public static void register(HttpRequest httpRequest,String id,String password,String nickname,HttpListener httpListener){
        String param = "id="+id+"&"+"password="+password+"&"+"nickName="+nickname;
        httpRequest.sendPost(register,param,httpListener);
    }
    public static void setPsw(HttpRequest httpRequest,String id,String password,String newpsw,HttpListener httpListener){
        String param = "id=" + id + "&" +"passWord="+password+"&"+"newPsw="+newpsw;
        httpRequest.sendPost(setPsw,param,httpListener);
    }
    public static void setNickName(HttpRequest httpRequest,String id,String nickName,HttpListener httpListener){
        String param = "id="+id+"&"+"newNickName="+nickName;
        httpRequest.sendGet(setNickName, param, httpListener);
    }
    public static void setPicture(HttpRequest httpRequest,String id,String filepath,HttpFileListener httpFileListener){
        String param = "id="+id;
        httpRequest.sendFileByPost(setPicture, param, filepath, "bitmap", httpFileListener);
    }
    public static void ownsToGetDis(HttpRequest httpRequest,String id,HttpListener httpListener){
        String param = "id="+id;
        httpRequest.sendGet(ownsToGetDis, param, httpListener);
    }

    public static void test(HttpRequest httpRequest,HttpListener httpListener){
        String param = "test=哈哈哈";
        httpRequest.sendGet(test,param,httpListener);
    }

    public static void insertVerifyCode(HttpRequest httpRequest,String id,String verifyCode,HttpListener httpListener){
        String param = "id="+id+"&"+"verifyCode="+verifyCode;
        httpRequest.sendPost(insertVerifyCode, param, httpListener);
    }

    public static void deleteVerifyCode(HttpRequest httpRequest,String id,HttpListener httpListener){
        String param = "id="+id;
        httpRequest.sendGet(deleteVerifyCode, param, httpListener);
    }

    public static void insertGift(HttpRequest httpRequest,String id,String money,HttpListener httpListener){
        String param = "id="+id+"&"+"money="+money;
        httpRequest.sendGet(insertGif,param,httpListener);
    }

    public static void getGiftSum(HttpRequest httpRequest,String id,HttpListener httpListener){
        String param = "id="+id;
        httpRequest.sendGet(getGiftSum,param,httpListener);
    }

    public static void getPhoto(HttpRequest httpRequest,String url,HttpImgListener httpImgListener){
        httpRequest.getPhoto(url,httpImgListener);
    }
}
