package com.nextblank.android.sdk.network.builder;

import com.nextblank.android.sdk.network.request.PostStringRequest;
import com.nextblank.android.sdk.network.request.RequestCall;

import okhttp3.MediaType;

public class PostStringBuilder extends HttpRequestBuilder<PostStringBuilder> {
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, content, mediaType, id).build();
    }


}
