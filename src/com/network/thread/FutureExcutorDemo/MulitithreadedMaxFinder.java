package com.network.thread.FutureExcutorDemo;

import java.util.concurrent.*;

/**
 * Created by 周振平
 * on 2016/3/28.
 */
public class MulitithreadedMaxFinder {

    public static int max (int[] data) throws ExecutionException, InterruptedException {
        if (data.length == 1) {
            return data[0];
        } else if (data.length == 0) {
            throw new IllegalArgumentException();
        }

        FindMaxThread task1 = new FindMaxThread(data, 0, data.length/2);
        FindMaxThread task2 = new FindMaxThread(data, data.length/2, data.length);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = service.submit(task1);
        Future<Integer> future2 = service.submit(task2);

        return Math.max(future1.get(), future2.get());
    }



}
