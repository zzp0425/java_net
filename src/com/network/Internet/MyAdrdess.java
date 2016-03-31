package com.network.Internet;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 周振平
 * on 2016/3/31.
 * 查找本地机器的地址
 */
public class MyAdrdess {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
