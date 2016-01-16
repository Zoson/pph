
package com.pengpenghui.ui.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {
    private String TAG = "WXEntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SocializeListeners.SnsPostListener mSnsPostListener = new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
                Log.d(TAG, "onStart()");
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int stCode, SocializeEntity entity) {
                Log.d(TAG, "onComplete()");
                if (stCode == 200) {
                    Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private Activity getActivity() {
        return this;
    }


}
