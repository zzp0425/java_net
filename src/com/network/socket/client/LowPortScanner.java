package com.network.socket.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by 周振平
 * on 2016/3/23.
 * 检查端口占用
 */
public class LowPortScanner {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try {
            System.out.println(host);
            for (int i = 1; i < 1024; i++) {
                Socket socket = new Socket(host, i);
                System.out.println("there is a server on port " + i + " of " + host);
                socket.close();
            }

        } catch (UnknownHostException err){
            System.err.println(err);
        } catch (IOException e) {
            System.err.println(e);
        }


    }
}
