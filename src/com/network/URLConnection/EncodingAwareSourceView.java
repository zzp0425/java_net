package com.network.URLConnection;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 周振平
 * on 2016/4/3.
 * 用正确的字符集下载一个页面
 */
public class EncodingAwareSourceView {
    public static void main(String[] args) {
        try {
            String charset = "ISO-8859-1";
            URL url = new URL("http://www.baidu.com");
            URLConnection urlConnection = url.openConnection();

            String contentType = urlConnection.getContentType();

            if (contentType != null && contentType.indexOf("charset=") > -1) {
                charset = contentType.substring(contentType.indexOf("charset=" + 8));
            }

            System.out.println("filename:" + url.getFile());

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Reader reader = new InputStreamReader(in, charset);
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
