package com.network.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 周振平
 * on 2016/3/27.
 * 升级版实例化的回调方法
 */
public class InstanceCallbackDegist implements Runnable{
    private InstanceCallbackDegistUserInterface callback;
    private String fileName;

    public InstanceCallbackDegist (InstanceCallbackDegistUserInterface callback, String fileName) {
        this.callback = callback;
        this.fileName = fileName;
    }
    @Override
    public void run() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            DigestInputStream dis = new DigestInputStream(new FileInputStream(fileName), md);
            while (dis.read() != -1);
            dis.close();
            callback.receivedDigest(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
