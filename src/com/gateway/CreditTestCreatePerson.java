package com.gateway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreditTestCreatePerson {
	public static void main(String[] args) throws Exception { 
        Socket socket = new Socket("172.26.2.41",6077);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.write(getPackage());
        out.write("\r\n");
		out.flush(); 
		String strReceive = new String(in.readLine());
		byte[] temp = strReceive.getBytes("gbk");
		String str = new String(temp,"utf-8");
		
		System.out.println("返回的报文为："+str);		
		socket.close();
    }

    public static String getPackage() {
        StringBuffer sb = new StringBuffer();
        sb.append("30303931353030303030302020202020");
        sb.append("20202020202020202020202020203939");
        sb.append("39390030303030303939393900000000");
        sb.append("00003131313145303030555041590000");
        sb.append("0000000020202020202020E3B60001B0");
        sb.append("00001004000000000000003632333139");
        sb.append("39303030303030303135303034320000");
        sb.append("00000000000000000000004530303031");
        sb.append("00323031363034323630393535353720");
        sb.append("20202020302020202020303631333738");
        sb.append("37202020202030323031363034323632");
        sb.append("30313631323131000000000000000000");
        sb.append("00003939393900000000000000363231");
        sb.append("34363830303030303530313434443231");
        sb.append("31323232303030303030333234393630");
        sb.append("4600A49C599E3211D537A8FAF6B74295");
        sb.append("76C10000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000323738555041");
        sb.append("59000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("30303031000000000000000000005550");
        sb.append("41590000000000000000000059000000");
        sb.append("31000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000020");
        sb.append("20202020202020203020202020202020");
        sb.append("20203000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000032393000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000041343943353939453332");
        sb.append("31314435333741384641463642373432");
        sb.append("39353736433100000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("41343943353939453332313144353337");
        sb.append("20202030202020300000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("00000000000000000000000000000000");
        sb.append("20202030202020300020202020203020");
        sb.append("20202020300000000020202020203000");
        sb.append("00000000000000000000000000000000");
        sb.append("0000000020202030");
        return sb.toString();
    }

}
