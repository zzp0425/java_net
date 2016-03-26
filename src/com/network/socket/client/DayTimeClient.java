package com.network.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 周振平
 * on 2016/3/23.
 */
public class DayTimeClient {
    public Date getDateFromNetwork() throws ParseException {
        Socket socket = null;
        String time = null;
        try {
            socket = new Socket("time.nist.gov", 13);//时间服务器
            socket.setSoTimeout(15000);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            boolean flag = true;
            while (flag) {
                time = br.readLine();
                if(!"".equals(time.trim())){
                    flag = false;
                }
            }

            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return parseDate(time);
    }

    public static Date parseDate(String time) throws ParseException {
        String parees[] = time.split(" ");
        String date = parees[1] + " " + parees[2] + " UTC";
        return new SimpleDateFormat("yy-MM-dd hh:mm:ss z").parse(date);

    }

    public static void main(String[] args) throws ParseException {
        DayTimeClient dtc = new DayTimeClient();
        System.out.println(dtc.getDateFromNetwork());

        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("localhost", 80);//InetSocketAddress是jdk中SocketAddress唯一的子类
        try {
            socket.connect(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();//使用socket无参构造器可以避免socket为空
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
