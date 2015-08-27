package com.pengpenghui.pph_interface;

/**
 * Created by zoson on 6/17/15.
 */
public interface HttpListener {
    public void succToRequired(String message, String data);
    public void failToRequired(String message, String data);
    public void netWorkError();
}
