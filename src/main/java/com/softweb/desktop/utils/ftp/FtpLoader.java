package com.softweb.desktop.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.softweb.desktop.database.utils.ConnectionValidator;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpLoader {
    private static FTPClient ftpClient = new FTPClient();
    private static String encoding = System.getProperty("file.encoding");

    private static final Logger logger = LoggerFactory.getLogger(
            ConnectionValidator.class);

    private static String username = "newftpuser";

    private static String password = "ftp";

    private static String url = "45.153.230.50";

    private static int port = 21;

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
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return result;
            }

            boolean change = ftpClient.changeWorkingDirectory(path);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (change) {
                result = ftpClient.storeFile(new String(filename.getBytes(encoding), StandardCharsets.ISO_8859_1), input);
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
            logger.info("Check FTP upload connection");
            FileInputStream in = new FileInputStream("D:/list.txt");
            boolean flag = uploadFile("/", "lis.txt", in);
            if(flag)
                logger.info("FTP upload testing success");
            else
                logger.warn("FTP upload testing failed");
        } catch (Exception e) {
            logger.error("FTP upload connection refused", e);
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
            logger.info("Check FTP download connection");
            boolean flag = downFile("/", "lis.txt", "D:/newfolder/");
            System.out.println(flag);
            if(flag)
                logger.info("FTP download testing success");
            else
                logger.warn("FTP download testing failed");
        } catch (Exception e) {
            logger.error("FTP download connection refused", e);
        }
    }
}
