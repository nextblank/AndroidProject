package com.nextblank.android.sdk.network.builder;

import com.nextblank.android.sdk.network.HttpUtil;
import com.nextblank.android.sdk.network.request.OtherRequest;
import com.nextblank.android.sdk.network.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, HttpUtil.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
