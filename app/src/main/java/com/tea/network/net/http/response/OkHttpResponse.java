package com.tea.network.net.http.response;

import com.tea.network.net.http.HttpHeader;
import com.tea.network.net.http.HttpStatus;

import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by jiangtea on 2020/6/6.
 */
public class OkHttpResponse extends AbstractHttpResponse {

    private Response mResponse;

    private HttpHeader mHeaders;

    public OkHttpResponse(Response response) {
        this.mResponse = response;
    }

    @Override
    protected InputStream getBodyInternal() {
        return mResponse.body().byteStream();
    }

    @Override
    protected void closeInternal() {
        mResponse.body().close();

    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.getValue(mResponse.code());
    }

    @Override
    public String getStatusMsg() {
        return mResponse.message();
    }

    @Override
    public long getContentLength() {
        return mResponse.body().contentLength();
    }

    @Override
    public HttpHeader getHeaders() {
        if (mHeaders == null) {
            mHeaders = new HttpHeader();
        }

        for (String name : mResponse.headers().names()) {
            mHeaders.set(name, mResponse.headers().get(name));
        }

        return mHeaders;
    }
}