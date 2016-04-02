package com.network.uriAndUrl;


import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周振平
 * on 2016/4/2.
 * 判断两个地址是否相等
 */
public class URLEquality {
    private static Logger logger = Logger.getLogger(URLEquality.class);

    public static void main(String[] args) {
        try {
            URL baidu = new URL("http://www.baidu.com");
            URL githup = new URL("http://github.com");

            if (baidu.equals(githup)) {
                logger.info("same");
            } else {
                logger.info("not same");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
