package com.pengpenghui.domain.context;

/**
 * Created by Zoson on 16/1/13.
 */
public interface ContextCallback {
    public final static int DEFAULT = -1;
    public final static int SUCC = -2;
    public final static int FAIL = -3;
    public final static int ERROR = -4;
    public void response(int state,Object object);
}
