package com.pengpenghui.domain.service.http;

/**
 * Created by Zoson on 16/5/24.
 */
public interface HttpFileListener {
    public void succ(String message,byte[] bytes);
    public void fail(String message);
}
