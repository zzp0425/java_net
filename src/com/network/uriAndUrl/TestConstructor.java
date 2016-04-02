package com.network.uriAndUrl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周振平
 * on 2016/4/1.
 */
public class TestConstructor {

    public static void main(String[] args) {
        try {
            //协议、主机名、文件
            URL url = new URL("http", "www.baidu.com", "/index.html");//端口设置为-1，所以会使用该协议的默认端口
            //指定端口
            url = new URL("http", "fourier.dur.ac.uk", 8000, "~dma3mjh/jsci/");

            //构造相对URL
            url = new URL("http://www.baidu.com/index.html");
            url = new URL(url, "main.html");


            //test openStream
            testOpenStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //test openStream
    private static void testOpenStream() throws IOException {
        URL url = new URL("http://www.baidu.com");
        InputStream is = url.openStream();
        int c;
        while ((c = is.read()) != -1) {
            System.out.write(c);
        }
        is.close();
    }
}
