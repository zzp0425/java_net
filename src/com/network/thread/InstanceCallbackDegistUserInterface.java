package com.network.thread;

import com.network.thread.Constants.Constants;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by 周振平
 * on 2016/3/27.
 */
public class InstanceCallbackDegistUserInterface {
    private String fileName;
    private byte[] digest;

    public InstanceCallbackDegistUserInterface (String fileName) {
        this.fileName = fileName;
    }

    public void calculateDigset(){
        InstanceCallbackDegist instanceCallbackDegist = new InstanceCallbackDegist(this, fileName);
        new Thread(instanceCallbackDegist).start();
    }

    public void receivedDigest(byte[] digest) {
        this.digest = digest;
        System.out.println(this);
    }

    @Override
    public String toString() {
        String result = fileName + ": ";
        if(digest != null) {
            result += DatatypeConverter.printHexBinary(digest);
        } else {
            result += "digest not available";
        }
        return result;
    }

    public static void main(String[] args) {
        args = Constants.files;
        for (String fileName : args) {
            InstanceCallbackDegistUserInterface callback = new InstanceCallbackDegistUserInterface(fileName);
            callback.calculateDigset();
            callback.toString();
        }
    }
}
