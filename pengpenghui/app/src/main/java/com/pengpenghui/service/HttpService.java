package com.pengpenghui.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.pengpenghui.pph_interface.HttpImgListener;
import com.pengpenghui.pph_interface.HttpListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by zoson on 6/17/15.
 */
public class HttpService {
    private String Website ="http://182.92.100.145/TouchYourCredit";
    private String Required = "";
    private HttpListener listener;
    private HttpImgListener httpImgListener;
    private String message;
    private String data;
    private String api;
    private String param;
    private String filePath;
    private Bitmap bit;
    private HttpURLConnection connection;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:listener.failToRequired(message, data);break;
                case 1:listener.succToRequired(message, data);break;
                case -1:listener.netWorkError();break;
                case 0x3:httpImgListener.successToGetImg(bit); break;
                default:listener.netWorkError();
            }
        }
    };

    public void sendGet(String api, String param) {
        this.api = api;
        this.param = param;
        System.out.println("sendGet");
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                BufferedReader in = null;
                Required = "";
                try {

                    String urlNameString = getUrl(HttpService.this.api, HttpService.this.param);
                    URL realUrl = new URL(urlNameString);
                    // 打开和URL之间的连接
                    connection = (HttpURLConnection)realUrl.openConnection();
                    // 设置通用的请求属�?
                    connection.setRequestProperty("accept", "*/*");
                    connection.setRequestProperty("connection", "Keep-Alive");
                    connection.setRequestProperty("contentType", "utf-8");
                    // 建立实际的连�?
                    connection.connect();
                    // 获取�?有响应头字段
                    Map<String, List<String>> map = connection.getHeaderFields();
                    // 遍历�?有的响应头字�?
                    for (String key : map.keySet()) {
                        System.out.println(key + "--->" + map.get(key));
                    }
                    // 定义 BufferedReader输入流来读取URL的响�?
                    in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));

                    String line;
                    while ((line = in.readLine()) != null) {
                        Required += line;
                    }
                    System.out.println(Required);
                    JSONObject js = new JSONObject(Required);
                    message = js.getString("message");
                    data = js.getString("data");
                    Message msg = Message.obtain();

                    switch (js.getInt("state")){
                        case 0:msg.what = 0;break;
                        case 1:msg.what = 1;break;
                        case -1:msg.what = -1;break;
                        default:msg.what = -1;break;
                    }
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Message msg = Message.obtain();
                    msg.what = 0x2;
                    handler.sendMessage(msg);
                    System.out.println("发�?�GET请求出现异常�?" + e);
                    e.printStackTrace();
                }
                // 使用finally块来关闭输入�?
                finally {

                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

            }
        }.start();

    }


    public void sendPost(String api, String param) {
        this.param = param;
        this.api = api;
        new Thread(){
            @Override
            public void run() {
                PrintWriter out = null;
                BufferedReader in = null;
                Required = "";
                try {
                    Looper.prepare();
                    URL realUrl = new URL(getUrl(HttpService.this.api,""));
                    // 打开和URL之间的连�?
                    connection = (HttpURLConnection)realUrl.openConnection();
                    // 设置通用的请求属�?
                    connection.setRequestProperty("accept", "*/*");
                    connection.setRequestProperty("connection", "Keep-Alive");

                    // 发�?�POST请求必须设置如下两行
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    // 获取URLConnection对象对应的输出流
                    out = new PrintWriter(connection.getOutputStream());
                    // 发�?�请求参�?
                    out.print(HttpService.this.param);
                    // flush输出流的缓冲
                    out.flush();
                    System.out.println("conn="+connection.getHeaderFields());
                    // 定义BufferedReader输入流来读取URL的响�?
                    in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        Required += line;
                    }
                    System.out.println(Required);
                    JSONObject js = new JSONObject(Required);
                    message = js.getString("message");
                    data = js.getString("data");
                    //PersonalInfo personalInfo = PersonalInfo.getInstance();
                    //personalInfo.setUid(Integer.parseInt(data));
                    Message msg = Message.obtain();
                    switch (js.getInt("state")){
                        case 0:msg.what = 0;break;
                        case 1:msg.what = 1;break;
                        case -1:msg.what = -1;break;
                        default:msg.what = -1;break;
                    }
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Message msg = Message.obtain();
                    msg.what = -1;
                    handler.sendMessage(msg);
                    System.out.println("发�?? POST 请求出现异常�?"+e);
                    e.printStackTrace();

                }
                //使用finally块来关闭输出流�?�输入流
                finally{

                    try{
                        if(out!=null){
                            out.close();
                        }
                        if(in!=null){
                            in.close();
                        }
                    }
                    catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }.start();
    }
    public void sendFileByPost(String api, final String param,String filePath){
        this.filePath = filePath;
        this.api = api;
        this.param = param;
        new Thread(){
            @Override
            public void run(){
                try{
                    File file = new File(HttpService.this.filePath);
                    if (!file.exists() || !file.isFile()) {
                        System.out.println("no exist");
                        return;
                    }

                    /**
                     * 第一部分
                     */
                    URL urlObj = new URL(getUrl(HttpService.this.api,""));
                    connection = (HttpURLConnection) urlObj.openConnection();

                    /**
                     * 设置关键�?
                     */
                    connection.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false); // post方式不能使用缓存
                    // 设置请求头信�?
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");

                    // 设置边界
                    String BOUNDARY = "----------" + System.currentTimeMillis();
                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                            + BOUNDARY);
                    // 请求正文信息

                    OutputStream out = new DataOutputStream(connection.getOutputStream());
                    // 第一部分�?
                    StringBuilder sb_param = new StringBuilder();
                    sb_param.append("--").append(BOUNDARY).append("\r\n");
                    sb_param.append("Content-Disposition: form-data; name=\""+param.split("=")[0]+"\"\r\n\r\n");
                    sb_param.append(param.split("=")[1]);
                    out.write(sb_param.toString().getBytes("utf-8"));

                    StringBuilder sb_file = new StringBuilder();
                    sb_file.append("\r\n");
                    sb_file.append("--"); // ////////必须多两道线
                    sb_file.append(BOUNDARY);
                    sb_file.append("\r\n");

                    sb_file.append("Content-Disposition: form-data;name=\"bitmap\";filename=\""
                            + file.getName() + "\"\r\n");
                    sb_file.append("Content-Type:application/octet-stream\r\n\r\n");

                    byte[] head = sb_file.toString().getBytes("utf-8");

                    out.write(head);
                    //out.write(param.getBytes());
                    // 文件正文部分
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();

                    // 结尾部分
                    byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义�?后数据分隔线

                    out.write(foot);

                    out.flush();
                    out.close();
                    System.out.println("conn="+connection.getHeaderFields());
                    // 定义BufferedReader输入流来读取URL的响�?
                    BufferedReader in_buff = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = in_buff.readLine()) != null) {
                        Required += line;
                    }
                    System.out.println(Required);
                    /**
                     * 读取服务器响应，必须读取,否则提交不成�?
                     */
                    System.out.print(connection.getResponseCode());
                    System.out.println("upload9");


                    /**
                     * 下面的方式读取也是可以的
                     */

                    // try {
                    // // 定义BufferedReader输入流来读取URL的响�?
                    // BufferedReader reader = new BufferedReader(new InputStreamReader(
                    // con.getInputStream()));
                    // String line = null;
                    // while ((line = reader.readLine()) != null) {
                    // System.out.println(line);
                    // }
                    // } catch (Exception e) {
                    // System.out.println("发�?�POST请求出现异常�?" + e);
                    // e.printStackTrace();
                    // }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();

    }
    protected String getUrl(String api,String param){
        String url;
        if(api.equals("")){
            System.out.println(Website);
            return Website;
        }else {
            if (param.equals("")) {
                System.out.println("url=" + Website + "/" + api + "/");
                url = Website + "/" + api + "/";
            } else {
                url = Website + "/" + api + "/?" + param;
                System.out.println("url=" + Website + "/" + api + "/?" + param);
            }
            return url;
        }
    }
    public String getRequired(){
        return Required;
    }

    public void setListener(HttpListener listener){
        this.listener = listener;

    }
    public void setImgListener(HttpImgListener httpImgListener) {
        this.httpImgListener = httpImgListener;
    }

    public void getPhoto(String urlString, int _tag) {

        final String url = urlString;
        final int pos = _tag;
        new Thread() {
            @Override
            public void run() {
                try {
                    URL imgUrl = new URL(url);
                    // 使用HttpURLConnection打开连接
                    connection = (HttpURLConnection) imgUrl
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    // 将得到的数据转化成InputStream
                    InputStream is = connection.getInputStream();
                    // 将InputStream转换成Bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Message msg = Message.obtain();
                    msg.what = 0x3;
                    bit = bitmap;
                    handler.sendMessage(msg);
                    is.close();
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    System.out.println("[getNetWorkBitmap->]MalformedURLException");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("[getNetWorkBitmap->]IOException");
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void disConnect(){
        new Thread(){
            @Override
            public void run() {
                connection.disconnect();
            }
        }.start();

    }
}
