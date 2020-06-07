package com.tea.network.net.http;

import java.util.Map;

/**
 * Created by jiangtea on 2020/6/6.
 *
 * 请求头相关
 *
 */
public interface NameValueMap<K, V> extends Map<K, V> {

    String get(String name);

    void set(String name, String value);

    void setAll(Map<String, String> map);
}
