package com.tea.network.net.factory;

import com.tea.network.net.http.HttpMethod;
import com.tea.network.net.http.request.HttpRequest;
import com.tea.network.utils.Utills;

import java.io.IOException;
import java.net.URI;

/**
 * Created by jiangtea on 2020/6/6.
 */

public class HttpRequestProvider {

    private static boolean OKHTTP_REQUEST = Utills.isExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    private HttpRequestFactory mHttpRequestFactory;

    public HttpRequestProvider() {
        if (OKHTTP_REQUEST) {
            mHttpRequestFactory = new OkHttpRequestFactory();
        } else {
            mHttpRequestFactory = new OriginHttpRequestFactory();
        }
    }

    public HttpRequest getHttpRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return mHttpRequestFactory.createHttpRequest(uri, httpMethod);
    }

    public HttpRequestFactory getHttpRequestFactory() {
        return mHttpRequestFactory;
    }

    public void setHttpRequestFactory(HttpRequestFactory httpRequestFactory) {
        mHttpRequestFactory = httpRequestFactory;
    }
}
