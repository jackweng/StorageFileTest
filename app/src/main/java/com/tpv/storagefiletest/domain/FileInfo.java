package com.tpv.storagefiletest.domain;

/**
 * Created by Jack.Weng on 2017/5/26.
 */

public class FileInfo {
    public boolean isChecked;
    public String FileName;
    public String FileSize;
    public String FilePath;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(String fileSize) {
        FileSize = fileSize;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    @Override
    public String toString() {
        return isChecked() + "\n" + getFileName() + "\n" + getFileSize() + "\n" + getFilePath();
    }
}
