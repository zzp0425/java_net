package com.network.Internet;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 周振平
 * on 2016/3/31.
 * 给定地址找出主机名
 */
public class ReverseTest {

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("208.201.239.100");
            System.out.println(address.getCanonicalHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
