package com.network.Internet;

import java.net.InetAddress;

/**
 * Created by 周振平
 * on 2016/3/31.
 */
public class AddressTest {
    public static void main(String[] args) {
    }

    public static int getVersion(InetAddress ia){
        byte[] address = ia.getAddress();
        if(address.length == 4){
            return 4;
        } else if (address.length == 16) {
            return 6;
        } else {
            return -1;
        }
    }
}
