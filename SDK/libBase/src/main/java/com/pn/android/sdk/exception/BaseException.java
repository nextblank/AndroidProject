package com.pn.android.sdk.exception;

public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    public int code;
    public String message;

    public BaseException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }
}
