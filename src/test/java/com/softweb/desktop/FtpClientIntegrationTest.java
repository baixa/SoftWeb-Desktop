package com.softweb.desktop;

import com.softweb.desktop.utils.ftp.FtpClient;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Integration test for FTPClient class
 */
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FtpClientIntegrationTest {

    private FtpClient ftpClient;

    /**
     * Setup ftp client and open connection
     *
     * @throws IOException
     */
    public void setup() throws IOException {
        ftpClient = new FtpClient("45.153.230.50",21, "newftpuser", "ftp");
        ftpClient.open();
    }

    /**
     * Close connection to FTP Server
     *
     * @throws IOException
     */
    public void teardown() throws IOException {
        ftpClient.close();
    }

    /**
     * Test of getting list of files from FTP Server
     *
     * @throws IOException
     */
    @Test
    public void getFilesListFromFtpServer() throws IOException {
        setup();
        Collection<String> files = ftpClient.listFiles("/");
        assertTrue((files).contains("upload"));
        teardown();
    }

    /**
     * Test of uploading file to FTP Server
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void dropFileOnFtpServer()
            throws IOException, URISyntaxException {

        setup();
        InputStream inputStream = this.getClass().getResourceAsStream("/images/logo.png");
        ftpClient.putFileToPath(inputStream, "/upload/logo.png");
        assertTrue(ftpClient.listFiles("/upload").contains("logo.png"));
        teardown();
    }

    /**
     * Test of downloading file from FTP Server
     *
     * @throws IOException
     */
    @Test
    public void getFileFromFtpServer() throws IOException {
        setup();
        ftpClient.downloadFile("/upload/logo.png", "downloaded_logo.png");
        assertTrue(new File("downloaded_logo.png").exists());
        teardown();
    }

    /**
     * Test of delete file on FTP Server
     *
     * @throws IOException
     */
    @Test
    public void removeFileFromFtpServer()
            throws IOException {

        setup();
        ftpClient.deleteFile("/upload/logo.png");
        assertFalse(ftpClient.listFiles("/upload").contains("logo.png"));
        teardown();
    }

}
