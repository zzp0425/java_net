package com.network.uriAndUrl;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周振平
 * on 2016/4/2.
 * ProxySelector会记住可以连接的URL
 */
public class LocalPorxySelector extends ProxySelector{

    //private static Logger logger = Logger.getLogger(LocalPorxySelector.class);

    private List<URI> failed = new ArrayList<URI>();

    @Override
    public List<Proxy> select(URI uri) {
        List<Proxy> proxys = new ArrayList<Proxy>();

        if (failed.contains(uri) || !"http".equalsIgnoreCase(uri.getScheme())) {
            proxys.add(Proxy.NO_PROXY);
        } else {
            SocketAddress socketAddress = new InetSocketAddress("proxy.example.com", 8000);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
            proxys.add(proxy);
        }

        return proxys;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        failed.add(uri);
    }
}
