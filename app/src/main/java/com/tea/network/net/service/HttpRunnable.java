package com.tea.network.net.service;

import com.tea.network.net.http.request.HttpRequest;
import com.tea.network.net.http.response.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jiangtea on 2020/6/6.
 */

public class HttpRunnable implements Runnable {

    private HttpRequest mHttpRequest;

    private MoocRequest mRequest;

    private WorkStation mWorkStation;

    public HttpRunnable(HttpRequest httpRequest, MoocRequest request, WorkStation workStation) {
        this.mHttpRequest = httpRequest;
        this.mRequest = request;
        this.mWorkStation = workStation;

    }

    @Override
    public void run() {
        try {
            mHttpRequest.getBody().write(mRequest.getData());
            HttpResponse response = mHttpRequest.execute();
            String contentType = response.getHeaders().getContentType();
            mRequest.setContentType(contentType);
            if (response.getStatus().isSuccess()) {
                if (mRequest.getResponse() != null) {
                    mRequest.getResponse().success(mRequest, new String(getData(response)));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mWorkStation.finish(mRequest);
        }


    }

    public byte[] getData(HttpResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) response.getContentLength());
        int len;
        byte[] data = new byte[512];
        try {
            while ((len = response.getBody().read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
