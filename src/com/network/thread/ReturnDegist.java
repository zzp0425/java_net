package com.network.thread;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zzp on 2016/3/23.
 */
public class ReturnDegist extends Thread {

    private String fileName;
    private byte[] digest;

    public ReturnDegist(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void run() {
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileName);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream dis = new DigestInputStream(in, sha);
            while (dis.read() != -1);
            dis.close();
            digest = sha.digest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public byte[] getDigest() {
        return digest;
    }
}
