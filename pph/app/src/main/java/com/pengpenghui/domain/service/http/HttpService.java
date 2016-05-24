package com.pengpenghui.domain.service.http;

import android.os.Handler;
import android.os.Message;

import com.druson.cycle.enity.Enity;
import com.druson.cycle.service.http.HttpRequest;
import com.druson.cycle.service.http.Request;
import com.druson.cycle.service.http.Response;
import com.druson.cycle.utils.ThreadPool;

import org.json.JSONException;

/**
 * Created by Zoson on 16/5/23.
 */
public class HttpService {
    public static final int SUCC = 1;
    public static final int FAIL = 0;
    public static final int ERROR = -1;

    public static final int MSG = 1000;
    public static final int FILE = 1001;

    private String Address = "http://182.92.100.145/Touch/index.php/Home/Enter";
    private HttpRequest httpRequest;
    private HttpHanlder handler;
    public HttpService(){
        httpRequest = new HttpRequest();
        handler = new HttpHanlder();
    }

    public void sendGet(final Request request, final HttpListener httpListener){
        ThreadPool.start(new Runnable() {
            @Override
            public void run() {
                request.reqType = HttpRequest.GET;
                Response response = httpRequest.sendGet(request);
                Msg msg = new Msg();
                msg.httpListener = httpListener;
                switch (response.getState()) {
                    case HttpRequest.SUCC:
                        try {
                            msg.initByJson(response.getData_string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case HttpRequest.FAIL:
                        msg.state = FAIL;
                        msg.message = "网络出错";
                        break;
                }
                Message message = Message.obtain();
                message.obj = msg;
                message.what = MSG;
                handler.sendMessage(message);
            }
        });
    }


    public void sendPost(final Request request, final HttpListener httpListener){
        ThreadPool.start(new Runnable() {
            @Override
            public void run() {
                request.reqType = HttpRequest.POST;
                Response response = httpRequest.sendPost(request);
                Msg msg = new Msg();
                msg.httpListener = httpListener;
                switch (response.getState()) {
                    case HttpRequest.SUCC:
                        try {
                            msg.initByJson(response.getData_string());
                            System.out.println("HttpService sendPost data " + response.getData_string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("HttpService sendPost msg state "+msg.state);
                        break;
                    case HttpRequest.FAIL:
                        msg.state = FAIL;
                        msg.message = "网络出错";
                        break;
                }
                Message message = Message.obtain();
                message.obj = msg;
                message.what = MSG;
                handler.sendMessage(message);
            }
        });
    }

    public void sendFile(String api,String params,String file){

    }

    class HttpHanlder extends Handler {

        @Override
        public void handleMessage(Message message) {

            Msg msg = (Msg)message.obj;
            if (msg == null)return;
            HttpListener httpListener = msg.httpListener;
            if (httpListener==null)return;
            System.out.println("HttpService HttpHanlder "+ msg.state);
            if (message.what == MSG){
                switch (msg.state){
                    case SUCC:
                        httpListener.succ(msg.message,msg.data,null);
                        break;
                    case FAIL:
                    case ERROR:
                        httpListener.fail(msg.message);
                        break;
                }
//            }else if (message.what == FILE){
//                switch (msg.state){
//                    case SUCC:
//                        httpListener.succ(msg.message,null,msg.bytes);
//                        break;
//                    case FAIL:
//                    case ERROR:
//                        httpListener.fail(msg.message);
//                        break;
//                }
//
            }
        }
    }

    class Msg extends Enity {
        int state;
        String message;
        String data;
        HttpListener httpListener;
    }

}
