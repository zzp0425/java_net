package com.network.socket.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 周振平
 * on 2016/4/4.
 * 多线程datetime服务器
 */
public class MultithreadedDatetimeServer {

    private static final int DEFAULT_PORT = 13;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(DEFAULT_PORT);

            while (true) {
                Socket connect = server.accept();

                Thread task = new Thread(new DatetimeThread(connect));
                task.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static class DatetimeThread implements Runnable {

        private Socket connect;

        public DatetimeThread(Socket connect) {
            this.connect = connect;
        }

        @Override
        public void run() {
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
        }
    }
}
