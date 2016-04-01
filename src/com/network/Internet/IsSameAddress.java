package com.network.Internet;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 周振平
 * on 2016/3/31.
 */
public class IsSameAddress {
    public static void main(String[] args) {
        //访问不了helios.ibiblio.org,若能访问，应返回‘same’
        try {
            InetAddress ibiblio = InetAddress.getByName("www.ibiblio.org");
            InetAddress helios = InetAddress.getByName("helios.ibiblio.org");
            if (ibiblio.equals(helios)) {
                System.out.println("same");
            } else {
                System.out.println("not same");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
