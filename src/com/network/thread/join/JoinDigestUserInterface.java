package com.network.thread.join;

import com.network.thread.Constants.Constants;
import com.network.thread.ReturnDegist;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by 周振平
 * on 2016/3/30.
 * join：线程连接，会等待另一个线程结束
 */
public class JoinDigestUserInterface {

    public static void main(String[] args) {
        args = Constants.files;
        ReturnDegist[] digestThreads = new ReturnDegist[args.length];

        for (int i = 0; i < digestThreads.length; i++) {
            digestThreads[i] = new ReturnDegist(args[i]);
            digestThreads[i].start();
        }

        try {
            for (int i = 0; i < digestThreads.length; i++) {
                digestThreads[i].join();//等待当前线程结束
                StringBuilder sb = new StringBuilder(args[i]);
                sb.append(" : ");
                byte[] digest = digestThreads[i].getDigest();
                sb.append(DatatypeConverter.printHexBinary(digest));
                System.out.println(sb.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
