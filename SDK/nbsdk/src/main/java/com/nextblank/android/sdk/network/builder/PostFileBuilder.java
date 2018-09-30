package com.nextblank.android.sdk.network.builder;

import com.nextblank.android.sdk.network.request.PostFileRequest;
import com.nextblank.android.sdk.network.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

public class PostFileBuilder extends HttpRequestBuilder<PostFileBuilder> {
    private File file;
    private MediaType mediaType;


    public HttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public HttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(url, tag, params, headers, file, mediaType, id).build();
    }


}
