package com.network.Internet.weblog;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by 周振平
 * on 2016/3/31.
 */
public class PooledWeblog {

    private final static int NUM_THREADS = 4;

    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(NUM_THREADS);
        Queue<LogEntry> results = new LinkedList<LogEntry>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            for (String entry = br.readLine(); entry != null; entry = br.readLine()){
                LookupTask task = new LookupTask(entry);
                Future<String> future = executors.submit(task);
                LogEntry logEntry = new LogEntry(entry, future);
                results.add(logEntry);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (LogEntry entry : results) {
                System.out.println(entry.future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static class LogEntry {
        String original;
        Future<String> future;

        public LogEntry(String original, Future<String> future) {
            this.original = original;
            this.future = future;
        }
    }

}
