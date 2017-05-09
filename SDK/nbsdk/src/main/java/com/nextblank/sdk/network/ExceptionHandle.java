package com.nextblank.sdk.network;

import android.net.ParseException;

import com.nextblank.sdk.exception.AppLayerException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public AppLayerException handleException(Throwable e) {
        AppLayerException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new AppLayerException(e, Error.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        }
        if (e instanceof JSONException
                || e instanceof ParseException) {
            ex = new AppLayerException(e, Error.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new AppLayerException(e, Error.NETWORD_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new AppLayerException(e, Error.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new AppLayerException(e, Error.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new AppLayerException(e, Error.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else {
            ex = new AppLayerException(e, Error.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }

}

