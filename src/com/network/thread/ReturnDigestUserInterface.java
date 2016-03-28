package com.network.thread;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by zzp on 2016/3/23.
 */
public class ReturnDigestUserInterface {
    private static String[] files = new String[]{"D:\\trans\\data_9.txt", "D:\\trans\\data_err.txt", "D:\\trans\\data_err_9.txt", "D:\\trans\\list.txt"};

    //使用存取方法获得县城输出的主程序
    public static void main0(String[] args) throws InterruptedException {

        for (String fileName : files) {
            ReturnDegist rd = new ReturnDegist(fileName);
            rd.start();
            StringBuilder result = new StringBuilder(fileName);
            result.append(": ");

            result.append(DatatypeConverter.printHexBinary(rd.getDigest()));
            System.out.println(result);

        }
    }

    //竞态条件
    public static void main(String[] args) throws InterruptedException {
        ReturnDegist[] degists = new ReturnDegist[files.length];

        for (int i = 0; i < degists.length; i++) {
            degists[i] = new ReturnDegist(files[i]);
            degists[i].start();
        }

       // Thread.sleep(1000);
        for (int i = 0; i < degists.length; i++) {

            StringBuilder result = new StringBuilder(files[i]);
            result.append(": ");

            result.append(DatatypeConverter.printHexBinary(degists[i].getDigest()));
            System.out.println(result);

        }
    }
}
