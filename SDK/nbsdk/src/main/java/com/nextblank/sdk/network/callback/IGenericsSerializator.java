package com.nextblank.sdk.network.callback;

public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
