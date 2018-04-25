package me.fetonxu.tank_console.util;

import java.io.*;

public class FileUtil {

    public final static int BUFFER_SIZE = 1024 * 1024;

    public static String saveFile(String baseDir, String name, byte[] content) throws Exception{
        File file = new File(baseDir, name);
        file.createNewFile();
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        outputStream.write(content);
        outputStream.flush();
        outputStream.close();

        baseDir = baseDir.endsWith("/") ? baseDir : baseDir + "/";
        return baseDir + name;
    }

    public static String saveFile(String baseDir, String name, InputStream inputStream) throws Exception{
        File file = new File(baseDir, name);
        file.createNewFile();
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));

        byte[] buffer = new byte[BUFFER_SIZE];
        int len = -1;
        while((len = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        outputStream.close();

        baseDir = baseDir.endsWith("/") ? baseDir : baseDir + "/";
        return baseDir + name;
    }
}
