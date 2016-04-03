package com.network.socket.client;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by 周振平
 * on 2016/4/3.
 * 获得socket的信息
 */
public class SocketInfo {
    private static Logger logger = Logger.getLogger(SocketInfo.class);

    public static void main(String[] args) {
        args = new String[]{"www.baidu.com", "www.baidu.com", "www.github.com", "login.ibiblio.org"};

        try {
            for (String host : args) {
                Socket socket = new Socket(host, 80);
                logger.info("connected to " + socket.getInetAddress() + " on port " + socket.getPort()
                        + " from port " + socket.getLocalPort() + " of " + socket.getLocalAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
