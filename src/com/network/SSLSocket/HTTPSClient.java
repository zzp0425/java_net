package com.network.SSLSocket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

/**
 * Created by 周振平
 * on 2016/4/5.
 * 链接一个安全服务器
 */
public class HTTPSClient {

    public static void main(String[] args) {
        int port = 443;//默认https端口
        String host = "www.usps.com";

        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = null;
        try {
            socket = (SSLSocket) socketFactory.createSocket(host, port);

            //启用所有密码组
            String[] supported = socket.getSupportedCipherSuites();
            socket.setEnabledCipherSuites(supported);

            Writer out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            out.write("GET http://" + host + "/ HTTP/1.1\r\n");
            out.write("Host: " + host + "\r\n");
            out.write("\r\n");
            out.flush();

            //读取响应
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //读取首部
            String s;
            while (!(s = br.readLine()).equals("")) {
                System.out.println(s);
            }
            System.out.println();

            //读取长度
            String contentLength = br.readLine();
            Integer length = Integer.MAX_VALUE;
            length = Integer.parseInt(contentLength.trim(), 16);//按16进制解析
            System.out.println(contentLength);

            int c;
            int i = 0;
            while ((c = br.read()) != -1 && i++ < length) {
                System.out.write(c);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
