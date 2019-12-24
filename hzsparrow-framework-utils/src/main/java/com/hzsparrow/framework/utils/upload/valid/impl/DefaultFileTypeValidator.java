package com.hzsparrow.framework.utils.upload.valid.impl;

import com.hzsparrow.framework.utils.upload.valid.exception.FileTypeNotAllowedException;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileTypeValidator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认文件类型检查器
 */
public class DefaultFileTypeValidator implements FileTypeValidator {

    private Map<String, String[]> extMap = new HashMap<String, String[]>();

    public DefaultFileTypeValidator() {
        this(null, true);
    }

    /**
     * @param allowType 允许上传的文件类型（带.）
     */
    public DefaultFileTypeValidator(String[] allowType, boolean isEnableDefault) {
        init(allowType, isEnableDefault);
    }

    /**
     * 初始化方法，设置允许上传的文件类型
     *
     * @author Heller.Zhang
     * @since 2017年10月6日 下午2:54:29
     */
    private void init(String[] allowType, boolean isEnableDefault) {
        // 初始化允许上传的文件扩展名
        if (isEnableDefault) {
            extMap.put("image", new String[]{".gif", ".jpg", ".jpeg", ".png", ".bmp", ".jfif"});
            extMap.put("flash", new String[]{".swf", ".flv"});
            extMap.put("media", new String[]{".mp3", ".wav", ".wma", ".wmv", ".mid", ".avi", ".mpg",
                    ".asf", ".rm", ".rmvb"});
            extMap.put("file", new String[]{".doc", ".docx", ".xls", ".xlsx", ".ppt", ".htm", ".html", ".txt", ".zip",
                    ".rar", ".gz", ".bz2", ".7z", ".pdf", ".jrxml", ".jasper"});
        }
        if (allowType != null) {
            extMap.put("other", allowType);
        }
    }

    @Override
    public boolean validFileType(MultipartFile file) {
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        boolean isAllowedFileType = false;
        for (String key : extMap.keySet()) {
            if (Arrays.asList(extMap.get(key)).contains(fileExt)) {
                isAllowedFileType = true;
                break;
            }
        }
        if (!isAllowedFileType) {
            throw new FileTypeNotAllowedException(fileExt);
        }
        return true;
    }

    public Map<String, String[]> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, String[]> extMap) {
        this.extMap = extMap;
    }
}
