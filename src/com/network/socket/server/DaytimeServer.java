package com.network.socket.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by 周振平
 * on 2016/4/4.
 * datetime服务端
 */
public class DaytimeServer {
    private static final int DEFAULT_PORT = 13;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(DEFAULT_PORT);

            while (true) {
                Socket connect = server.accept();

                Writer out = new BufferedWriter(new OutputStreamWriter(connect.getOutputStream()));
                Date date = new Date();
                out.write(date.toString() + "\r\n");
                out.flush();
                out.close();

                connect.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
