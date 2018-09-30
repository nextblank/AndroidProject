package com.nextblank.android.sdk.widget.jsbridge;


public interface WebViewJavascriptBridge {

    void send(String data);

    void send(String data, CallBackFunction responseCallback);
}
