package com.network.thread;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zzp on 2016/3/21.
 */
public class DigestRunnable implements Runnable {
    private String fileName;

    public DigestRunnable(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(fileName);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream dis = new DigestInputStream(in, sha);
            while (dis.read() != -1);
            dis.close();

            byte[] digest = sha.digest();

            StringBuilder result = new StringBuilder(fileName);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String[] files = new String[]{"D:\\trans\\data_9.txt", "D:\\trans\\data_err.txt", "D:\\trans\\data_err_9.txt", "D:\\trans\\list.txt"};
        for (String fileName : files) {
            Thread t = new Thread(new DigestRunnable(fileName));
            t.start();
        }
    }
}
