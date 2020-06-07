package com.tea.network.net.http.request;

import com.tea.network.net.http.Header;
import com.tea.network.net.http.HttpMethod;
import com.tea.network.net.http.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by jiangtea on 2020/6/6.
 */

public interface HttpRequest extends Header {

    HttpMethod getMethod();

    URI getUri();

    OutputStream getBody();

    HttpResponse execute() throws IOException;

}
