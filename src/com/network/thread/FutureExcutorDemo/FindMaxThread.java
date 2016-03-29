package com.network.thread.FutureExcutorDemo;

import java.util.concurrent.Callable;

/**
 * Created by 周振平
 * on 2016/3/28.
 */
public class FindMaxThread implements Callable<Integer>{

    private int[] data;
    private int start;
    private int end;

    public FindMaxThread(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }


    @Override
    public Integer call(){
        int max = Integer.MIN_VALUE;

        for (int i = start; i < end; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }

        return max;
    }
}
