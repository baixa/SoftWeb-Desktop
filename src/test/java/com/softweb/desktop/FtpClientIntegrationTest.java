package com.softweb.desktop;

import com.softweb.desktop.utils.ftp.FtpClient;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Integration test for FtpClient class
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
     */
    @Test
    public void dropFileOnFtpServer()
            throws IOException {

        setup();
        File file = new File("D:/list.txt");
        ftpClient.putFileToPath(file, "/upload/list.txt");
        assertTrue(ftpClient.listFiles("/upload").contains("list.txt"));
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
        ftpClient.downloadFile("/upload/list.txt", "downloaded_list.txt");
        assertTrue(new File("downloaded_list.txt").exists());
        new File("downloaded_list.txt").delete(); // cleanup
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
        ftpClient.deleteFile("/upload/list.txt");
        assertFalse(ftpClient.listFiles("/upload").contains("list.txt"));
        teardown();
    }

}
