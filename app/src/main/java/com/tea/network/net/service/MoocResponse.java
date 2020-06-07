package com.tea.network.net.service;

/**
 * Created by jiangtea on 2020/6/6.
 */

public abstract class MoocResponse<T> {

    public abstract void success(MoocRequest request, T data);

    public abstract void fail(int errorCode, String errorMsg);

}
