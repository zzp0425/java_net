package com.network.uriAndUrl;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周振平
 * on 2016/4/1.
 * 虚拟机支持那些协议
 */
public class ProtocolTester {

    private static Logger logger = Logger.getLogger(ProtocolTester.class);

    public static void main(String[] args) {
        //超文本传输协议
        testProtocol("http://www.adc.org");

        //安全http
        testProtocol("https://www.amazon.com/exec/obidos/order2/");

        //文件传输协议
        testProtocol("ftp://ibiblio.org/pub/languages/java/javafaq/");

        //简单邮件传输协议
        testProtocol("mailto:zzpvip@qq.com");

        //telnet
        testProtocol("telnet://dibner.poly.edu/");

        //本地文件访问
        testProtocol("file://D:\\git");

        //gopher Gopher是Internet上一个非常有名的信息查找系统，现在它基本过时
        testProtocol("gopher://gopher.anc.org.za/");

        //轻量组目录访问协议
        testProtocol("ldap://ldap.itd.umich.edu/o=University%2Oof%2oMichigan,c=US?postalAddress");

        //jar
        testProtocol("jar:http://cafeaulait.org/books/javaio.ioexamples/javaia.jar!/com/macfaq/io/StreamCopier.class");

        //NFS, 网络文件系统
        testProtocol("nfs://utopia.poly.edu/usr/tmp/");

        //JDBC的定制协议
        testProtocol("jdbc:mysql://luan.ibiblio.org:3306/NEWS");

        //rmi, 远程方法调用的定制协议
        testProtocol("rmi://ibiblio.org/RenderEngine");

        //HotJava的定制协议
        testProtocol("doc:/UsersGuide/release.html");
        testProtocol("netdoc:/UsersGuide/relase.html");
        testProtocol("systemresource://www.adc.org/+/index.html");
        testProtocol("verbatim:http://www.adc.org/");
    }



    public static void testProtocol (String url) {
        try {
            URL u = new URL(url);
            logger.info(u.getProtocol() + " is supported");
        } catch (MalformedURLException e) {
//            e.printStackTrace();
            logger.info(url.substring(0, url.indexOf(":")) + " is not supported");
        }
    }
}
