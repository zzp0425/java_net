package com.network.uriAndUrl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周振平
 * on 2016/4/2.
 * 完成一次百度搜索
 */
public class Baidu {
    public static void main(String[] args) {
        args = new String[]{""};
        String target = "";

        for (int i = 0; i < args.length; i++) {
            target += args[i] + " ";
        }

        target = target.trim();

        QueryString query = new QueryString();
        query.add("wd", target);

        try {
            URL u = new URL("http://www.baidu.com/s?" + query);
            InputStream in = new BufferedInputStream(u.openStream());
            int c;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
