package com.nextblank.sdk.exception;

public class IllegalArgumentException extends BaseException {

    public IllegalArgumentException(Throwable throwable, int code) {
        super(throwable,code);
    }

    public void IllegalArgumentException(String msg, Object... params) {
        throw new java.lang.IllegalArgumentException(String.format(msg, params));
    }
}
