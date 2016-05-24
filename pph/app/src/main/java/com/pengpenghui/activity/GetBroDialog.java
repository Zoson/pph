package com.pengpenghui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.entity.AdData;
import com.pengpenghui.domain.context.MainController;
import com.pengpenghui.ui.R;

import java.util.jar.Manifest;

/**
 * Created by Administrator on 2015/8/7.
 */
public class GetBroDialog extends Dialog{
    private WebView wv_get_bro;
    private Button bt_get_bro;
    private Button bt_cancel;
    private MainController mainPageController;
    private Context context;
    private long dis_id;
    private AdData adData;
    public GetBroDialog(Context context, int theme) {
        super(context, theme);
    }

    public GetBroDialog(Context context,AdData adData) {
        super(context);
        setContentView(R.layout.activity_dialog_get_bro);
        this.context = context;
        this.adData = adData;
        this.mainPageController = MainController.get();
        findView();
        setListener();
        initData();
    }
    private void initData(){
        setTitle(adData.getInfo());
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
                GetBroDialog.this.dismiss();
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
                dismiss();
            }
        });
    }
}
