package com.network.uriAndUrl.authenticator;

import java.io.*;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周振平
 * on 2016/4/3.
 * 下载由口令保护的Web页面的程序
 */
public class SecureSourceViewer {

    public static void main(String[] args) {
        Authenticator.setDefault(new DialogAuthenticator());
        args = new String[]{"http://www.baidu.com"};
        try {
            for (int i = 0; i < args.length; i++) {
                URL u = new URL(args[i]);
                InputStream in = new BufferedInputStream(u.openStream());
                Reader reader = new InputStreamReader(in);
                int c;
                while ((c = reader.read()) != -1) {
                    System.out.print((char) c);
                }
                System.out.println();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
