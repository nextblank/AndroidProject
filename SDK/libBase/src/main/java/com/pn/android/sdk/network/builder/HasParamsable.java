package com.pn.android.sdk.network.builder;

import java.util.Map;

public interface HasParamsable {
    HttpRequestBuilder params(Map<String, String> params);

    HttpRequestBuilder addParams(String key, String val);
}
