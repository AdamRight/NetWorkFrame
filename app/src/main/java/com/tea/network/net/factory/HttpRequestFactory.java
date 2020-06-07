package com.tea.network.net.factory;


import com.tea.network.net.http.HttpMethod;
import com.tea.network.net.http.request.HttpRequest;

import java.io.IOException;
import java.net.URI;

/**
 * Created by jiangtea on 2020/6/6.
 */

public interface HttpRequestFactory {

    HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException;
}
