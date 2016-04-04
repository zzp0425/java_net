package com.network.socket.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by 周振平
 * on 2016/4/4.
 * 使用线程池的datetime服务器
 */
public class PooledDatetimeServer {

    private static final int DEFAULT_PORT = 13;
    private static final int POOL_SIZE = 50;

    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(POOL_SIZE);
        try {
            ServerSocket server = new ServerSocket(DEFAULT_PORT);

            while (true) {
                Socket connect = server.accept();
                Callable<Void> task = new DatetimeTask(connect);
                executors.submit(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static class DatetimeTask implements Callable<Void> {

        private Socket connect;

        public DatetimeTask(Socket connect) {
            this.connect = connect;
        }


        @Override
        public Void call() throws Exception {
            try {
                Writer out = new OutputStreamWriter(connect.getOutputStream());
                Date date = new Date();
                out.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date) + "\r\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    connect.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
