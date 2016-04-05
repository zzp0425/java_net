package com.network.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 周振平
 * on 2016/4/5.
 * 字符生成器服务端
 */
public class ChargenServer {
    private static final int port = 19;

    public static void main(String[] args) {
        byte[] rotation = new byte[95 * 2];//循环数组
        for (byte i = ' '; i <= '~'; i++) {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }

        ServerSocketChannel serverChannel;
        Selector selector = null;

        try {
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            ss.bind(address);
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<SelectionKey> readykeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readykeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            try {
                if (key.isAcceptable()) {//服务器，等待接入
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                    ByteBuffer buffer = ByteBuffer.allocate(74);
                    buffer.put(rotation, 0 , 72);
                    buffer.put((byte) '\r');
                    buffer.put((byte) '\n');
                    buffer.flip();
                    key2.attach(buffer);
                } else if (key.isWritable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    if (!buffer.hasRemaining()) {
                        //用下一行重新填充缓冲区
                        buffer.rewind();
                        //得到上一次的首字符
                        int first = buffer.get();
                        //准备改变缓冲区中的数据
                        buffer.rewind();
                        //寻找rotation中新的首字符位置
                        int position = first - ' ' + 1;
                        //将数据从rotation复制到缓冲区
                        buffer.put(rotation, position, 72);
                        //在缓冲区末尾存储一个行分隔符
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        //准备缓冲区进行写入
                        buffer.flip();
                    }
                    client.write(buffer);
                }
            } catch (IOException e) {
                key.cancel();
                try {
                    key.channel().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }
}
