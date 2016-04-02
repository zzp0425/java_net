package com.network.uriAndUrl;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周振平
 * on 2016/4/1.
 * URL的组成部分
 */
public class URLSplitter {

    private static Logger log = Logger.getLogger(URLSplitter.class);

    public static void main(String[] args) {
        args = new String[]{"ftp://mp3:mp3@138.247.121.61:21000/c%3a/",
                    "http://yun.baidu.com/?ref=PPZQ", "http://admin@www.blackstar.com:8080"};

        try {
            for (String url : args) {
                URL u = new URL(url);
                log.info("the URL is " + u);
                log.info("the scheme is " + u.getProtocol());//协议
                log.info("the user info is " + u.getUserInfo());

                String host = u.getHost();
                if (host != null) {
                    int atSign = host.indexOf("@");
                    if (atSign != -1) {
                        host = host.substring(atSign + 1);
                        log.info("the host is " + host);
                    } else {
                        log.info("the host is null");
                    }
                }

                log.info("the port is " + u.getPort());
                log.info("the path is " + u.getPath());
                log.info("the ref is " + u.getRef());
                log.info("the query string is " + u.getQuery());
                System.out.println();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
