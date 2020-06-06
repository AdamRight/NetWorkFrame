package com.tea.network;

import android.app.Application;

import com.tea.network.file.FileStorageManager;
import com.tea.network.http.HttpManager;

/**
 * Created by jiangtea on 2020/6/6.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
    }
}
