package com.tpv.storagefiletest.domain;

/**
 * Created by Jack.Weng on 2017/6/5.
 */

public class TestInfo {
    private FileInfo SourceFileInfo;
    private String TargetPath;
    private int count;

    public FileInfo getSourceFileInfo() {
        return SourceFileInfo;
    }

    public void setSourceFileInfo(FileInfo sourceFileInfo) {
        SourceFileInfo = sourceFileInfo;
    }

    public String getTargetPath() {
        return TargetPath;
    }

    public void setTargetPath(String targetPath) {
        TargetPath = targetPath;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
