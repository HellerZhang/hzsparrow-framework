/**
 * $Id:$
 * Copyright 2019 HellerZhang. All rights reserved.
 */
package com.hzsparrow.framework.fastdfs.util;

import org.csource.common.NameValuePair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.zjt.fastdfs.pool.FastdfsPool;
import com.zjt.fastdfs.pool.FastdfsPoolConfig;
import com.zjt.fastdfs.pool.StorageClient;

/**
 * <strong>类概要： FastDFS Java客户端工具类</strong> <br>
 * <strong>创建时间： 2016-9-26 上午10:26:48</strong> <br>
 * 
 * @Project springmvc-main(com.wl.bean)
 * @author Wang Liang
 * @version 1.0.0
 */
public class FileManager {

    private static final String SEPARATOR = "/";

    private static FastdfsPool fastdfsPool;

    static {
        try {
            FastdfsPoolConfig config = new FastdfsPoolConfig();
            fastdfsPool = new FastdfsPool(config);
            System.out.println("--config.maxWait=" + config.getMaxWait());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * <strong>方法概要： 文件上传</strong> <br>
     * <strong>创建时间： 2016-9-26 上午10:26:11</strong> <br>
     * 
     * @param file
     * @return fileAbsolutePath
     * @author Wang Liang
     * @throws Exception 
     */
    public static String upload(FastDFSFile file, NameValuePair[] valuePairs) throws Exception {
        String[] uploadResults = null;

        StorageClient client = fastdfsPool.getResource();
        uploadResults = client.upload_file(file.getContent(), file.getExt(), valuePairs);
        fastdfsPool.returnBrokenResource(client);

        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        String fileAbsolutePath =
                //PROTOCOL +
                //TRACKER_NGNIX_ADDR +
                //trackerServer.getInetSocketAddress().getHostName() +
                //SEPARATOR + TRACKER_NGNIX_PORT +
                //SEPARATOR + 
                groupName + SEPARATOR + remoteFileName;
        return fileAbsolutePath;
    }

    public static String upload(FastDFSFile file)  throws Exception {
        return upload(file, null);
    }

    /**
     * <strong>方法概要： 文件下载</strong> <br>
     * <strong>创建时间： 2016-9-26 上午10:28:21</strong> <br>
     * 
     * @param groupName
     * @param remoteFileName
     * @return returned value comment here
     * @author Wang Liang
     */
    public static ResponseEntity<byte[]> download(String groupName, String remoteFileName, String specFileName)
            throws Exception {
        byte[] content = null;
        HttpHeaders headers = new HttpHeaders();
        StorageClient client = fastdfsPool.getResource();
        content = client.download_file(groupName, remoteFileName);
        fastdfsPool.returnBrokenResource(client);
        headers.setContentDispositionFormData("attachment", new String(specFileName.getBytes("UTF-8"), "iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
    }

    public static ResponseEntity<byte[]> download(String filePath, String specFileName) throws Exception {
        String groupName = filePath.substring(0, filePath.indexOf("/"));
        String remoteFileName = filePath.substring(filePath.indexOf("/") + 1);
        return download(groupName, remoteFileName, specFileName);
    }
}
