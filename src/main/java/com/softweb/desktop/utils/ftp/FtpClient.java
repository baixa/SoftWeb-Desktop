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
 * The class is responsible for interacting with the FTP server and handling files for uploading and downloading from the server.
 */
@Component
public class FtpClient {

    /**
     * FTP Server URL
     */
    private final String server;

    /**
     * Connection port
     */
    private final int port;

    /**
     * FTP user login
     */
    private final String username;

    /**
     * FTP user password
     */
    private final String password;

    /**
     * Object of class org.apache.commons.net.ftp.FTP for interacting with FTP
     */
    private static FTPClient ftp;

    /**
     * Path to the directory with application images on the FTP server
     */
    public static final String IMAGE_PATH = "/images/";

    /**
     * Path to the directory with logo images on the FTP server
     */
    public static final String LOGO_PATH = "/logo/";

    /**
     * Path to the directory with application installers on the FTP server
     */
    public static final String INSTALLER_PATH = "/installers/";

    /**
     * URL to access the content uploaded to the server
     */
    public static final String WEB_PATH = "http://127.0.0.1/softweb/resources";

    /**
     * Logger that captures errors and passes them to the console
     */
    private static final Logger logger = LoggerFactory.getLogger(
            FtpClient.class);

    /**
     * Initializes an object of the FtpClient class and fills in the fields for connecting to it
     *
     * @param server URL of the server
     * @param port Connection port
     * @param username User login
     * @param password User password
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
     * Open connection to FTP server
     *
     * @throws IOException Error connecting to FTP server
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
     * Close the connection to the server
     *
     * @throws IOException Error closing FTP connection
     */
    public void close() throws IOException {
        logger.info("Disconnect FTP server");
        ftp.disconnect();
    }

    /**
     * Get a list of files in a specific directory
     *
     * @param path Directory to process
     * @return List of filenames
     * @throws IOException Error getting list of files
     */
    public Collection<String> listFiles(String path) throws IOException {
        FTPFile[] files = ftp.listFiles(path);
        return Arrays.stream(files)
                .map(FTPFile::getName)
                .collect(Collectors.toList());
    }

    /**
     * Download file to directory
     *
     * @param source Download URL
     * @param destination Destination path
     * @throws IOException Error downloading file
     */
    public void downloadFile(String source, String destination) throws IOException {
        OutputStream out = new FileOutputStream(destination);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.retrieveFile(source, out);
    }

    /**
     * Upload file to server
     *
     * @param inputStream File data stream
     * @param path Destination path on the server
     * @throws IOException Error while uploading file to server
     */
    public void putFileToPath(InputStream inputStream, String path) throws IOException {
        ftpCreateDirectoryTree(path.substring(0, path.lastIndexOf("/")));
        ftp.storeFile(path, inputStream);
    }

    /**
     * Delete file on server
     *
     * @param path URL of file to be deleted
     * @throws IOException Error while deleting file
     */
    public void deleteFile(String path) throws IOException {
        ftp.deleteFile(path);
    }

    /**
     * Create directory recursively
     *
     * @param dirTree Directory tree
     * @throws IOException Error creating directory tree
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