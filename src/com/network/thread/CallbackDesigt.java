package com.network.thread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 周振平
 * on 2016/3/27.
 */
public class CallbackDesigt implements Runnable{
    private String fileName;
    public CallbackDesigt (String fileName) {
        this.fileName = fileName;
    }
    @Override
    public void run() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            DigestInputStream dis = new DigestInputStream(fis, md);
            while (dis.read() != -1);
            dis.close();

            byte[] digest = md.digest();
            CallbackDesigtUserInterface.receiveDigest(digest, fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
