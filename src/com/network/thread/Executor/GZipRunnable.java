package com.network.thread.Executor;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * Created by 周振平
 * on 2016/3/30.
 */
public class GZipRunnable implements Runnable {
    private final File file;

    public GZipRunnable(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        if (!file.getName().endsWith(".gz")) {
            File output = new File(file.getParent(), file.getName() + ".gz");
            if (!output.exists()) {
                try {
                    InputStream in = new BufferedInputStream(new FileInputStream(file));
                    BufferedOutputStream bos =
                            new BufferedOutputStream(
                                    new GZIPOutputStream(new FileOutputStream(output)));
                    int b;
                    while ((b = in.read()) != -1) {
                        System.out.println(b);
                        bos.write(b);
                    }
                    bos.flush();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
