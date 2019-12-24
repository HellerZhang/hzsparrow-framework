/**
 * $Id:$
 * Copyright 2019 HellerZhang. All rights reserved.
 */
package com.hzsparrow.framework.utils.files;

import com.hzsparrow.framework.fastdfs.util.FastDFSFile;
import com.hzsparrow.framework.fastdfs.util.FileManager;
import com.hzsparrow.framework.utils.ftp.FTPUtil;
import com.hzsparrow.framework.utils.ftp.ReadFTPFile;
import com.hzsparrow.framework.utils.ftp.WriteFTPFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Heller.Zhang
 * @Deprecated This utils is Deprecated.Please use HzSparrowFileUploadUtils.
 * @since 2017年10月16日 下午7:35:32
 */
@Deprecated
public class FileUploadUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private WriteFTPFile ftpWriter;

    private ReadFTPFile ftpReader;

    private Map<String, String[]> extMap = new HashMap<String, String[]>();

    private String rootPath = "d:\\hzsparrow\\upload";

    private String secondPath = "upload";

    private Integer fileServerType = 1;

    private Long maxFileSize = 1073741824L;

    /**
     * Consturctor
     */
    public FileUploadUtils() {
        init();
    }

    /**
     * 初始化方法，设置允许上传的文件类型
     *
     * @author Heller.Zhang
     * @since 2017年10月6日 下午2:54:29
     */
    private void init() {
        // 初始化允许上传的文件扩展名
        extMap.put("image", new String[]{".gif", ".jpg", ".jpeg", ".png", ".bmp", ".jfif"});
        extMap.put("flash", new String[]{".swf", ".flv"});
        extMap.put("media", new String[]{".swf", ".flv", ".mp3", ".wav", ".wma", ".wmv", ".mid", ".avi", ".mpg",
                ".asf", ".rm", ".rmvb"});
        extMap.put("file", new String[]{".doc", ".docx", ".xls", ".xlsx", ".ppt", ".htm", ".html", ".txt", ".zip",
                ".rar", ".gz", ".bz2", ".7z", ".pdf", ".jrxml", ".jasper"});

    }

    /**
     * 增加其他允许的格式
     *
     * @param types
     * @author Heller.Zhang
     * @since 2019年1月20日 上午1:34:33
     */
    public void putOtherTypes(String[] types) {
        String[] other = extMap.get("other");
        if (other == null) {
            other = types;
        } else {
            if (Arrays.asList(other).containsAll(Arrays.asList(types))) {
                return;
            }
            List<String> otherList = Arrays.asList(other);
            otherList = new ArrayList<String>(otherList);

            List<String> typeList = Arrays.asList(types);
            if (typeList == null) {
                return;
            }
            otherList.addAll(typeList);
            other = (String[]) otherList.toArray(new String[otherList.size()]);
        }
        extMap.put("other", other);
    }

    /**
     * HTTP上传文件时，检测文件的合法性 合法返回文件扩展名（带点），不合法抛出异常
     *
     * @param file
     * @return String
     * @author Heller.Zhang
     * @since 2017年10月6日 下午3:00:27
     */
    private String checkFileLegality(MultipartFile file) {
        if (file.getSize() > maxFileSize) {
            throw new RuntimeException("文件大小超过允许上传的限制！");
        }
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
                file.getOriginalFilename().length());
        boolean isAllowedFileType = false;
        for (String key : extMap.keySet()) {
            if (Arrays.asList(extMap.get(key)).contains(fileExt)) {
                isAllowedFileType = true;
                break;
            }
        }
        if (!isAllowedFileType) {
            throw new RuntimeException(fileExt + "是禁止上传的格式。");
        }
        return fileExt;
    }

    /**
     * 接收一个输入流，在本地指定的路径生成文件
     *
     * @param in
     * @param destFilePath
     */
    private void localUpload(InputStream in, String destFilePath) {
        File uploadFile;
        if (destFilePath.trim().startsWith(secondPath)) {
            uploadFile = new File(rootPath + File.separator + destFilePath);
        } else {
            uploadFile = new File(destFilePath);
        }
        File uploadFolder = uploadFile.getParentFile();
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        try {
            FileOutputStream fs = new FileOutputStream(uploadFile);
            byte[] buffer = new byte[1024 * 1024];
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
            fs.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 本地上传（不指定目录）的接口，接收一个MultipartFile对象作为参数，用于开放给前端上传文件时调用
     *
     * @param file
     * @return FileInfoModel
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:12:34
     */
    public FileInfoModel localUpload(MultipartFile file) {
        return localUpload(file, null);
    }

    public List<FileInfoModel> localUpload(MultipartFile[] files) {
        return localUpload(files, null);
    }

    /**
     * 本地上传（指定目录）的接口，接收一个MultipartFile对象作为参数，用于开放给前端上传文件时调用
     *
     * @param file
     * @param destFolder
     * @return FileInfoModel
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:12:55
     */
    public FileInfoModel localUpload(MultipartFile file, String destFolder) {
        String fileExt = checkFileLegality(file);
        FileInfoModel uploadDTO = new FileInfoModel();
        String fileName = createRandomFileName() + fileExt;
        String destFile;
        if (StringUtils.isBlank(destFolder)) {
            destFile = createUploadFolderPath() + File.separator + fileName;
        } else {
            destFile = destFolder + File.separator + fileName;
        }
        try {
            InputStream in = file.getInputStream();
            localUpload(in, destFile);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
        uploadDTO.setByteSize(file.getSize());
        uploadDTO.setPath(destFile);
        uploadDTO.setOldName(file.getOriginalFilename());
        uploadDTO.setNewName(fileName);
        uploadDTO.setUploadTime(new Date());
        return uploadDTO;
    }

    public List<FileInfoModel> localUpload(MultipartFile[] files, String destFolder) {
        for (MultipartFile file : files) {
            checkFileLegality(file);
        }
        List<FileInfoModel> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
                    file.getOriginalFilename().length());
            FileInfoModel uploadDTO = new FileInfoModel();
            String fileName = createRandomFileName() + fileExt;
            String destFile;
            if (StringUtils.isBlank(destFolder)) {
                destFile = createUploadFolderPath() + File.separator + fileName;
            } else {
                destFile = destFolder + File.separator + fileName;
            }
            try {
                InputStream in = file.getInputStream();
                localUpload(in, destFile);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("上传文件失败！", e);
            }
            uploadDTO.setByteSize(file.getSize());
            uploadDTO.setPath(destFile);
            uploadDTO.setOldName(file.getOriginalFilename());
            uploadDTO.setNewName(fileName);
            uploadDTO.setUploadTime(new Date());
            list.add(uploadDTO);
        }
        return list;
    }

    /**
     * 本地上传（不指定目录）的接口，接收一个File对象作为参数，用于后端处理文件时使用
     *
     * @param file
     * @return FileInfoModel
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:12:34
     */
    public FileInfoModel localUpload(File file) {
        return localUpload(file, null);
    }

    /**
     * 本地上传（指定目录）的接口，接收一个File对象作为参数，用于后端处理文件时使用
     *
     * @param file
     * @param destFolder
     * @return FileInfoModel
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:12:55
     */
    public FileInfoModel localUpload(File file, String destFolder) {
        FileInfoModel uploadDTO = new FileInfoModel();
        String fileExt = file.getName().substring(file.getName().indexOf('.'), file.getName().length());
        String fileName = createRandomFileName() + fileExt;
        String destFile;
        if (StringUtils.isBlank(destFolder)) {
            destFile = createUploadFolderPath() + File.separator + fileName;
        } else {
            destFile = destFolder + File.separator + fileName;
        }
        try {
            InputStream in = new FileInputStream(file);
            localUpload(in, destFile);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
        uploadDTO.setByteSize(file.getTotalSpace());
        uploadDTO.setPath(destFile);
        uploadDTO.setOldName(file.getName());
        uploadDTO.setNewName(fileName);
        uploadDTO.setUploadTime(new Date());
        return uploadDTO;
    }

    /**
     * 接收一个输入流，向FTP服务器的指定路径上传文件
     *
     * @param in
     * @param destFilePath
     * @author Heller.Zhang
     * @since 2017年10月6日 下午2:27:57
     */
    private void ftpUpload(InputStream in, String destFilePath) {
        ftpWriter.upload(destFilePath, in);
    }

    /**
     * 向FTP服务器上传文件（不指定路径）的接口，接收一个MultipartFile对象作为参数，用于开放给前端上传文件时调用
     *
     * @param file
     * @return ResultDTO<Object>
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:14:35
     */
    private FileInfoModel remoteUploadFtp(MultipartFile file) {
        return remoteUploadFtp(file, null);
    }

    private List<FileInfoModel> remoteUploadFtp(MultipartFile[] files) {
        return remoteUploadFtp(files, null);
    }

    /**
     * 向FTP服务器上传文件（指定路径）的接口，接收一个MultipartFile对象作为参数，用于开放给前端上传文件时调用
     *
     * @param file
     * @param destFolder
     * @return FileInfoModel
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:15:24
     */
    private FileInfoModel remoteUploadFtp(MultipartFile file, String destFolder) {
        String fileExt = checkFileLegality(file);
        FileInfoModel uploadDTO = new FileInfoModel();
        String fileName = createRandomFileName() + fileExt;
        String destFile;
        if (StringUtils.isBlank(destFolder)) {
            destFile = createUploadFolderPath() + FTPUtil.strSeparator + fileName;
        } else {
            destFile = destFolder + FTPUtil.strSeparator + fileName;
        }
        try {
            InputStream in = file.getInputStream();
            String ftpPath = FTPUtil.strSeparator + destFile.replace(File.separatorChar, '/');
            ftpUpload(in, ftpPath);
            uploadDTO.setByteSize(file.getSize());
            uploadDTO.setPath(ftpPath);
            uploadDTO.setOldName(file.getOriginalFilename());
            uploadDTO.setNewName(fileName);
            uploadDTO.setUploadTime(new Date());
            return uploadDTO;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
    }

    private List<FileInfoModel> remoteUploadFtp(MultipartFile[] files, String destFolder) {
        for (MultipartFile file : files) {
            String fileExt = checkFileLegality(file);
        }
        List<FileInfoModel> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
                    file.getOriginalFilename().length());
            FileInfoModel uploadDTO = new FileInfoModel();
            String fileName = createRandomFileName() + fileExt;
            String destFile;
            if (StringUtils.isBlank(destFolder)) {
                destFile = createUploadFolderPath() + FTPUtil.strSeparator + fileName;
            } else {
                destFile = destFolder + FTPUtil.strSeparator + fileName;
            }
            try {
                InputStream in = file.getInputStream();
                String ftpPath = FTPUtil.strSeparator + destFile.replace(File.separatorChar, '/');
                ftpUpload(in, ftpPath);
                uploadDTO.setByteSize(file.getSize());
                uploadDTO.setPath(ftpPath);
                uploadDTO.setOldName(file.getOriginalFilename());
                uploadDTO.setNewName(fileName);
                uploadDTO.setUploadTime(new Date());
                list.add(uploadDTO);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("上传文件失败！", e);
            }
        }
        return list;
    }

    /**
     * 向FTP服务器上传文件（不指定路径）的接口，接收一个File对象作为参数，用于后端处理文件时使用
     *
     * @param file
     * @return ResultDTO<Object>
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:14:35
     */
    private FileInfoModel remoteUploadFtp(File file) {
        return remoteUploadFtp(file, null);
    }

    /**
     * 向FTP服务器上传文件（指定路径）的接口，接收一个File对象作为参数，用于后端处理文件时使用
     *
     * @param file
     * @param destFolder
     * @return FileInfoModel
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:15:24
     */
    private FileInfoModel remoteUploadFtp(File file, String destFolder) {
        FileInfoModel uploadDTO = new FileInfoModel();
        String fileExt = file.getName().substring(file.getName().indexOf('.'), file.getName().length());
        String fileName = createRandomFileName() + fileExt;
        String destFile;
        if (StringUtils.isBlank(destFolder)) {
            destFile = createUploadFolderPath() + FTPUtil.strSeparator + fileName;
        } else {
            destFile = destFolder + FTPUtil.strSeparator + fileName;
        }
        try {
            InputStream in = new FileInputStream(file);
            String ftpPath = FTPUtil.strSeparator + destFile.replace(File.separatorChar, '/');
            ftpUpload(in, ftpPath);
            uploadDTO.setByteSize(file.getTotalSpace());
            uploadDTO.setPath(ftpPath);
            uploadDTO.setOldName(file.getName());
            uploadDTO.setNewName(fileName);
            uploadDTO.setUploadTime(new Date());
            return uploadDTO;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
    }

    /**
     * 接收一个字节数组，向FastDFS文件服务器上传文件，并返回保存的文件路径
     *
     * @param source
     * @param fileName
     * @param fileExt
     * @return String
     * @throws Exception
     * @author Heller.Zhang
     * @since 2017年10月6日 下午2:47:16
     */
    private String fastDFSUpload(byte[] source, String fileName, String fileExt) throws Exception {
        FastDFSFile dfsFile = new FastDFSFile(source, fileExt);

        NameValuePair[] metaList = new NameValuePair[4];
        metaList[0] = new NameValuePair("fileName", fileName);
        metaList[1] = new NameValuePair("fileLength", String.valueOf(source.length));
        metaList[2] = new NameValuePair("fileExt", fileExt.substring(1, fileExt.length()));
        metaList[3] = new NameValuePair("fileAuthor", "hzsparrow");
        String filePath = FileManager.upload(dfsFile);
        return filePath;
    }

    /**
     * 向FastDFS文件服务器上传文件的接口，接收一个MultipartFile对象作为参数，用于开放给前端上传文件时调用
     *
     * @param file
     * @return FileInfoModel
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:18:48
     */
    private FileInfoModel remoteUploadFastDfs(MultipartFile file) throws Exception {
        String fileExt = checkFileLegality(file);
        FileInfoModel uploadDTO = new FileInfoModel();
        String fileName = createRandomFileName();
        try {
            byte[] sources = file.getBytes();

            String filePath = fastDFSUpload(sources, fileName, fileExt);
            uploadDTO.setByteSize(file.getSize());
            uploadDTO.setPath(filePath);
            uploadDTO.setOldName(file.getOriginalFilename());
            uploadDTO.setNewName(fileName + fileExt);
            uploadDTO.setUploadTime(new Date());
            return uploadDTO;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
    }

    private List<FileInfoModel> remoteUploadFastDfs(MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            String fileExt = checkFileLegality(file);
        }
        List<FileInfoModel> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
                    file.getOriginalFilename().length());
            FileInfoModel uploadDTO = new FileInfoModel();
            String fileName = createRandomFileName();
            try {
                byte[] sources = file.getBytes();

                String filePath = fastDFSUpload(sources, fileName, fileExt);
                uploadDTO.setByteSize(file.getSize());
                uploadDTO.setPath(filePath);
                uploadDTO.setOldName(file.getOriginalFilename());
                uploadDTO.setNewName(fileName + fileExt);
                uploadDTO.setUploadTime(new Date());
                list.add(uploadDTO);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("上传文件失败！", e);
            }
        }
        return list;
    }

    /**
     * 向FastDFS文件服务器上传文件的接口，接收一个File对象作为参数，用于后端处理文件时使用
     *
     * @param file
     * @return FileInfoModel
     * @author Heller.Zhang
     * @since 2017年10月6日 上午10:18:48
     */
    private FileInfoModel remoteUploadFastDfs(File file) throws Exception {
        FileInfoModel uploadDTO = new FileInfoModel();
        try {
            String fileExt = file.getName().substring(file.getName().indexOf('.'), file.getName().length());
            String fileName = createRandomFileName();
            byte[] sources = FileUtils.readFileToByteArray(file);

            String filePath = fastDFSUpload(sources, fileName, fileExt);
            uploadDTO.setByteSize(file.getTotalSpace());
            uploadDTO.setPath(filePath);
            uploadDTO.setOldName(file.getName());
            uploadDTO.setNewName(fileName + fileExt);
            uploadDTO.setUploadTime(new Date());
            return uploadDTO;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
    }

    /**
     * 从本地服务器下载文件，接收一个HttpServletResponse对象，用于开放给前端下载功能使用
     *
     * @param resp
     * @param fileOldName
     * @param filePath
     * @author Heller.Zhang
     * @since 2017年10月6日 下午3:11:45
     */
    public void localDownload(HttpServletRequest request, HttpServletResponse resp, String fileOldName, String filePath) {
        InputStream in = null;
        try {
            String fileExt = fileOldName.substring(fileOldName.indexOf('.'));
            if (filePath.trim().startsWith(secondPath)) {
                in = new FileInputStream(rootPath + File.separator + filePath);
            } else {
                in = new FileInputStream(filePath);
            }

            resp.setContentType("UTF-8");
            setRespContentType(resp, fileExt);

            resp.setHeader("Content-Disposition", "attachment;filename=\"".concat(getDownloadFileName(request, fileOldName)));
            resp.setHeader("Connection", "close");
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            OutputStream out = resp.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("下载文件失败！", e);
        } finally {
            if (!(in == null)) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 下载本地指定文件
     *
     * @param resp        响应对象
     * @param fileOldName 文件名
     * @param file        文件对象
     * @author Heller.Zhang
     * @since 2019年7月31日 上午11:12:56
     */
    public void localDownload(HttpServletRequest request, HttpServletResponse resp, String fileOldName, File file) {
        InputStream in = null;

        try {
            String fileExt = fileOldName.substring(fileOldName.indexOf('.'), fileOldName.length());
            in = new FileInputStream(file);
            resp.setContentType("UTF-8");
            setRespContentType(resp, fileExt);

            resp.setHeader("Content-Disposition", "attachment;filename="
                    .concat(getDownloadFileName(request, fileOldName)));
            resp.setHeader("Connection", "close");
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            OutputStream out = resp.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("下载文件失败！", e);
        } finally {
            if (!(in == null)) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 从FTP服务器下载文件，接收一个HttpServletResponse对象，用于开放给前端下载功能使用
     *
     * @param resp        响应对象
     * @param fileOldName 文件旧名
     * @param filePath    文件存储地址
     * @author Heller.Zhang
     * @since 2017年10月6日 下午3:39:39
     */
    private void ftpDownload(HttpServletRequest request, HttpServletResponse resp, String fileOldName, String filePath) {
        InputStream in = null;
        try {
            String fileExt = fileOldName.substring(fileOldName.lastIndexOf('.'));
            String ftpPath = filePath.replace(File.separator, FTPUtil.strSeparator);

            in = ftpReader.readFileForFTP(ftpPath);
            if (in == null) {
                throw new RuntimeException("下载文件失败，未找到要下载的文件：" + filePath);
            }
            resp.setContentType("UTF-8");
            setRespContentType(resp, fileExt);

            resp.setHeader("Content-Disposition", "attachment;filename="
                    .concat(getDownloadFileName(request, fileOldName)));
            resp.setHeader("Connection", "close");
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            OutputStream out = resp.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("下载文件失败！", e);
        } finally {
            if (!(in == null)) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 从FTP服务器下载文件，并返回下载完成后的文件路径，用于后端的文件处理
     *
     * @author Heller.Zhang
     * @since 2017年10月6日 下午3:39:39
     */
    private String ftpDownload(String destFileName, String destFolder, String filePath) {
        InputStream in = null;
        try {
            if (!destFolder.endsWith(File.separator)) {
                destFolder += File.separator;
            }
            String destPath = destFolder + destFileName;

            in = ftpReader.readFileForFTP(filePath);
            if (in == null) {
                throw new RuntimeException("下载文件失败，未找到要下载的文件：" + filePath);
            }
            OutputStream out = FileUtils.openOutputStream(FileUtils.getFile(destPath));
            int len;
            byte[] buffer = new byte[1024 * 1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            return destPath;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("下载文件失败！", e);
        } finally {
            if (!(in == null)) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 从FastDFS文件服务器下载文件，接收一个HttpServletResponse对象，用于开放给前端下载功能使用
     *
     * @author Heller.Zhang
     * @since 2017年10月6日 下午3:42:26
     */
    private void fastDFSDownload(HttpServletRequest request, HttpServletResponse resp, String fileOldName, String filePath) {
        OutputStream out = null;
        try {
            String fileExt = fileOldName.substring(fileOldName.indexOf('.'), fileOldName.length());

            ResponseEntity<byte[]> res = FileManager.download(filePath, fileOldName);
            resp.setContentType("UTF-8");
            setRespContentType(resp, fileExt);

            resp.setHeader("Content-Disposition", "attachment;filename="
                    .concat(getDownloadFileName(request, fileOldName)));
            resp.setHeader("Connection", "close");
            out = resp.getOutputStream();
            out.write(res.getBody());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("下载文件失败！", e);
        }
    }

    /**
     * 从FastDFS文件服务器下载文件，并返回下载完成后的文件路径，用于后端的文件处理
     *
     * @author Heller.Zhang
     * @since 2017年10月6日 下午3:42:26
     */
    private String fastDFSDownload(String destFileName, String destFolder, String filePath) {
        try {
            if (!destFolder.endsWith(File.separator)) {
                destFolder += File.separator;
            }
            String destPath = destFolder + destFileName;
            ResponseEntity<byte[]> res = FileManager.download(filePath, destFileName);
            FileUtils.writeByteArrayToFile(FileUtils.getFile(destPath), res.getBody());
            return destPath;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("下载文件失败！", e);
        }
    }

    /**
     * 按日期在upload文件夹下生成上传文件的文件夹，并返回路径
     *
     * @return String
     * @author Heller.Zhang
     * @since 2017年10月6日 上午11:24:02
     */
    public String createUploadFolderPath() {
        String date = dateFormat.format(new Date());
        String folder = secondPath + File.separator + date;
        File f1 = new File(rootPath + File.separator + folder + File.separator + date);
        if (!f1.exists()) {
            f1.mkdirs();
        }
        return folder;
    }

    /**
     * 生成随机文件名
     *
     * @return String
     * @author Heller.Zhang
     * @since 2017年10月6日 上午11:27:30
     */
    private String createRandomFileName() {
        int r = new Random().nextInt(1000);
        return dateTimeFormat.format(new Date()) + "_" + r;
    }

    /**
     * 生成上传文件路径
     *
     * @param fileExt
     * @return String
     * @author Heller.Zhang
     * @since 2017年10月6日 上午11:33:50
     */
    public String createRandomFileUrl(String fileExt) {
        StringBuilder strBud = new StringBuilder(createUploadFolderPath() + File.separator + createRandomFileName());
        if (StringUtils.isNotBlank(fileExt)) {
            if (fileExt.indexOf('.') < 0) {
                strBud.append('.');
            }
            strBud.append(fileExt);
        }
        return strBud.toString();
    }

    /**
     * 接收一个MultipartFile对象作为参数，用于开放给前端上传文件时调用
     *
     * @param file
     * @return UcUploadifyUploadDTO
     * @author Heller.Zhang
     * @since 2017年10月6日 下午7:33:14
     */
    public FileInfoModel upload(MultipartFile file) throws Exception {
        if (fileServerType == 2) {
            return remoteUploadFtp(file);
        } else if (fileServerType == 3) {
            return remoteUploadFastDfs(file);
        } else {
            return localUpload(file);
        }
    }

    public List<FileInfoModel> upload(MultipartFile[] files) throws Exception {
        if (fileServerType == 2) {
            return remoteUploadFtp(files);
        } else if (fileServerType == 3) {
            return remoteUploadFastDfs(files);
        } else {
            return localUpload(files);
        }
    }

    /**
     * 接收一个MultipartFile对象作为参数，用于开放给前端上传文件时调用
     *
     * @param file
     * @return UcUploadifyUploadDTO
     * @author Heller.Zhang
     * @since 2017年10月6日 下午7:33:14
     */
    public FileInfoModel upload(MultipartFile file, String destFolder) throws Exception {
        if (fileServerType == 2) {
            return remoteUploadFtp(file, destFolder);
        } else if (fileServerType == 3) {
            return remoteUploadFastDfs(file);
        } else {
            return localUpload(file, destFolder);
        }
    }

    /**
     * 服务器端上传（不指定目录）的接口，接收一个File对象作为参数，用于后端处理文件时使用
     *
     * @param file
     * @return UcUploadifyUploadDTO
     * @author Heller.Zhang
     * @since 2017年10月6日 下午7:33:56
     */
    public FileInfoModel upload(File file) throws Exception {
        if (fileServerType == 2) {
            return remoteUploadFtp(file);
        } else if (fileServerType == 3) {
            return remoteUploadFastDfs(file);
        } else {
            return localUpload(file);
        }
    }

    /**
     * 服务器端上传（指定目录）的接口，接收一个File对象作为参数，用于后端处理文件时使用
     *
     * @param file
     * @param destFolder
     * @return UcUploadifyUploadDTO
     * @author Heller.Zhang
     * @since 2017年10月6日 下午7:34:11
     */
    public FileInfoModel upload(File file, String destFolder) throws Exception {
        if (fileServerType == 2) {
            return remoteUploadFtp(file, destFolder);
        } else if (fileServerType == 3) {
            return remoteUploadFastDfs(file);
        } else {
            return localUpload(file, destFolder);
        }
    }

    /**
     * 下载文件，接收一个HttpServletResponse对象，用于开放给前端下载功能使用
     *
     * @param resp
     * @param fileOldName
     * @param filePath
     * @author Heller.Zhang
     * @since 2017年10月6日 下午7:36:46
     */
    public void download(HttpServletResponse resp, String fileOldName, String filePath) {
        if (fileServerType == 2) {
            ftpDownload(null, resp, fileOldName, filePath);
        } else if (fileServerType == 3) {
            fastDFSDownload(null, resp, fileOldName, filePath);
        } else {
            localDownload(null, resp, fileOldName, filePath);
        }
    }

    public void download(HttpServletRequest request, HttpServletResponse resp, String fileOldName, String filePath) {
        if (fileServerType == 2) {
            ftpDownload(request, resp, fileOldName, filePath);
        } else if (fileServerType == 3) {
            fastDFSDownload(request, resp, fileOldName, filePath);
        } else {
            localDownload(request, resp, fileOldName, filePath);
        }
    }

    /**
     * 下载文件，并返回下载完成后的文件路径，用于后端的文件处理
     *
     * @param destFileName
     * @param destFolder
     * @param filePath
     * @return String
     * @author Heller.Zhang
     * @since 2017年10月6日 下午7:37:18
     */
    public String download(String destFileName, String destFolder, String filePath) {
        if (fileServerType == 2) {
            return ftpDownload(destFileName, destFolder, filePath);
        } else if (fileServerType == 3) {
            return fastDFSDownload(destFileName, destFolder, filePath);
        } else {
            String destPath = destFolder + destFileName;
            File srcFile = FileUtils.getFile(rootPath + File.separator + filePath);
            File destFile = FileUtils.getFile(destPath);
            try {
                FileUtils.copyFile(srcFile, destFile);
            } catch (IOException e) {
                throw new RuntimeException("复制文件发生错误", e);
            }
            return destPath;
        }
    }

    /**
     * 根据文件类型为响应设置内容类型
     *
     * @param resp
     * @param fileExt
     * @author Heller.Zhang
     * @since 2017年10月6日 下午3:23:36
     */
    private void setRespContentType(HttpServletResponse resp, String fileExt) {
        switch (fileExt) {
            case "txt":
                resp.setContentType("text/plain");
                break;
            case "xml":
                resp.setContentType("text/xml");
                break;
            case "jpeg":
                resp.setContentType("image/jpeg");
                break;
            case "gif":
                resp.setContentType("image/gif");
                break;
            case "bmp":
                resp.setContentType("image/bmp");
                break;
            case "xls":
            case "xlsx":
                resp.setContentType("application/vnd.ms-excel");
                break;
            case "doc":
            case "docx":
                resp.setContentType("application/msword");
                break;
            case "pdf":
                resp.setContentType("application/pdf");
                break;
            default:
                resp.setContentType("text/plain");
                break;
        }
    }

    private String getDownloadFileName(HttpServletRequest request, String fileName) {
        String result;
        boolean isIe11 = false;
        boolean isIe = false;
        if (request != null) {
            String userAgent = request.getHeader("User-Agent");
            isIe11 = userAgent.indexOf("like Gecko") > 0;
            isIe = userAgent.indexOf("MSIE") > 0;
        }
        try {
            if (isIe || isIe11) {
                result = URLEncoder.encode(fileName, "UTF-8");
            } else {
                result = new String(fileName.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("文件名错误！");
        }
        return result;
    }

    public WriteFTPFile getFtpWriter() {
        return ftpWriter;
    }

    public void setFtpWriter(WriteFTPFile ftpWriter) {
        this.ftpWriter = ftpWriter;
    }

    public ReadFTPFile getFtpReader() {
        return ftpReader;
    }

    public void setFtpReader(ReadFTPFile ftpReader) {
        this.ftpReader = ftpReader;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    public Integer getFileServerType() {
        return fileServerType;
    }

    public void setFileServerType(Integer fileServerType) {
        this.fileServerType = fileServerType;
    }

    public Long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(Long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
