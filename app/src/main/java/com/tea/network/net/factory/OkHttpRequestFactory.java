package com.tea.network.net.factory;

import com.tea.network.net.http.request.OkHttpRequest;
import com.tea.network.net.http.HttpMethod;
import com.tea.network.net.http.request.HttpRequest;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by jiangtea on 2020/6/6.
 */

public class OkHttpRequestFactory implements HttpRequestFactory {

    private OkHttpClient mClient;

    public OkHttpRequestFactory() {
        this.mClient = new OkHttpClient();
    }

    public OkHttpRequestFactory(OkHttpClient client) {
        this.mClient = client;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.mClient = mClient.newBuilder().
                readTimeout(readTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.mClient = mClient.newBuilder().
                writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.mClient = mClient.newBuilder().
                connectTimeout(connectionTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) {
        return new OkHttpRequest(mClient, method, uri.toString());
    }
}
