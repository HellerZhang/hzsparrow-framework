package com.hzsparrow.boot.base.contant;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum FileSaveAddrEnum {
    LOCAL(1, "本地", "local"),
    FTP(2, "FTP", "ftp"),
    FASTDFS(3, "FastDFS", "fastdfs"),
    ;
    private Integer value;

    private String name;

    private String flag;

    FileSaveAddrEnum(Integer value, String name, String flag) {
        this.value = value;
        this.name = name;
        this.flag = flag;
    }

    public static Integer valueOfByFlag(String flag) {
        for (FileSaveAddrEnum e : FileSaveAddrEnum.values()) {
            if (StringUtils.equals(e.getFlag(), flag)) {
                return e.getValue();
            }
        }
        return null;
    }
}
