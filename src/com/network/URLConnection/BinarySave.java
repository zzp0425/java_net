package com.network.URLConnection;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 周振平
 * on 2016/4/3.
 * 从web网站下载二进制文件并保存到磁盘
 */
public class BinarySave {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.baidu.com");
            saveBinary(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveBinary(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        String contentType = connection.getContentType();
        int contentLength = connection.getContentLength();

        if (contentType == null || !contentType.startsWith("text/") || contentLength == -1) {
            throw new IOException("this is not a binary file");
        }

        byte[] bytes = new byte[contentLength];

        InputStream in = new BufferedInputStream(connection.getInputStream());

        int offset = 0;

        while (offset < contentLength) {
            int bytesRead = in.read(bytes, offset, bytes.length - offset);
            if (bytesRead == -1) {
                break;
            }
            offset += bytesRead;
        }

        if (offset != contentLength) {
            throw new IOException("only read " + offset + " bytes; expected " + contentLength + "bytes");
        }

        String fileName = url.getFile();
        if (fileName == null || "".equals(fileName.trim())) {
            fileName = "D:\\trans\\binary.html";
        }

        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(bytes);
        fos.close();
    }
}
