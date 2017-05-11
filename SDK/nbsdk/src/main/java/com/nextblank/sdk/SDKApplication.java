package com.nextblank.sdk;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nextblank.sdk.tools.Tools;
import com.squareup.leakcanary.LeakCanary;

/**
 * Application相关初始化工作
 **/
public class SDKApplication extends Application {

    private static SDKApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //Application在此初始化
        Tools.init(this);
        Fresco.initialize(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static SDKApplication getInstance() {
        return instance;
    }
}
