package com.network.uriAndUrl;

import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by 周振平
 * on 2016/4/2.
 * URI的各部分
 */
public class URISplitter {

    private static Logger logger = Logger.getLogger(URISplitter.class);

    public static void main(String[] args) {
        args = new String[]{"http://www.xml.com/pub/pub/a/2003/09/17/stax.html#id=_hbc", "tel:+1-00-99988-9938",
                            "urn:isbn:1-565-92870-9"};

        try {
            for (String arg : args) {
                URI u = new URI(arg);
                logger.info("the uri is " + u);

                if (u.isOpaque()) {//是不透明的
                    logger.info("this is an opaque uri");
                    logger.info("the scheme is " + u.getScheme());
                    logger.info("the scheme specific part is " + u.getSchemeSpecificPart());
                    logger.info("the fragment id is " + u.getFragment());//片段
                } else {//层次的
                    logger.info("this is a hierarchical uri");
                    logger.info("the scheme is " + u.getScheme());
                    u = u.parseServerAuthority();//强制重新解析授权机构
                    logger.info("the host is " + u.getHost());
                    logger.info("the user info is " + u.getUserInfo());
                    logger.info("the port is " + u.getPort());
                    logger.info("the path is " + u.getPath());
                    logger.info("the query string is " + u.getQuery());
                    logger.info("the fragment id is " + u.getFragment());
                }
                System.out.println();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
