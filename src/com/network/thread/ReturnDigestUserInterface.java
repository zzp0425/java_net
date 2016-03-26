package com.network.thread;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by zzp on 2016/3/23.
 */
public class ReturnDigestUserInterface {
    public static void main(String[] args) {
        String[] files = new String[]{"D:\\trans\\data_9.txt", "D:\\trans\\data_err.txt", "D:\\trans\\data_err_9.txt", "D:\\trans\\list.txt"};

        for (String fileName : files) {
            ReturnDegist rd = new ReturnDegist(fileName);
            rd.start();
            StringBuilder result = new StringBuilder(fileName);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(rd.getDigest()));
            System.out.println(result);

        }
    }
}
