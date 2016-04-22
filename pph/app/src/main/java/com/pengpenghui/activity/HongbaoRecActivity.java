package com.pengpenghui.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

import com.pengpenghui.ui.R;

public class HongbaoRecActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hongbao_list);
    }

}
