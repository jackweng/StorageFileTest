package com.tpv.storagefiletest.domain;

/**
 * Created by Jack.Weng on 2017/4/27.
 */

public class TestCase {
    public boolean isNeedTest;
    public StorageInfo[] storageInfos;

    public boolean isNeedTest() {
        return isNeedTest;
    }

    public void setNeedTest(boolean needTest) {
        isNeedTest = needTest;
    }

    public StorageInfo[] getStorageInfos() {
        return storageInfos;
    }

    public void setStorageInfos(StorageInfo[] storageInfos) {
        this.storageInfos = storageInfos;
    }

    @Override
    public String toString() {
        return getStorageInfos()[0] + "--" + getStorageInfos()[1] + "--" + isNeedTest();
    }
}
