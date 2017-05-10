package com.nextblank.sdk.exception;

public class AppLayerException extends Exception {

    private static final long serialVersionUID = 1L;

    public int code;
    public String message;

    public AppLayerException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }
}
