package com.hzsparrow.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipException;

public class ZipUtils {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws Exception {
        ZipUtils zipHelper = new ZipUtils();

//        List<ZipModel> fileList = zipHelper.unZip("D:\\test\\AO_back_a4a12045_58e8_4f8a_9083_26eae9815467", "D:\\test\\test2");
//        for (ZipModel zm : fileList) {
//            System.out.print(" zm.getStrFileName() = " + zm.getStrFileName() + "  |  ");
//            System.out.print(" zm.getStrFileSuffix() = " + zm.getStrFileSuffix() + "  |  ");
//            System.out.print(" zm.getStrFileSize() = " + zm.getlFileSize() / 1024 + "  |  ");
//            System.out.print(" zm.getStrFileUrl() = " + zm.getStrFileUrl() + "  |  ");
//
//        }
        zipHelper.zip("D:\\项目文档\\财政数据导入\\一体化部署文档\\export\\13fed998-0613-4a1f-8e44-9c0452a93f88", "D:\\项目文档\\财政数据导入\\一体化部署文档\\export\\2019张家口市第七中学130700201936000400001010070000600001.epkg", "随便一个注释");
        //		logger.debug("-----------------------------------------------");
        //		List<ZipModel> fileList = zipHelper.unZip(zipfile, null);
        //		for (ZipModel zm : fileList) {
        //			System.out.print(" zm.getStrFileName() = "+zm.getStrFileName() +"  |  ");
        //			System.out.print(" zm.getStrFileSuffix() = "+zm.getStrFileSuffix() +"  |  ");
        //			System.out.print(" zm.getStrFileSize() = "+zm.getlFileSize()/1024 +"  |  ");
        //			System.out.print(" zm.getStrFileUrl() = "+zm.getStrFileUrl() +"  |  ");
        //			
        //			logger.debug();
        //		}
    }

    public String zip(String src, String archive, String comment) throws FileNotFoundException, IOException {
        return zip(src, archive, comment, null);
    }

    /**
     * 对文件夹或者文件进行压缩
     *
     * @param src     源文件/文件夹
     * @param archive 文件输出目录
     * @param comment 压缩包注释
     * @throws FileNotFoundException
     * @throws IOException           void
     *  zip
     *  对文件夹或者文件进行压缩
     * @author HellerZhang
     */
    public String zip(String src, String archive, String comment, String zipEncoding) throws FileNotFoundException, IOException {

        File srcFile = new File(src);
        if (!srcFile.exists() || (srcFile.isDirectory() && srcFile.list().length == 0)) {
            throw new FileNotFoundException("File must exist and  ZIP file must have at least one entry.");
        }
        if (StringUtils.isEmpty(archive)) {
            archive = srcFile.getParent() + File.separator + getRandomZipFileName();
        }

        //----压缩文件：   
        FileOutputStream f = new FileOutputStream(archive);
        //使用指定校验和创建输出流   
        CheckedOutputStream csum = new CheckedOutputStream(f, new CRC32());

        ZipOutputStream zos = new ZipOutputStream(csum);
        //支持中文   
        zos.setEncoding(StringUtils.isBlank(zipEncoding) ? "GBK" : zipEncoding);
        BufferedOutputStream out = new BufferedOutputStream(zos);
        //设置压缩包注释   
        if (StringUtils.isNotBlank(comment)) zos.setComment(comment);
        //启用压缩   
        zos.setMethod(ZipOutputStream.DEFLATED);
        //压缩级别为最强压缩，但时间要花得多一点   
        zos.setLevel(Deflater.BEST_COMPRESSION);

        //开始压缩   
        writeRecursive(zos, out, srcFile, srcFile.getAbsolutePath());

        out.close();
        zos.close();
        // 注：校验和要在流关闭后才准备，一定要放在流被关闭后使用   
        logger.debug("Checksum: " + csum.getChecksum().getValue());
        return archive;
    }

