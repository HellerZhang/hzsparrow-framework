package com.hzsparrow.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 移动、复制文件
 *
 * @author Heller.Zhang
 * @since 2019年5月7日 上午9:03:22
 */
@Component
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 移动文件到指定路径
     *
     * @param srcFile
     * @param destPath
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:03:28
     */
    public static boolean Move(File srcFile, String destPath) {
        File dir = new File(destPath);
        boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));
        return success;
    }

    /**
     * 移动文件到指定路径
     *
     * @param srcFile
     * @param destPath
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:03:51
     */
    public static boolean Move(String srcFile, String destPath) {
        File file = new File(srcFile);
        File dir = new File(destPath);
        boolean success = file.renameTo(new File(dir, file.getName()));
        return success;
    }

    /**
     * 复制文件到指定路径
     *
     * @param oldPath
     * @param newPath
     * @throws IOException
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:04:04
     */
    public static void Copy(String oldPath, String newPath) throws IOException {
        File oldfile = new File(oldPath);
        Copy(oldfile, newPath);
    }

    /**
     * 复制文件到指定路径
     *
     * @param oldfile
     * @param newPath
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:04:13
     */
    public static void Copy(File oldfile, String newPath) {
        try {
            int byteread = 0;
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("复制文件失败！", e);
        }
    }

    /**
     * 删除空目录
     *
     * @param dir
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:05:05
     */
    public static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            logger.debug("Successfully deleted empty directory: " + dir);
        } else {
            logger.debug("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 删除指定目录及其下的子目录和文件
     *
     * @param dirPath
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:05:25
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(new File(dirPath));
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:05:40
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 获取文件内容的字节数组
     *
     * @param filePath
     * @return
     * @throws IOException
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:09:14
     */
    public static byte[] getContent(String filePath) throws IOException {
        FileInputStream in = new FileInputStream(filePath);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();
        byte[] bytes = out.toByteArray();
        out.close();
        return bytes;
    }

    /**
     * 读取文件的字节数组
     *
     * @param file
     * @return
     * @throws IOException
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:06:12
     */
    public static byte[] getByteArray(String file) throws IOException {
        File f = new File(file);
        if (!f.exists()) {
            throw new FileNotFoundException("file not exists");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    /**
     * 将byte数组写入文件
     *
     * @param path
     * @param content
     * @throws IOException
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:06:02
     */
    public static void createFile(String path, byte[] content) throws IOException {
        File file1 = new File(path);

        File file2 = new File(file1.getParent());
        if (!file2.exists()) file2.mkdirs();

        FileOutputStream fos = new FileOutputStream(path);

        fos.write(content);
        fos.close();
    }

    /**
     * 获取源文件的绝对地址
     *
     * @param relativePath 源文件相对地址
     * @return
     * @throws IOException
     * @author Heller.Zhang
     * @since 2019年5月27日 下午6:39:27
     */
    public static String getResourceFilePath(String relativePath) throws IOException {
        return getResourceFile(relativePath).getAbsolutePath();
    }

    /**
     * 获取classpath下的源文件
     *
     * @param relativePath 源文件相对地址
     * @return
     * @throws IOException
     * @author Heller.Zhang
     * @since 2019年5月27日 下午6:39:45
     */
    public static File getResourceFile(String relativePath) throws IOException {
        Resource resource = new ClassPathResource(relativePath);
        return resource.getFile();
    }

    /**
     * 合并可排序的文件，文件名应以 【序号-名称】 格式命名
     *
     * @param rootPath
     * @param outputPath
     * @return
     * @author Heller.Zhang
     * @since 2019年7月25日 下午3:28:56
     */
    public static boolean mergeSortFile(String rootPath, String outputPath) {
        File root = new File(rootPath);
        String[] childs = root.list();
        // 对文件进行排序
        for (int i = 0; i < childs.length; i++) {
            for (int j = i + 1; j < childs.length; j++) {
                Integer iCount = Integer.valueOf(childs[i].substring(0, childs[i].indexOf("-")));
                Integer jCount = Integer.valueOf(childs[j].substring(0, childs[j].indexOf("-")));
                if (iCount > jCount) {
                    String temp = childs[i];
                    childs[i] = childs[j];
                    childs[j] = temp;
                }
            }
        }
        return mergeFile(rootPath, childs, outputPath);
    }

    /**
     * 合并指定目录下的文件
     *
     * @param rootPath
     * @param outputPath
     * @return
     * @author Heller.Zhang
     * @since 2019年7月25日 下午3:30:26
     */
    public static boolean mergeFile(String rootPath, String outputPath) {
        File root = new File(rootPath);
        return mergeFile(rootPath, root.list(), outputPath);
    }

    /**
     * 合并指定文件
     *
     * @param filePath
     * @param outputPath
     * @return
     * @author Heller.Zhang
     * @since 2019年7月25日 下午3:24:22
     */
    public static boolean mergeFile(String rootPath, String[] filePath, String outputPath) {
        StringBuffer allStr = new StringBuffer("");
        for (String child : filePath) {
            String childStr = TextReaderUtil.readAll(rootPath + File.separator + child);
            allStr.append(childStr + "\n");
        }

        TextWriterUtil.wirteLineUTF8(outputPath, allStr.toString());
        return true;
    }

    public static void main(String[] args) {
        FileUtils.mergeSortFile("D:\\java\\workspace\\szwl-product\\ConfigurationItems\\03_SystemDesign\\initsql\\op",
                "D:\\java\\workspace\\szwl-product\\ConfigurationItems\\03_SystemDesign\\initsql\\opinit.sql");
        FileUtils.mergeSortFile("D:\\java\\workspace\\szwl-product\\ConfigurationItems\\03_SystemDesign\\initsql\\ct",
                "D:\\java\\workspace\\szwl-product\\ConfigurationItems\\03_SystemDesign\\initsql\\ctinit.sql");
    }
}
