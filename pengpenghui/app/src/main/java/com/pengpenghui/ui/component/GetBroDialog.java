package com.pengpenghui.ui.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.pengpenghui.domain.entity.AdData;
import com.pengpenghui.domain.controller.MainPageController;
import com.pengpenghui.ui.R;

/**
 * Created by Administrator on 2015/8/7.
 */
public class GetBroDialog extends Dialog{
    private WebView wv_get_bro;
    private Button bt_get_bro;
    private Button bt_cancel;
    private MainPageController mainPageController;
    private Context context;
    private long dis_id;
    private String url;
    public GetBroDialog(Context context, int theme) {
        super(context, theme);
    }

    public GetBroDialog(Context context,MainPageController mainPageController) {
        super(context);
        setContentView(R.layout.activity_dialog_get_bro);
        this.context = context;
        this.mainPageController = mainPageController;
        findView();
        setListener();
        initData();
    }
    private void initData(){
        AdData adData = mainPageController.getAdData();
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
                GetBroDialog.this.dismiss();
            }
        });
        bt_get_bro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPageController.getBroByAd(0);
                dismiss();
            }
        });
    }
}
