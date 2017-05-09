package com.nextblank.sdk.network;

import android.content.Context;
import android.widget.Toast;

import com.nextblank.sdk.exception.AppLayerException;
import com.nextblank.sdk.tools.Tools;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class BaseSubscriber<T> implements Subscriber<T> {

    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Subscription s) {
        // if  NetworkAvailable no !   must to call onCompleted
        if (!Tools.network().isNetworkConnected(context)) {
            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onComplete();
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof AppLayerException) {
            onError(t);
        } else {
            onError(new AppLayerException(t, Error.UNKNOWN));
        }
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void onError(AppLayerException e);

}
