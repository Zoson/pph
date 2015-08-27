package com.pengpenghui.pph_interface;

/**
 * Created by zoson on 6/19/15.
 */
public interface ViewInterface {
    public void requestSuccessfully(String msg, String data);
    public void requestUnSuccessfully(String msg, String data);
    public void requestError(String msg, String data);
}
