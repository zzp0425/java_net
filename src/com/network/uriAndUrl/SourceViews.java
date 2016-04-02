package com.network.uriAndUrl;

import com.network.thread.Constants.Constants;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周振平
 * on 2016/4/1.
 * 下载一个web页面
 */
public class SourceViews {
    public static void main(String[] args) {
        args = Constants.views;
        InputStream in = null;

        try {
            URL u = new URL(args[0]);
            in = u.openStream();

            //缓冲输入流提高性能
            in = new BufferedInputStream(in);

            //将InputStream串连到一个Reader
            Reader reader = new InputStreamReader(in);
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char)c);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
