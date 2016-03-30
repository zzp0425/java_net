package com.network.thread.Executor;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 周振平
 * on 2016/3/30.
 */
public class GZipAllFiles {
    private final static int THREAD_COUNT = 4;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        args = new String[]{"D:\\cfc\\20151221"};
        for (String fileName : args) {
            File file = new File(fileName);
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if (!files[i].isDirectory()) {//不递归处理目录
                            GZipRunnable gZip = new GZipRunnable(files[i]);
                            pool.submit(gZip);
                        }
                    }
                } else {
                    GZipRunnable gZip = new GZipRunnable(file);
                    pool.submit(gZip);
                }
            }
        }
        pool.shutdown();
    }
}
