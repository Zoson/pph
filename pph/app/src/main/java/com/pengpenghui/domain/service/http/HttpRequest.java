package com.pengpenghui.domain.service.http;

/**
 * Created by zoson on 3/15/15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

public class HttpRequest {
    private final static String TAG = "HttpRequest";
    private final String Website = "http://182.92.100.145/TouchYourCredit";
    private final int SUCCESSFULLY = 0x1;
    private final int UNSUCCESSFULLY = 0x0;
    private final int NETWORKERROR = -1;
    private final int SUCCESSFULLY_IMAGE = 0x3;
    private final int SUCCESSFULLY_FILE = 0x4;
    private String api = "API = null";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.println("handleinvoke");
            Msg m = (Msg)msg.obj;
            if (m == null){
                Log.e(TAG,api + "  m is null");return;
            }
            switch (msg.what){
                case SUCCESSFULLY:((HttpListener)m.listeners).succToRequire(m.msg,m.data);break;
                case UNSUCCESSFULLY:((HttpListener)m.listeners).failToRequire(m.msg, m.data);break;
                case NETWORKERROR:((HttpListener)m.listeners).netWorkError(m.msg, m.data);break;
                case SUCCESSFULLY_IMAGE:((HttpImgListener)m.listeners).succToImg((Bitmap)m.ob); break;
                case SUCCESSFULLY_FILE:((HttpFileListener)m.listeners).succToFile();break;
                //default:((HttpListener)m.listeners).netWorkError(m.msg, m.data);
            }
        }
    };

    public void sendGet(final String api,final String param, final HttpListener httpListener) {
        this.api = api;
        System.out.println("sendGet");
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                BufferedReader in = null;
                String require = "";
                Msg m = new Msg();
                m.listeners = httpListener;
                try {
                    String urlNameString = getUrl(api, param);
                    URL realUrl = new URL(urlNameString);
                    // 打开和URL之间的连接
                    HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
                    // 设置通用的请求属性
                    connection.setRequestProperty("accept", "*/*");
                    connection.setRequestProperty("connection", "Keep-Alive");
                    connection.setRequestProperty("contentType", "utf-8");
                    // 建立实际的连接
                    connection.connect();
                    // 获取所有响应头字段
                    Map<String, List<String>> map = connection.getHeaderFields();
                    // 遍历所有的响应头字段
                    for (String key : map.keySet()) {
                        System.out.println(key + "--->" + map.get(key));
                    }
                    // 定义 BufferedReader输入流来读取URL的响应
                    in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));

                    String line;
                    while ((line = in.readLine()) != null) {
                        require += line;
                    }
                    System.out.println(require);
                    JSONObject js = new JSONObject(require);

                    m.state = js.getInt("state");
                    m.msg = js.getString("message");
                    m.data = js.getString("data");
                    Message msg = Message.obtain();
                    msg.obj = m;

                    switch (js.getInt("state")){
                        case SUCCESSFULLY:msg.what = SUCCESSFULLY;break;
                        case UNSUCCESSFULLY:msg.what = UNSUCCESSFULLY;break;
                        case NETWORKERROR:msg.what = NETWORKERROR;break;
                       // case 101:msg.what=0x0;break;
                        default:msg.what = NETWORKERROR;break;
                    }
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    Message msg = Message.obtain();

                    msg.obj = m;
                    handler.sendMessage(msg);
                    e.printStackTrace();

                }catch (IOException e){
                    Message msg = Message.obtain();
                    msg.what = NETWORKERROR;
                    msg.obj = m;
                    handler.sendMessage(msg);
                }
                // 使用finally块来关闭输入流
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

    public void sendPost(final String api, final String param,final HttpListener httpListener) {
        //this.param = param;
        this.api = api;
        new Thread(){
            @Override
            public void run() {
                Msg m = new Msg();
                m.listeners = httpListener;
                PrintWriter out = null;
                BufferedReader in = null;
                String require = "";
                try {
                    Looper.prepare();
                    URL realUrl = new URL(getUrl(api,""));
                    // 打开和URL之间的连接
                    HttpURLConnection  connection = (HttpURLConnection)realUrl.openConnection();
                    // 设置通用的请求属性
                    connection.setRequestProperty("accept", "*/*");
                    connection.setRequestProperty("connection", "Keep-Alive");

                    // 发送POST请求必须设置如下两行
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    // 获取URLConnection对象对应的输出流
                    out = new PrintWriter(connection.getOutputStream());
                    // 发送请求参数
                    out.print(param);
                    // flush输出流的缓冲
                    out.flush();
                    System.out.println("conn="+connection.getHeaderFields());
                    // 定义BufferedReader输入流来读取URL的响应
                    in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        require += line;
                    }
                    System.out.println(require);
                    JSONObject js = new JSONObject(require);
                    m.state = js.getInt("state");
                    m.msg = js.getString("message");
                    m.data = js.getString("data");
                    Message msg = Message.obtain();
                    msg.obj = m;
                    switch (js.getInt("state")){
                        case SUCCESSFULLY:msg.what = SUCCESSFULLY;break;
                        case UNSUCCESSFULLY:msg.what = UNSUCCESSFULLY;break;
                        case NETWORKERROR:msg.what = NETWORKERROR;break;
                        default:msg.what = NETWORKERROR;break;
                    }
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    Message msg = Message.obtain();
                    msg.obj = m;
                    handler.sendMessage(msg);
                    e.printStackTrace();

                }catch (IOException e){
                    Message msg = Message.obtain();
                    msg.what = NETWORKERROR;
                    msg.obj = m;
                    handler.sendMessage(msg);
                }
                //使用finally块来关闭输出流、输入流
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

    public void sendFileByPost(final String api, final String param,final String filePath,final String param2,HttpFileListener httpFileListener){
        this.api = api;
        new Thread(){
            @Override
            public void run(){
                String require = "";
                try{
                    File file = new File(filePath);
                    if (!file.exists() || !file.isFile()) {
                        System.out.println("no exist");
                        return;
                    }

                    /**
                     * 第一部分
                     */
                    URL urlObj = new URL(getUrl(api,""));
                    HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

                    /**
                     * 设置关键值
                     */
                    connection.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false); // post方式不能使用缓存
                    // 设置请求头信息
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");

                    // 设置边界
                    String BOUNDARY = "----------" + System.currentTimeMillis();
                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                            + BOUNDARY);
                    // 请求正文信息

                    OutputStream out = new DataOutputStream(connection.getOutputStream());
                    // 第一部分：
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

                    sb_file.append("Content-Disposition: form-data;name=\""+param2+"\";filename=\""
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
                    byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

                    out.write(foot);

                    out.flush();
                    out.close();
                    System.out.println("conn="+connection.getHeaderFields());
                    // 定义BufferedReader输入流来读取URL的响应
                    BufferedReader in_buff = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = in_buff.readLine()) != null) {
                        require += line;
                    }
                    System.out.println(require);
                    /**
                     * 读取服务器响应，必须读取,否则提交不成功
                     */
                    System.out.print(connection.getResponseCode());
                    /**
                     * 下面的方式读取也是可以的
                     */

                    // try {
                    // // 定义BufferedReader输入流来读取URL的响应
                    // BufferedReader reader = new BufferedReader(new InputStreamReader(
                    // con.getInputStream()));
                    // String line = null;
                    // while ((line = reader.readLine()) != null) {
                    // System.out.println(line);
                    // }
                    // } catch (Exception e) {
                    // System.out.println("发送POST请求出现异常！" + e);
                    // e.printStackTrace();
                    // }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();

    }

    protected String getUrl(String api,String param){
        this.api =api;
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


    public void getPhoto(String urlString, final HttpImgListener httpImgListener) {
        this.api = urlString;
        final String url = urlString;
        new Thread() {
            @Override
            public void run() {
                try {
                    URL imgUrl = new URL(url);
                    // 使用HttpURLConnection打开连接
                    HttpURLConnection  connection = (HttpURLConnection) imgUrl
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    // 将得到的数据转化成InputStream
                    InputStream is = connection.getInputStream();
                    // 将InputStream转换成Bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    Message msg = Message.obtain();
                    Msg m = new Msg();
                    m.state = SUCCESSFULLY_IMAGE;
                    m.ob = bitmap;
                    m.listeners = httpImgListener;
                    msg.what = SUCCESSFULLY_IMAGE;
                    msg.obj = m;
                    handler.sendMessage(msg);

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

    public void downloadFile(String urlStr, final HttpFileListener httpFileListener) {
        this.api = urlStr;
        final String _urlStr = urlStr;
        String filePath;

        String tableName = _urlStr.substring(_urlStr.lastIndexOf("/")+1, _urlStr.length());

//        if(EnvironmentData.checkSDCard()) {
//            filePath = Environment.getExternalStorageDirectory()+StaticData.IMAGE_DIR+tableName;
//        }
//        else {
//            filePath = StaticData.IMAGE_DIR+tableName;
//        }
        filePath = Environment.getExternalStorageDirectory()+"";
        final String _filePah = filePath;
        Log.i("finance", _filePah);
        new Thread() {
            OutputStream output = null;
            InputStream input = null;
            HttpURLConnection conn = null;
            @Override
            public void run() {
                super.run();
                try {
                    URL url=new URL(_urlStr);
                    conn=(HttpURLConnection)url.openConnection();
                    File file=new File(_filePah);
                    if(file.exists()) {
                        Log.i("finance", "exist");
                        file.delete();

                        Log.i("finance", "delete");
                        Thread.sleep(1000);
                    }

                    input=conn.getInputStream();

                    File file2  = new File(_filePah);
                    output=new FileOutputStream(file2);
                    //读取大文件
                    byte[] buffer=new byte[4*1024];
                    while(input.read(buffer)!=-1){
                        output.write(buffer);
                    }
                    output.flush();
                    Message msg = new Message();
                    msg.what = SUCCESSFULLY_FILE;
                    Msg m = new Msg();
                    m.listeners = httpFileListener;
                    handler.sendMessage(msg);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    try {
                        output.close();
                        input.close();
                        conn.disconnect();
                        System.out.println("success");
                    } catch (IOException e) {
                        System.out.println("fail");
                        e.printStackTrace();
                    }
                }

            }
        }.start();

    }

    public void disConnect(){
//        new Thread(){
//            @Override
//            public void run() {
//                connection.disconnect();
//            }
//        }.start();
    }

    protected class Msg{
        public int state = -1;
        public String msg;
        public String data;
        public Object ob;
        public Object listeners;
    }
}
