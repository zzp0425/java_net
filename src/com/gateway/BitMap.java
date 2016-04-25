package com.gateway;

/**
 * Created by 周振平
 * on 2016/4/25.
 * 定义BitMap类存储每个域的信息
 */
public class BitMap {

    private int bit;//位
    private int bitType;//数据类型 1-ascii 2-binary
    private int variable;//是否变长 0-否 1-两位变长 2-三位变长
    private int len;//数据长度
    private byte[] data;//数据

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

    public int getBitType() {
        return bitType;
    }

    public void setBitType(int bitType) {
        this.bitType = bitType;
    }

    public int getVariable() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
