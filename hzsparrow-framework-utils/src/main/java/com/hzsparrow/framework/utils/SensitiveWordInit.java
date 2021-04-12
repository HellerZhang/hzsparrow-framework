package com.hzsparrow.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 */
public class SensitiveWordInit {

    Logger logger = LoggerFactory.getLogger(getClass());

    private String ENCODING = "UTF-8"; //字符编码

    @SuppressWarnings("rawtypes")
    public HashMap sensitiveWordMap;

    public SensitiveWordInit() {
        super();
    }

    /**
     * 通过敏感词字符串和分隔符初始化敏感词库
     *
     * @param keyWordStr 敏感词字符串
     * @param separator  分隔符
     * @return 敏感词map集合
     * @author Heller.Zhang
     * @since 2019年4月30日 上午11:13:24
     */
    @SuppressWarnings("rawtypes")
    public Map initKeyWord(String keyWordStr, String separator) {
        try {
            //读取敏感词库
            Set<String> keyWordSet = new HashSet<String>(Arrays.asList(keyWordStr.split(separator)));
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            logger.error("初始化敏感词库失败！", e);
        }
        return sensitiveWordMap;
    }

    /**
     * 通过读取文件初始化敏感词库，每行一个敏感词
     *
     * @param filePath 文件路径
     * @return 敏感词map集合
     * @author Heller.Zhang
     * @since 2019年4月30日 上午11:07:43
     */
    @SuppressWarnings("rawtypes")
    public Map initKeyWordByFlie(String filePath) {
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile(filePath);
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            logger.error("初始化敏感词库失败！", e);
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = { isEnd = 0 国 = {<br>
     * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd = 1 } } } } 五 = {
     * isEnd = 0 星 = { isEnd = 0 红 = { isEnd = 0 旗 = { isEnd = 1 } } } }
     *
     * @param keyWordSet 敏感词库
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size()); //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next(); //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i); //转换成char型
                Object wordMap = nowMap.get(keyChar); //获取

                if (wordMap != null) { //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else { //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd", "0"); //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1"); //最后一个
                }
            }
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     *
     * @param filePath 敏感词库文件路径
     * @return 敏感词set集合
     * @throws Exception 文件不存在时抛出异常
     */
    @SuppressWarnings("resource")
    private Set<String> readSensitiveWordFile(String filePath) throws Exception {
        Set<String> set = null;

        File file = new File(filePath); //读取文件
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
        try {
            if (file.isFile() && file.exists()) { //文件流是否存在
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while ((txt = bufferedReader.readLine()) != null) { //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            } else { //不存在抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            read.close(); //关闭文件流
        }
        return set;
    }
}
