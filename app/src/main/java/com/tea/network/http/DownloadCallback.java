package com.tea.network.http;

import java.io.File;

/**
 * Created by jiangtea on 2020/6/6.
 */
public interface DownloadCallback {

    void success(File file);

    void fail(int errorCode, String errorMessage);

    void progress(int progress);
}
