package com.tea.network.download;

import com.tea.network.db.DownloadEntity;
import com.tea.network.db.DownloadHelper;
import com.tea.network.file.FileStorageManager;
import com.tea.network.http.DownloadCallback;
import com.tea.network.http.HttpManager;
import com.tea.network.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by jiangtea on 2020/6/6.
 */
public class DownloadManager {

    public final static int MAX_THREAD = 2;
    public final static int LOCAL_PROGRESS_SIZE = 1;

    private static volatile DownloadManager sManager;

    private static ExecutorService sLocalProgressPool = Executors.newFixedThreadPool(LOCAL_PROGRESS_SIZE);

    private static ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 60, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {

        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "download thread #" + mInteger.getAndIncrement());
            return thread;
        }
    });

    /**
     * 管理队列
     */
    private HashSet<DownloadTask> mHashSet = new HashSet<>();

    private List<DownloadEntity> mCache;

    private long mLength;

    public static DownloadManager getInstance() {
        if (sManager == null) {
            synchronized (DownloadManager.class) {
                if (sManager == null) {
                    sManager = new DownloadManager();
                }
            }
        }
        return sManager;
    }

    /**
     * 静态内部类单例模式
     */
    public static class Holder {
        private static DownloadManager sManager = new DownloadManager();

        public static DownloadManager getInstance() {
            return sManager;
        }
    }


    private DownloadManager() {

    }

    /**
     * 移除队列
     * @param task
     */
    private void finish(DownloadTask task) {
        mHashSet.remove(task);
    }

    public void download(final String url, final DownloadCallback callback) {
        final DownloadTask task = new DownloadTask(url, callback);
        if (mHashSet.contains(task)) {
            callback.fail(HttpManager.TASK_RUNNING_ERROR_CODE, "任务已经执行了");
            return;
        }
        mHashSet.add(task);

        mCache = DownloadHelper.getInstance().getAll(url);

        if (mCache == null || mCache.size() == 0) {
            HttpManager.getInstance().asyncRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    finish(task);
                    Logger.debug("eee", "onFailure ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (!response.isSuccessful() && callback != null) {
                        callback.fail(HttpManager.NETWORK_ERROR_CODE, "网络出问题了");
                        return;
                    }

                    mLength = response.body().contentLength();
                    if (mLength == -1) {
                        callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE, "content length -1");
                        return;
                    }
                    processDownload(url, mLength, callback, mCache);
                    finish(task);
                }
            });

        } else {
            for (int i = 0; i < mCache.size(); i++) {
                DownloadEntity entity = mCache.get(i);
                if (i == mCache.size() - 1) {
                    mLength = entity.getEnd_position() + 1;
                }
                long startSize = entity.getStart_position() + entity.getProgress_position();
                long endSize = entity.getEnd_position();
                sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback, entity));
            }
        }

        sLocalProgressPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        File file = FileStorageManager.getInstance().getFileByName(url);
                        long fileSize = file.length();
                        int progress = (int) (fileSize * 100.0 / mLength);
                        if (progress >= 100) {
                            callback.progress(progress);
                            return;
                        }
                        callback.progress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    private void processDownload(String url, long length, DownloadCallback callback, List<DownloadEntity> cache) {
        long threadDownloadSize = length / MAX_THREAD;
        if (cache == null && cache.size() == 0) {
            mCache = new ArrayList<>();
        }
        for (int i = 0; i < MAX_THREAD; i++) {
            DownloadEntity entity = new DownloadEntity();
            long startSize = i * threadDownloadSize;
            long endSize = 0;
            if (endSize == MAX_THREAD - 1) {
                endSize = length - 1;
            } else {
                endSize = (i + 1) * threadDownloadSize - 1;
            }

            entity.setDownload_url(url);
            entity.setStart_position(startSize);
            entity.setEnd_position(endSize);
            entity.setThread_id(i + 1);
            sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback, entity));
        }

    }


    /**
     * DownloadConfig使用构建者模式
     *
     * @param config
     */
    public void init(DownloadConfig config) {
        sThreadPool = new ThreadPoolExecutor(config.getCoreThreadSize(), config.getMaxThreadSize(), 60, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {
            private AtomicInteger mInteger = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "download thread #" + mInteger.getAndIncrement());
                return thread;
            }
        });

        sLocalProgressPool = Executors.newFixedThreadPool(config.getLocalProgressThreadSize());

    }


//    public static DownloadManager getInstance() {
//        if (sManager == null) {
//            synchronized (DownloadManager.class) {
//                if (sManager == null) {
//                    sManager = new DownloadManager();
//
//                    // 1. sManager 分配内存
//                    //2.sManager 调用构造方法 init
//                    //3 sManager 指向内存分配区域
//                }
//            }
//            return sManager;
//        }
//        return sManager;
//    }
}
