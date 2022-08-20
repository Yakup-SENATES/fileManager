package com.example.filemanager.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    public static byte[] compressImage(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[4*1024];
        while (!deflater.finished()){
            int size = deflater.deflate(buffer);
            outputStream.write(buffer, 0, size);
        }

        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }



    public static byte[] deCompressImage(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[4*1024];

        try {
            while (!inflater.finished()){
                int size = inflater.inflate(buffer);
                outputStream.write(buffer, 0, size);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }

        return outputStream.toByteArray();
    }


}
