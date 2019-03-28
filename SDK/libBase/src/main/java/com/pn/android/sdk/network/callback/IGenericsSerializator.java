package com.pn.android.sdk.network.callback;

public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
