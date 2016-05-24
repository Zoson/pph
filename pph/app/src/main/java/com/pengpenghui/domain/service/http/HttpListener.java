package com.pengpenghui.domain.service.http;

/**
 * Created by Zoson on 16/5/23.
 */
public interface HttpListener {
    public void succ(String message, String data, byte[] bytes);
    public void fail(String message);
}
