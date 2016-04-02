package com.network.uriAndUrl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 周振平
 * on 2016/4/2.
 */
public class QueryString {
    private StringBuilder query = new StringBuilder();

    public synchronized void add (String key, String value) {
        if (query.toString().length() != 0) {
            query.append("&");
        }
        encode(key, value);
    }

    private synchronized void encode (String key, String value) {
        try {
            query.append(URLEncoder.encode(key, "UTF-8"));
            query.append("=");
            query.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public synchronized String getQuery () {
        return query.toString();
    }

    @Override
    public String toString() {
        return getQuery();
    }


    public static void main(String[] args) {
        QueryString query = new QueryString();
        query.add("hl", "en");
        query.add("as_1", "Java");
        query.add("as_epq", "I/O");
        String url = "http://www.google.com/search?" + query;
        System.out.println(url);
    }
}
