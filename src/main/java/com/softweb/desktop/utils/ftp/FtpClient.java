package com.softweb.desktop.utils.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Класс отвечает за взаимодействие с FTP сервером и оперирование файлами для их загрузки на сервер и скачивания с него.
 */
@Component
public class FtpClient {

    /**
     * URL сервера FTP
     */
    private final String server;

    /**
     * Порт подключения
     */
    private final int port;

    /**
     * Логин пользователя FTP
     */
    private final String username;

    /**
     * Пароль пользователя FTP
     */
    private final String password;

    /**
     * Объект класса  org.apache.commons.net.ftp.FTP для взаимодействия с FTP
     */
    private static FTPClient ftp;

    /**
     * Путь до директории с изображениями приложений на FTP сервере
     */
    public static final String IMAGE_PATH = "/images/";

    /**
     * Путь до директории с изображениями логотипов на FTP сервере
     */
    public static final String LOGO_PATH = "/logo/";

    /**
     * Путь до директории с установщиками приложений на FTP сервере
     */
    public static final String INSTALLER_PATH = "/installers/";

    /**
     * URL для доступа к загруженному на сервер контенту
     */
    public static final String WEB_PATH = "http://45.67.35.2/softweb/resources";

    /**
     * Логер, фиксирующий ошибки и передающий их в консоль
     */
    private static final Logger logger = LoggerFactory.getLogger(
            FtpClient.class);

    /**
     * Инициализирует объект класса FtpClient и заполняет поля для подключения к нему
     * @param server URL сервера
     * @param port Порт подключения
     * @param username Логин пользователя
     * @param password Пароль пользователя
     */
    public FtpClient(@Value("${connections.ftp.server}") String server,
                     @Value("${connections.ftp.port}") int port,
                     @Value("${connections.ftp.username}") String username,
                     @Value("${connections.ftp.password}") String password) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * Открыть подключение к серверу FTP
     * @throws IOException Ошибка при подключении к серверу FTP
     */
    public void open() throws IOException {
        ftp = new FTPClient();
        logger.info("Connect FTP server");
        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        ftp.login(username, password);

        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    /**
     * Закрыть подключение к серверу
     * @throws IOException Ошибка при закрытии соединения FTP
     */
    public void close() throws IOException {
        logger.info("Disconnect FTP server");
        ftp.disconnect();
    }

    /**
     * Получить список файлов в конкретной директории
     * @param path Обрабатываемый каталог
     * @return Список названий файлов
     * @throws IOException Ошибка при получении списка файлов
     */
    public Collection<String> listFiles(String path) throws IOException {
        FTPFile[] files = ftp.listFiles(path);
        return Arrays.stream(files)
                .map(FTPFile::getName)
                .collect(Collectors.toList());
    }

    /**
     * Скачать файл в директорию
     * @param source URL скачиваемого файла
     * @param destination Путь назначения
     * @throws IOException Ошибка при скачивании файла
     */
    public void downloadFile(String source, String destination) throws IOException {
        OutputStream out = new FileOutputStream(destination);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.retrieveFile(source, out);
    }

    /**
     * Загрузить файл на сервер
     * @param inputStream Поток данных файла
     * @param path Путь назначения на сервере
     * @throws IOException Ошибка при загрузке файла на сервер
     */
    public void putFileToPath(InputStream inputStream, String path) throws IOException {
        ftpCreateDirectoryTree(path.substring(0, path.lastIndexOf("/")));
        ftp.storeFile(path, inputStream);
    }

    /**
     * Удалить файл на сервере
     * @param path URL удаляемого файла
     * @throws IOException Ошибка при удалении файла
     */
    public void deleteFile(String path) throws IOException {
        ftp.deleteFile(path);
    }

    /**
     * Создать рекурсивно каталог
     *
     * @param dirTree Дерево каталогов
     * @throws IOException Ошибка при создании дерева каталогов
     */
    private void ftpCreateDirectoryTree(String dirTree) throws IOException {

        boolean dirExists = true;

        String[] directories = dirTree.split("/");
        for (String dir : directories ) {
            if (!dir.isEmpty() ) {
                if (dirExists) {
                    dirExists = ftp.changeWorkingDirectory(dir);
                }
            }
            if (!dirExists) {
                if (!ftp.makeDirectory(dir)) {
                    logger.error("Unable to create remote directory '" + dir + "'.  error='" + ftp.getReplyString()+"'");
                }
                if (!ftp.changeWorkingDirectory(dir)) {
                    logger.error("Unable to change into newly created remote directory '" + dir + "'.  error='" + ftp.getReplyString()+"'");
                }
            }
        }

        ftp.changeWorkingDirectory("/");
    }
}