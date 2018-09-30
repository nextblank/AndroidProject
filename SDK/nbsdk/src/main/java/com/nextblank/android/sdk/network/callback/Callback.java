package com.nextblank.android.sdk.network.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T> {
    /**
     * UI Thread
     *
     * @param request
     */
    public void onStart(Request request, int id) {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onComplete(int id) {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void onProgress(float progress, long total, int id) {

    }

    /**
     * if you parse reponse code in parseResponse, you should make this method return true.
     *
     * @param response
     * @return
     */
    public boolean validateReponse(Response response, int id) {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseResponse(Response response, int id) throws Exception;

    public abstract void onError(Call call, Exception e, int id);

    public abstract void onSuccess(T response, int id);


    public static Callback CALLBACK_DEFAULT = new Callback() {

        @Override
        public Object parseResponse(Response response, int id) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onSuccess(Object response, int id) {

        }
    };

}