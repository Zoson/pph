package com.pengpenghui.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.MainController;
import com.pengpenghui.domain.entity.AdData;
import com.pengpenghui.domain.service.nfc.NFCListener;
import com.pengpenghui.ui.R;

/**
 * Created by druson on 2016/2/29.
 */
public class GetBroActivity extends Activity implements NFCListener {

    private PendingIntent pendingIntent;
    private WebView wv_get_bro;
    private Button bt_get_bro;
    private Button bt_cancel;
    private MainController mainPageController;
    private Context context;
    private long dis_id;
    private String url;
    private boolean tryStartNfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_get_bro);
        this.mainPageController = mainPageController;
        findView();
        setListener();
        initData();
        startNfc();
    }

    private void startNfc(){
    }

    private void initData(){
        AdData adData = mainPageController.getCurrentAd();
        if(adData==null){
            Toast.makeText(context, "获取广告失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setTitle(adData.getAd_info());
        wv_get_bro.loadUrl(adData.getAd_url());
        wv_get_bro.setWebViewClient(new WebViewClient());
        WebSettings webSettings = wv_get_bro.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv_get_bro.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        Toast.makeText(context,adData.getAd_url(),Toast.LENGTH_SHORT).show();
    }
    private void findView(){
        wv_get_bro = (WebView)findViewById(R.id.wv_get_bro);
        bt_cancel = (Button)findViewById(R.id.bt_cancel);
        bt_get_bro = (Button)findViewById(R.id.bt_get_bro);
    }
    private void setListener(){
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt_get_bro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mainPageController.isUserLogin()){
                    Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                mainPageController.getBroByAd(new ContextCallback() {
                    @Override
                    public void response(int state, Object object) {
                        Toast.makeText(context,""+object,Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
    }

    public void getnfc(String code){
        Toast.makeText(this,code,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this,"new Intent",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void getNfcInfo(String code) {
        //Toast.makeText(this,code,Toast.LENGTH_SHORT).show();
//        if (getBroDialog!=null&&getBroDialog.isShowing()){
//            return;
//        }
//        mainPageController.fromNfcTagToGetAd(code, new ContextCallback() {
//            @Override
//            public void response(int state, Object object) {
//                getBroDialog = new GetBroDialog(MainActivity.this, mainPageController);
//                getBroDialog.show();
//            }
//        });
    }
}
