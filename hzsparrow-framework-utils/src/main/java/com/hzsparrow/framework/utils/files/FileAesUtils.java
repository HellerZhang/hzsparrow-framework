package com.hzsparrow.framework.utils.files;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class FileAesUtils {

    public static void main(String[] args) throws Exception {
        String strKey = "asghjdgfajysdgfasd244sa5df4gsdf54gsdf4gsdflkjsdfv,mnsdfbjsdfg56s5df654sdfg54sd";
        System.out.println(new Md5Hash(strKey).toHex());
        /*String filePath = "D:\\test\\test2222.pdf";
        String encodeFilePath = "D:\\test\\2.pdf";
        String decodeFilePath = "D:\\test\\decode.pdf";
        FileAesUtils util = new FileAesUtils();*/
        //        Date begin1 = new Date();
        //        util.encrypt(strKey, filePath, encodeFilePath);
        //        Date end1 = new Date();
        //        System.out.println((end1.getTime() - begin1.getTime()));
        //        Date begin2 = new Date();
        //        util.decrypt(strKey, encodeFilePath, decodeFilePath);
        //        Date end2 = new Date();
        //        System.out.println((end2.getTime() - begin2.getTime()));

        // 将明文密码进行base64加密
        String base64Pwd = Base64.getEncoder().encodeToString(strKey.getBytes());
        System.out.println(base64Pwd);
        //        // 使用base64密码对明文密码进行AES加密
        //        String aesPwd = new AesHelper(base64Pwd).encrypt(strKey);
        //        // 将AES密码进行base64加密
        //        String base64AesPwd = Base64.getEncoder().encodeToString(aesPwd.getBytes());
        //        // 对文件进行AES加密
        //        util.decrypt(base64AesPwd, encodeFilePath, decodeFilePath);
    }

    /**
     * 根据参数生成KEY
     */
    //    private Key getKey(String strKey) {
    //        if (StringUtils.isBlank(strKey)) {
    //            strKey = "";
    //        }
    //        try {
    //            KeyGenerator _generator = KeyGenerator.getInstance("AES");
    //            _generator.init(new SecureRandom(strKey.getBytes()));
    //            Key key = _generator.generateKey();
    //            _generator = null;
    //            System.out.println(JsonHelper.serialize(key));
    //            return key;
    //        } catch (Exception e) {
    //            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
    //        }
    //    }

    private Key getKey(String strKey) {
        if (StringUtils.isBlank(strKey)) {
            strKey = "";
        }
        try {
            String md5 = new Md5Hash(strKey).toHex();
            String md524 = md5.substring(8, 24);
            return new SecretKeySpec(md524.getBytes(), "AES");
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     * @Title: encrypt
     * @Description: 使用指定的key，将源文件加密后，写入目标文件
     * @author Dong
     * @param file 源文件
     * @param strKey 密钥
     * @param destFile 目标文件
     * @throws Exception void
     */
    public String encrypt(String strKey, String file, String destFile) throws Exception {
        if (StringUtils.isBlank(destFile))
            destFile = new File(file).getParent() + File.separator + getRandomDesFileName();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey(strKey));
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
        return destFile;
    }

    /**
     * @Title: decrypt
     * @Description: 使用指定的key，将源文件解密后，写入目标文件
     * @author Dong
     * @param strKey
     * @param file
     * @param dest
     * @throws Exception void
     */
    public String decrypt(String strKey, String file, String dest) throws Exception {
        if (StringUtils.isBlank(dest)) dest = new File(file).getParent() + File.separator
                + getRandomFileName(file.substring(file.lastIndexOf(".") + 1));
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getKey(strKey));
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();

        return dest;
    }

    public static String getRandomDesFileName() {
        String code = getRandomFileName("sn");
        return code;
    }

    public static String getRandomZipFileName() {
        String code = getRandomFileName("zip");
        return code;
    }

    public static String getRandomDocxFileName() {
        String code = getRandomFileName("docx");
        return code;
    }

    public static String getRandomTenderDesFileName() {
        String code = getRandomFileName("sntd");
        return code;
    }

    public static String getRandomBidDesFileName() {
        String code = getRandomFileName("snbd");
        return code;
    }

    public static String getRandomFileName(String sufix) {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = df2.format(new Date()) + "_" + new Random().nextInt(1000) + "." + sufix;
        return code;
    }
}
