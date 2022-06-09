package com.softweb.desktop.utils.ftp;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.ApplicationImage;
import javafx.scene.image.Image;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FtpClient {

    private String server;
    private int port;
    private String user;
    private String password;
    private FTPClient ftp;

    public static final String IMAGE_PATH = "/images/";
    public static final String LOGO_PATH = "/logo/";
    public static final String INSTALLER_PATH = "/installers/";

    public static final String WEB_PATH = "http://45.67.35.2/softweb/resources";

    private static final Logger logger = LoggerFactory.getLogger(
            FtpClient.class);

    public FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public void open() throws IOException {
        ftp = new FTPClient();
        logger.info("Connect FTP server");
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        ftp.login(user, password);

        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    public void close() throws IOException {
        logger.info("Disconnect FTP server");
        ftp.disconnect();
    }

    public Collection<String> listFiles(String path) throws IOException {
        FTPFile[] files = ftp.listFiles(path);
        return Arrays.stream(files)
                .map(FTPFile::getName)
                .collect(Collectors.toList());
    }

    public String downloadFileAsTemp(String source, String fileExt) throws IOException {
        File temp = File.createTempFile("SoftWeb-",fileExt);
        temp.deleteOnExit();
        String destinationPath = temp.getAbsolutePath();
        downloadFile(source, destinationPath);

        return destinationPath;
    }

    public void downloadFile(String source, String destination) throws IOException {
        OutputStream out = new FileOutputStream(destination);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.retrieveFile(source, out);
    }

    public void putFileToPath(InputStream inputStream, String path) throws IOException {
        ftpCreateDirectoryTree(path.substring(0, path.lastIndexOf("/")));
        ftp.storeFile(path, inputStream);
    }

    public void deleteFile(String path) throws IOException {
        ftp.deleteFile(path);
    }

    /**
     * Utility to create an arbitrary directory hierarchy on the remote ftp server
     *
     * @param dirTree
     *
     * @throws Exception
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