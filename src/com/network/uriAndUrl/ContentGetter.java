package com.network.uriAndUrl;

import com.network.thread.Constants.Constants;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周振平
 * on 2016/4/1.
 * 下载一个对象
 */
public class ContentGetter {

    public static Logger logger = Logger.getLogger(ContentGetter.class);

    public static void main(String[] args) {
        args = Constants.views;

        try {
            URL url = new URL(args[0]);
            Object o = url.getContent();
            logger.info("I got a " + o.getClass().getName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
