package com.gateway;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周振平
 * on 2016/4/25.
 */
public class BitMapiso {
    /**
     * 解析请求包
     * @param body
     * @param config
     * @return List
     */
//    02 00 70 20 00 00 20 C0 82 00 19 06 20 51 32 00 00 00 02 61 20 60 00 00 00 00 00 02 00 00 00 00 73 37 06
//    20 51 32 00 00 00 02 61 20 d1 91 12 01 00 00 00 00 00 30 30 30 30 31 31 31 31 31 30 32 32 35 30 31 35 33
//    31 31 31 31 31 31 01 56 00 44 9f 26 08 92 b6 ae 9a 9b 10 2e d6 9f 27 01 80 9f 10 13 07 01 01 03 a0 a0 10
//    01 0a 01 00 00 00 10 37 51 3a 22 be
    @SuppressWarnings("unchecked")
    public static List unpackRequest(byte[] body, int[][] config) {
        List outList = new ArrayList();
        // 取得除信息类型以外的包信息。也就是取得位图的初始位置。
        byte[] realbody = new byte[body.length - 4];
        System.arraycopy(body, 4, realbody, 0, realbody.length);
        // 取得位图
        byte[] map = null;//位图
        byte[] map8 = new byte[8];
        System.arraycopy(realbody, 0, map8, 0, 8);

        boolean[] bmap8 = LoUtils.getBinaryFromByte(map8);
        if (bmap8[1]) {
            // 如果第一位为1，则是可扩展位图，设为16字节长度。
            //如 70 20 00 00 ，0111 0100 0000 0000 第1位为1
            map = new byte[16];
            System.arraycopy(realbody, 0, map, 0, 16);
        } else {
            map = map8;
        }



        boolean[] bmap = LoUtils.getBinaryFromByte(map);
        int tmplen = map.length;
        //从2开始，即70 20 00 00 20 C0 82 00，
        //          0111 0000 0100 0000 0000 0000 0000 0000 0010 0000 1100 0000 1000 0010 0000 0000,忽略前两位
        for (int i = 2; i < bmap.length; i++) {
            if (bmap[i]) {
                //BitMap bitMap = null;
                //寻找位图中的1对应的数据
                int bit=-1;
                /**
                 * 存放所有接口的配置信息
                 * [][0]bit 位：在Map中的位
                 * [][1]type 类型：1-ascii 2-binary
                 * [][2]len 长度：(对定长有效)
                 * [][3]varLean 变长：0-非变长，2位变长，3位变长
                 */
                for (int j = 0; j < config.length; j++) {
                    if (config[j][0] == i) {//判断该域是否在配置信息中是否存在，存在，返回位图中的位置
                        bit=j;
                        break;
                    }
                }
                BitMap outBitMap = new BitMap();
                outBitMap.setBit(i);
                outBitMap.setBitType(config[bit][1]);
                //len对变长是无用的。
                outBitMap.setLen(config[bit][2]);
                outBitMap.setVariable(config[bit][3]);
                byte[] nextData = null;
                if (config[bit][3] > 0) {//变长
                    //取出变长部分的值。
                    int varLen = config[bit][3];//几位变长
                    if (config[bit][1] == 2) {//数据为二进制
                        varLen = varLen - 1;
                    }
                    tmplen += varLen;//追加变长域长度位数
                    byte[] varValue = new byte[varLen];
                    System.arraycopy(realbody, tmplen, varValue, 0, varValue.length);
                    int datLen = 0;
                    if (config[bit][1] == 2) {//二进制
                        datLen = LoUtils.bcdToint(varValue);
                    } else {//asicc的byte数组
                        datLen = byteToInt(varValue);
                    }

                    // 取出变长部分后带的值。
                    nextData = new byte[datLen];
                    System.arraycopy(realbody, tmplen, nextData, 0,nextData.length);
                    tmplen += nextData.length;
                } else {//定长
                    nextData = new byte[config[bit][2]];
                    System.arraycopy(realbody, tmplen, nextData, 0,nextData.length);
                    tmplen += config[bit][2];
                }
                outBitMap.setData(nextData);
                outList.add(outBitMap);
            }
        }
        return outList;
    }

    /**
     * 打包响应包,不包括消息类型
     * @param list
     * @return byte[]
     */
    @SuppressWarnings("unchecked")
    public static byte[] PackResponse(List list) {
        int len = 16;
        /**
         * 存放所有接口的配置信息
         * [][0]bit 位：在Map中的位
         * [][1]type 类型：1-ascii 2-binary
         * [][2]len 长度：(对定长有效)
         * [][3]varLean 变长：0-非变长，2位变长，3位变长
         */
        for (int i = 0; i < list.size(); i++) {
            BitMap bitMap = (BitMap) list.get(i);
            // 计算请求包总长度
            if (bitMap.getBitType() == 2) {//二进制
                if (bitMap.getVariable() > 0) {//变长
                    len += bitMap.getVariable() - 1 + bitMap.getData().length;
                } else {
                    len += bitMap.getVariable() + bitMap.getData().length;
                }
            } else {//ascii码
                len += bitMap.getVariable() + bitMap.getData().length;
            }
        }
        byte[] body = new byte[len];
        // 位图
        boolean[] bbitMap = new boolean[129];
        bbitMap[1] = true;//128域
        int temp = (bbitMap.length - 1) / 8;
        for (int j = 0; j < list.size(); j++) {
            BitMap bitMap = (BitMap) list.get(j);
            bbitMap[bitMap.getBit()] = true;//有值的位置置为true
            byte[] bitmap = LoUtils.getByteFromBinary(bbitMap);
            System.arraycopy(bitmap, 0, body, 0, bitmap.length);
            // 数据
            if (bitMap.getVariable() > 0) {
                // 数据是可变长的:拼变长的值
                byte[] varValue = null;
                if (bitMap.getBitType() == 2) {
                    varValue = LoUtils.StrToBCDBytes(String.format("%0"+ bitMap.getVariable() + "d",bitMap.getData().length));
                } else {
                    varValue = String.format("%0" + bitMap.getVariable() + "d",bitMap.getData().length).getBytes();
                }
                System.arraycopy(varValue, 0, body, temp, varValue.length);
                temp += varValue.length;
                // 拼变长部分后所带的数的值。
                System.arraycopy(bitMap.getData(), 0, body, temp, bitMap.getData().length);
                temp += bitMap.getData().length;
            } else {
                // 数据是固定长度的。
                byte dat[] =new byte[bitMap.getLen()];
                if (bitMap.getData().length!=bitMap.getLen()){
                    System.arraycopy(bitMap.getData(), 0, dat, 0, bitMap.getLen());
                }else{
                    dat=bitMap.getData();
                }
                System.arraycopy(dat, 0, body, temp, dat.length);
                temp += bitMap.getData().length;
            }
        }
        return body;
    }

    private static int byteToInt(byte[] res) {
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }
}
