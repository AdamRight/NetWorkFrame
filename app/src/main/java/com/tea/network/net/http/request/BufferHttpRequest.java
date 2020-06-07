package com.tea.network.net.http.request;


import com.tea.network.net.http.HttpHeader;
import com.tea.network.net.http.response.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jiangtea on 2020/6/6.
 */

public abstract class BufferHttpRequest extends AbstractHttpRequest {

    private ByteArrayOutputStream mByteArray = new ByteArrayOutputStream();

    protected OutputStream getBodyOutputStream() {
        return mByteArray;
    }

    protected HttpResponse executeInternal(HttpHeader header) throws IOException {
        byte[] data = mByteArray.toByteArray();
        return executeInternal(header, data);
    }

    protected abstract HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException;
}
