package com.tea.network.net.service.convert;

import com.tea.network.net.http.response.HttpResponse;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by jiangtea on 2020/6/6.
 */

public interface Convert {

    Object parse(HttpResponse response, Type type) throws IOException;

    boolean isCanParse(String contentType);

    Object parse(String content, Type type) throws IOException;
}
