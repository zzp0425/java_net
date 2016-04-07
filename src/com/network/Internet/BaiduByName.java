package com.network.Internet;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 周振平
 * on 2016/3/31.
 */
public class BaiduByName {
    public static void main(String[] args) {
        try {
            //根据主机名返回主机IP
            InetAddress address = InetAddress.getByName("https://assets-cdn.github.com");
            System.out.println(address);

            //根据主机IP返回主机名
            address = InetAddress.getByName("180.97.33.107");
            System.out.println(address.getHostName());

            //获取所有主机IP
            InetAddress[] inetAddress = InetAddress.getAllByName("http://www.baidu.com");
            for (InetAddress netAddress : inetAddress) {
                System.out.println(netAddress);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("could not find host");
        }
    }
}