    /**
     * 递归压缩
     *
     * @param zos
     * @param bo
     * @param srcFile
     * @param prefixDir
     * @throws FileNotFoundException void
     *  writeRecursive
     *  递归压缩
     * @author HellerZhang
     */
    private void writeRecursive(ZipOutputStream zos, BufferedOutputStream bo, File srcFile, String prefixDir)
            throws IOException, FileNotFoundException {
        ZipEntry zipEntry;

        String filePath = srcFile.getAbsolutePath();
        String entryName = filePath.replace(prefixDir, "").replaceFirst("\\" + File.separator, "");
        if (srcFile.isDirectory()) {
            if (!"".equals(entryName)) {
                logger.debug("正在创建目录 - " + srcFile.getAbsolutePath() + "  entryName=" + entryName);

                //如果是目录，则需要在写目录后面加上 /
                entryName = entryName + File.separator;
                zipEntry = new ZipEntry(entryName);
                zos.putNextEntry(zipEntry);
            }

            File srcFiles[] = srcFile.listFiles();
            for (int i = 0; i < srcFiles.length; i++) {
                writeRecursive(zos, bo, srcFiles[i], prefixDir);
            }
        } else {
            logger.debug("正在写文件 - " + srcFile.getAbsolutePath() + "  entryName=" + entryName);
            BufferedInputStream bi = new BufferedInputStream(new FileInputStream(srcFile));

            //开始写入新的ZIP文件条目并将流定位到条目数据的开始处   
            zipEntry = new ZipEntry(entryName);
            zos.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int readCount = bi.read(buffer);

            while (readCount != -1) {
                bo.write(buffer, 0, readCount);
                readCount = bi.read(buffer);
            }
            //注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新一把，不   
            //然可能有的内容就会存入到后面条目中去了   
            bo.flush();
            //文件读完后关闭   
            bi.close();
        }
    }

    public List<ZipModel> unZip(String archive, String decompressDir)
            throws IOException, FileNotFoundException, ZipException {
        return unZip(archive, decompressDir, getUnZipFilePath());
    }

    /**
     * 解压文件
     *
     * @param archive       压缩包路径
     * @param decompressDir 解压路径
     * @return 解压的文件列表
     *  解压文件
     * @author HellerZhang
     */
    public List<ZipModel> unZip(String archive, String decompressDir, String unZipPath)
            throws IOException, FileNotFoundException, ZipException {
        List<ZipModel> listFile = new ArrayList<>();

        BufferedInputStream bi;

        ZipFile zf = new ZipFile(archive, "GBK");//支持中文   
        if (StringUtils.isBlank(decompressDir)) {
            decompressDir = new File(archive).getParent() + File.separator + unZipPath;
        }
        Enumeration<ZipEntry> e = zf.getEntries();
        while (e.hasMoreElements()) {
            ZipEntry ze2 = (ZipEntry) e.nextElement();
            String entryName = ze2.getName();

            //Linux中文乱码解决
            /*if (OSinfoUtils.isLinux()) {
                logger.info("--Linux中文乱码解决,entryName:" + entryName);
                entryName = new String(getUTF8BytesFromGBKString(entryName));
            }*/

            String path = decompressDir + File.separator + entryName;
            if (ze2.isDirectory()) {
                logger.debug("正在创建解压目录 - " + entryName);
                File decompressDirFile = new File(path);
                if (!decompressDirFile.exists()) {
                    decompressDirFile.mkdirs();
                }
            } else {
                logger.debug("正在创建解压文件 - " + entryName);

                File file = new File(decompressDir + File.separator + entryName);
                File fileDir = new File(file.getParent());
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

                bi = new BufferedInputStream(zf.getInputStream(ze2));
                byte[] readContent = new byte[1024];
                int readCount = bi.read(readContent);
                while (readCount != -1) {
                    bos.write(readContent, 0, readCount);
                    readCount = bi.read(readContent);
                }
                bos.close();
                ZipModel zm = new ZipModel();
                zm.setStrFileName(entryName);
                zm.setlFileSize(ze2.getSize());
                if (entryName.contains(".")) {
                    zm.setStrFileSuffix(entryName.substring(entryName.lastIndexOf(".")));
                }
                zm.setStrFileUrl(decompressDir + File.separator + entryName);

                listFile.add(zm);
            }
        }
        zf.close();

        return listFile;
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }

    /**
     * 获取zip随机文件名
     *
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:22:55
     */
    public String getRandomZipFileName() {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = df2.format(new Date()) + "_" + new Random().nextInt(1000) + ".zip";
        return code;
    }

    /**
     * 获取zip随机路径名
     *
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:22:07
     */
    public String getUnZipFilePath() {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = df2.format(new Date()) + "_" + new Random().nextInt(1000);
        return code;
    }

}
