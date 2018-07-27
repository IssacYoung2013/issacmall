package com.imall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * author:  ywy
 * date:  2018-07-26
 * desc:
 *
 */
public class FtpUtil {

    public static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    public FtpUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FtpUtil ftpUtil = new FtpUtil(ftpIp, 21,ftpUser,ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img",fileList);
        logger.info("结束上传，上传结果：{}",result);

        return  result;
    }

    private void createDirs(FTPClient ftpClient, String remoteUpLoadPath) throws IOException {

//根据路径逐层判断目录是否存在，如果不存在则创建
//1.首先进入ftp的根目录
        ftpClient.changeWorkingDirectory("/");
        String[] dirs = remoteUpLoadPath.split("/");
        for (String dir : dirs) {
//2.创建并进入不存在的目录
            if (!ftpClient.changeWorkingDirectory(dir)) {
                ftpClient.mkd(dir);
                ftpClient.changeWorkingDirectory(dir);
            }
        }
    }

    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean upload = true;
        FileInputStream fis = null;
        // 连接Ftp服务器
        if(connectServer(this.getIp(),this.port,this.user,this.pwd)) {
            try {
                createDirs(ftpClient,remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File fileItem :
                        fileList) {
                    fis = new FileInputStream(fileItem);
                    upload = ftpClient.storeFile(fileItem.getName(),fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常",e);
                upload = false;
            }
            finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return upload;
    }

    private boolean connectServer(String ip,int port,String user,String pwd) {

        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(ftpUser,ftpPass);
        } catch (IOException e) {
            logger.error("连接FTP服务器异常",e);
        }

        return isSuccess;
    }

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}