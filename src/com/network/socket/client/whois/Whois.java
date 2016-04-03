package com.network.socket.client.whois;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;

/**
 * Created by 周振平
 * on 2016/4/4.
 * whois类
 */
public class Whois {

    private static Logger logger = Logger.getLogger(Whois.class);

    private static final int DEFAULT_PORT = 43;
    private static final String DEFAULT_HOST = "whois.internic.net";

    private int port = DEFAULT_PORT;
    InetAddress host;

    public Whois(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public Whois(String hostName, int port) throws UnknownHostException {
        this(InetAddress.getByName(hostName), port);
    }

    public Whois(InetAddress host) throws UnknownHostException {
        this(host, DEFAULT_PORT);
    }

    public Whois(String hostName) throws UnknownHostException {
        this(InetAddress.getByName(hostName), DEFAULT_PORT);
    }

    public Whois() throws UnknownHostException {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    /********************构造方法结束***********************************/
    //搜索的条目
    public static enum SearchFor {
        ANY("Any"), NETWORK("Network"), PERSON("Person"), HOST("Host"),
        DOMAIN("Domain"), ORGANIZATION("organization"), GROUP("Group"), GATEWAY("Gateway"), ASN("ASN");

        public String value;

        private SearchFor(String value){
            this.value = value;
        }
    }
    //搜索的类别
    public static enum SearchIn {
        ALL(""), NAME("Name"), MAILBOX("Mailbox"), HANDLER("!");

        public String value;
        private SearchIn(String value) {
            this.value = value;
        }
    }

    /*****************************枚举方法结束*********************/

    public String lookUpNames(String target, SearchFor category, SearchIn group, boolean exactMatch) throws IOException{
        String suffix = "";//结尾后缀
        if (!exactMatch) {
            suffix = ".";
        }

        String perfix = category.value + " " + group.value;//前缀
        String query = perfix + target + suffix;//前缀 + 关键字 + 后缀

        Socket socket = new Socket();
        try {
            SocketAddress address = new InetSocketAddress(host, port);
            socket.connect(address);

            Writer out = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ASCII"));

            //向服务器写数据
            out.write(query + "\r\n");
            out.flush();

            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                response.append(line);
                response.append("\r\n");
            }
            return response.toString();
        } finally {
            socket.close();
        }
    }



    public InetAddress getHost() {
        return host;
    }

    public void setHost(String host) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
    }
}
