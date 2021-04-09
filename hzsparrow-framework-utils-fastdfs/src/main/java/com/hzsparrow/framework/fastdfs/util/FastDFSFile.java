/**
 * $Id:$
 * Copyright 2019 HellerZhang. All rights reserved.
 */
package com.hzsparrow.framework.fastdfs.util;

import java.io.Serializable;

/**
 * <strong>类概要： FastDFS文件实体</strong> <br>
 * <strong>创建时间： 2016-9-27 下午10:29:25</strong> <br>
 * 
 * @Project springmvc-main(com.wl.bean)
 * @author Wang Liang
 * @version 1.0.0
 */
public class FastDFSFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String FILE_DEFAULT_AUTHOR = "hzsparrow";

    private byte[] content;

    private String name;

    private String ext;

    private String length;

    private String author = FILE_DEFAULT_AUTHOR;

    public FastDFSFile(byte[] content, String ext) {
        this.content = content;
        this.ext = ext;
    }

    public FastDFSFile(byte[] content, String name, String ext) {
        this.content = content;
        this.name = name;
        this.ext = ext;
    }

    public FastDFSFile(byte[] content, String name, String ext, String length, String author) {
        this.content = content;
        this.name = name;
        this.ext = ext;
        this.length = length;
        this.author = author;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
