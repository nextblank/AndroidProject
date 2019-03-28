package com.pn.android.sdk.network.builder;

import com.pn.android.sdk.network.HttpUtil;
import com.pn.android.sdk.network.request.OtherRequest;
import com.pn.android.sdk.network.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, HttpUtil.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
