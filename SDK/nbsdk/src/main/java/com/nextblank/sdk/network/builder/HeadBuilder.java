package com.nextblank.sdk.network.builder;

import com.nextblank.sdk.network.HttpUtil;
import com.nextblank.sdk.network.request.OtherRequest;
import com.nextblank.sdk.network.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, HttpUtil.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
