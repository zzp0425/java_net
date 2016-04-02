package com.network.uriAndUrl;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 周振平
 * on 2016/4/2.
 */
public class EncoderTest {
    private static Logger logger = Logger.getLogger(EncoderTest.class);

    public static void main(String[] args) {
        print("this string has spaces");
        print("this*string*has*asterisks");
        print("this%string%has%percent%signs");
        print("this+string+has+pluses");
        print("this/string/has/slashes");
        print("this\"string\"has\"quoe\"marks");
        print("this:string:has:colons");
        print("this~string~has~tildes");
        print("this(string)has(parentheses)");
        print("this.string.has.periods");
        print("this&string&has&ampersands");
        print("this=string=has=equals=signs");
    }

    private static void print(String str){
        try {
            logger.info(URLEncoder.encode(str, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
