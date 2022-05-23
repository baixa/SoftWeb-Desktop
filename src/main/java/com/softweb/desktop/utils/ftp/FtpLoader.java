package com.softweb.desktop.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpLoader {
    private static FTPClient ftpClient = new FTPClient();
    private static String encoding = System.getProperty("file.encoding");

    @Value("${connections.ftp.username}")
    private static String username = ;

    @Value("${connections.ftp.password}")
    private static String password;

    @Value("${connections.ftp.url}")
    private static String url;

    @Value("${connections.ftp.port}")
    private static int port;

    /**
     * Описание: загрузка файлов на FTP-сервер
     *
     * @Version1.0
     * @param path
     * FTP-сервер сохраняет каталог, если это корневой каталог, это "/"
     * @param filename
     * Имя файла загружено на FTP-сервер
     * @param input
     * Локальный поток файлов
     * @return возвращает true успешно, иначе false
     */
    public static boolean uploadFile(String path, String filename, InputStream input) {
        boolean result = false;

        try {
            int reply;
            ftpClient.connect(url, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding(encoding);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                System.out.println ("Ошибка подключения");
                ftpClient.disconnect();
                return result;
            }

            boolean change = ftpClient.changeWorkingDirectory(path);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (change) {
                result = ftpClient.storeFile(new String(filename.getBytes(encoding), StandardCharsets.ISO_8859_1), input);
                if (result) {
                    System.out.println ("Загрузка успешно завершена!");
                }
            }
            input.close();
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ignored) {
                }
            }
        }
        return result;
    }

    /**
     * Загрузить локальные файлы на FTP-сервер
     *
     */
    public static void testUpLoadFromDisk() {
        try {
            FileInputStream in = new FileInputStream("D:/list.txt");
            boolean flag = uploadFile("/", "lis.txt", in);
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Описание: загрузка файлов с FTP-сервера
     *
     * @Version1.0
     * @param remotePath
     * Относительный путь на FTP-сервере
     * @param fileName
     * Имя файла для загрузки
     * @param localPath
     * Сохранить на локальный путь после загрузки
     * @return
     */
    public static boolean downFile(String remotePath, String fileName,
                                   String localPath) {
        boolean result = false;
        try {
            int reply;
            ftpClient.setControlEncoding(encoding);

            ftpClient.connect(url, port);
            ftpClient.login (username, password);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                return result;
            }
            ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding), StandardCharsets.ISO_8859_1));
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }

            ftpClient.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ignored) {
                }
            }
        }
        return result;
    }

    /**
     * Загрузка файлов на FTP-сервер на локальный
     *
     */
    public static void testDownFile() {
        try {
            boolean flag = downFile("/", "lis.txt", "D:/newfolder/");
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
