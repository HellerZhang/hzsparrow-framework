package com.hzsparrow.framework.utils.files;

import com.hzsparrow.framework.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileDesUtils {

    public static void main(String[] args) {
        String strKey = "123456";
        Key key = new FileDesUtils().getKey(strKey);
        System.out.println(key.getAlgorithm());
        System.out.println(key.getFormat());
        System.out.println(JsonUtils.serialize(key.getEncoded()));
        System.out.println(JsonUtils.serialize(key));
    }

    /**
     * 根据参数生成KEY
     */
    private Key getKey(String strKey) {
        if (StringUtils.isBlank(strKey)) {
            strKey = "";
        }
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            _generator.init(new SecureRandom(strKey.getBytes()));
            Key key = _generator.generateKey();
            _generator = null;
            return key;
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

        Cipher cipher = Cipher.getInstance("DES");
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
        if (StringUtils.isBlank(dest)) dest = new File(file).getParent() + File.separator + getRandomDocxFileName();
        Cipher cipher = Cipher.getInstance("DES");
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
