package com.tea.network.net.http.response;

import com.tea.network.net.http.Header;
import com.tea.network.net.http.HttpStatus;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiangtea on 2020/6/6.
 */

public interface HttpResponse extends Header, Closeable {

    HttpStatus getStatus();

    String getStatusMsg();

    InputStream getBody() throws IOException;

    void close();

    long getContentLength();

}
