package com.nextblank.android.sdk.network.callback;

public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
