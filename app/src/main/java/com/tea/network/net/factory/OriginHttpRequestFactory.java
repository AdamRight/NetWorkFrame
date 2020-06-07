package com.tea.network.net.factory;


import com.tea.network.net.http.origin.OriginHttpRequest;
import com.tea.network.net.http.HttpMethod;
import com.tea.network.net.http.request.HttpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Created by jiangtea on 2020/6/6.
 */

public class OriginHttpRequestFactory implements HttpRequestFactory {

    private HttpURLConnection mConnection;

    public OriginHttpRequestFactory() {

    }

    public void setReadTimeOut(int readTimeOut) {
        mConnection.setReadTimeout(readTimeOut);
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        mConnection.setConnectTimeout(connectionTimeOut);
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException {
        mConnection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(mConnection, method, uri.toString());
    }
}
