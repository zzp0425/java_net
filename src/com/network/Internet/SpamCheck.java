package com.network.Internet;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 周振平
 * on 2016/3/31.
 */
public class SpamCheck {
    public static final String BLACKHOLE = "sbl.spamhaus.org";

    public static void main(String[] args) {
        args = new String[]{"207.34.56.23", "125.12.32.4", "130.130.130.130"};
        for (String arg : args) {
            if (isSpammer(arg)) {
                System.out.println("normal address");//正常地址
            } else {
                System.out.println("black address");//垃圾地址
            }
        }
    }

    private static boolean isSpammer(String arg) {
        try {
            InetAddress address = InetAddress.getByName(arg);

            byte[] ads = address.getAddress();
            String query = BLACKHOLE;
            for (byte adByte : ads) {
                int unsignedByte = adByte < 0 ? adByte + 256 : adByte;
                query = unsignedByte + "." + query;
            }
            InetAddress.getByName(query);
            return true;
        } catch (UnknownHostException e) {
           // e.printStackTrace();
            return false;
        }
    }
}
