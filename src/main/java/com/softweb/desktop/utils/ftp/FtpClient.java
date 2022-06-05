package com.softweb.desktop.utils.ftp;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.ApplicationImage;
import javafx.scene.image.Image;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    public static final String FTP_DIRECTORY = "/ftp/upload/SoftWeb/src/main/resources/static/";

    private static final Logger logger = LoggerFactory.getLogger(
            FtpClient.class);

    public FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public static void loadApplicationsImages(List<Application> applications) {
        if (applications == null || applications.size() == 0)
            return;

        FtpClient ftpClient = new FtpClient("45.153.230.50",21, "newftpuser", "ftp");
        try {
            ftpClient.open();
            for (Application application : applications) {
                loadApplicationData(application, ftpClient);
            }
            ftpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateApplicationData(Application application) {
        if(application == null)
            return;

        FtpClient ftpClient = new FtpClient("45.153.230.50",21, "newftpuser", "ftp");
        try {
            ftpClient.open();
            loadApplicationData(application, ftpClient);
            ftpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadApplicationData(Application application, FtpClient ftpClient) throws IOException {
        String logoPath = application.getLogoPath();
        if (logoPath != null) {
            String sourcePath = FtpClient.FTP_DIRECTORY + logoPath;
            String fileExt = logoPath.substring(logoPath.lastIndexOf("."));
            String destinationPath = ftpClient.downloadFileAsTemp(sourcePath, fileExt);
            application.setLogo(new Image(destinationPath));
        }

        if (application.getImages() != null && application.getImages().size() > 0) {
            for (ApplicationImage applicationImage : application.getImages()) {
                String path = applicationImage.getPath();
                if (path != null) {
                    String sourcePath = FtpClient.FTP_DIRECTORY + path;
                    String fileExt = path.substring(path.lastIndexOf("."));
                    String destinationPath = ftpClient.downloadFileAsTemp(sourcePath, fileExt);
                    applicationImage.setImage(new Image(destinationPath));
                }
            }
        }
    }

    public void open() throws IOException {
        ftp = new FTPClient();
        logger.info("Connect FTP server");
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
        ftp.storeFile(path, inputStream);
        ftp.sendSiteCommand("chmod 777 " + path);
    }

    public void deleteFile(String path) throws IOException {
        ftp.deleteFile(path);
    }
}