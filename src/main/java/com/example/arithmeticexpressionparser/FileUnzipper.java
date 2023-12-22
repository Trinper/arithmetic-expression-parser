package com.example.arithmeticexpressionparser;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUnzipper {
    public static String unzipFile(String zipFilePath, String destFolderPath) throws IOException {
        File destFolder = new File(destFolderPath);
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = destFolderPath + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                    return filePath;
                } else {
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
        return null;
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = zipIn.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }
    }
}