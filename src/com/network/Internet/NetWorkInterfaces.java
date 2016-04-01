package com.network.Internet;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by 周振平
 * on 2016/3/31.
 * 列出所有网络接口
 */
public class NetWorkInterfaces {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                System.out.println(ni);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
