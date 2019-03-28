package com.pn.android.sdk.tools.app;

import android.content.Context;
import android.widget.Toast;

import com.pn.android.sdk.tools.Tools;

public final class ToastTool {

    private static final Object lock = new Object();
    private static volatile ToastTool instance;

    private ToastTool() {
    }

    public static ToastTool instance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ToastTool();
                }
            }
        }
        return instance;
    }

    public void showToast(String msg) {
        Toast.makeText(Tools.app().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int stringID) {
        Context appContext = Tools.app().getApplicationContext();
        showToast(appContext.getString(stringID));
    }

    public void showToastLong(String msg) {
        Toast.makeText(Tools.app().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void showToastLong(int stringID) {
        Context appContext = Tools.app().getApplicationContext();
        showToastLong(appContext.getString(stringID));
    }
}
