package com.hzsparrow.framework.utils.upload.uploader.impl;

import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileDownloader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultFastDFSFileDownloader implements FileDownloader {
    @Override
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath) {

    }

    @Override
    public void downloadToLocal(String destFileName, String destFolder, String filePath) {

    }

    @Override
    public void downloadByRootPath(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath) {

    }
}
