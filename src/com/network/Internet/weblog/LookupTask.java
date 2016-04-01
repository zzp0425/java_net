package com.network.Internet.weblog;

import java.net.InetAddress;
import java.util.concurrent.Callable;

/**
 * Created by 周振平
 * on 2016/3/31.
 * 处理web服务器日志文件
 * 常见日志文件格式如下：
 *  访问地址                     时间                     方式   访问内容                 响应码 字节数
 * 205.160.187.76 unknow - [17/Jun/2016:22:53:58 -0500] "GET /bgs/greenbg.gif HTTP 1.0" 200 50
 *
 */
public class LookupTask implements Callable<String> {

    private String line;

    public LookupTask(String line) {
        this.line = line;
    }

    @Override
    public String call() throws Exception {
        int index = line.indexOf(" ");
        String address = line.substring(0, index);
        String theRest = line.substring(index);
        System.out.println("address : " + address + ", theRest : " + theRest);
        String hostName = InetAddress.getByName(address).getHostName();
        return hostName + " " + theRest;
    }
}
