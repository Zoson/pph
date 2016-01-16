package com.pengpenghui.util;

import android.os.Environment;

/**
 * Created by zoson on 15/4/4.
 */
public class EnvironmentData {

    public static boolean checkSDCard() {
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
}
