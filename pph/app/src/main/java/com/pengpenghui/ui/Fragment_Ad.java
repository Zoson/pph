package com.pengpenghui.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Fragment_Ad extends Fragment {
	private WebView webView;
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String neturl="http://182.92.100.145/TouchYourCredit/ad2/";
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_ad,
                                        container, false);
            swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
            //设置刷新时动画的颜色，可以设置4个
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                                                       android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
            
            webView = (WebView) rootView.findViewById(R.id.webView);
            webView.loadUrl(neturl);
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            setListener();
        }
        return rootView;
    }

    private void setListener(){
        webView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键 时的操作
                        webView.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient()
                                 {
            @Override
            public void onPageFinished(WebView view,String url)
            {
                
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            
            @Override
            public void onRefresh() {
                Log.v("s", "正在刷新");
                // TODO Auto-generated method stub
                webView.clearCache(true);
                //webView.reload();
                new Handler().postDelayed(new Runnable() {
                    
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Log.v("s", "刷新完成");
                        webView.reload();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

}
