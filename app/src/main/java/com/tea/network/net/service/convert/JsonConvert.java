package com.tea.network.net.service.convert;

import com.google.gson.Gson;
import com.tea.network.net.http.response.HttpResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by jiangtea on 2020/6/6.
 */
public class JsonConvert implements Convert {

    private Gson gson = new Gson();

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Override
    public Object parse(HttpResponse response, Type type) throws IOException {
        Reader reader = new InputStreamReader(response.getBody());
        return gson.fromJson(reader, type);

    }

    @Override
    public boolean isCanParse(String contentType) {
        return CONTENT_TYPE.equals(contentType);
    }

    @Override
    public Object parse(String content, Type type) throws IOException {
        return gson.fromJson(content, type);
    }
}
