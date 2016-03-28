package com.network.thread;

import com.network.thread.Constants.Constants;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by 周振平
 * on 2016/3/27.
 */
public class CallbackDesigtUserInterface {

    /**
     * 供CallbackDegist回调的回调函数
     * @param digest
     * @param name
     */
    public static void receiveDigest (byte[] digest, String name) {
        StringBuilder result = new StringBuilder(name);
        result.append(": ");
        result.append(DatatypeConverter.printHexBinary(digest));
        System.out.println(result);
    }

    public static void main(String[] args) {
        args = Constants.files;
        for (String fileName : args) {
            CallbackDesigt cd = new CallbackDesigt(fileName);
            Thread t = new Thread(cd);
            t.start();
        }
    }
}
