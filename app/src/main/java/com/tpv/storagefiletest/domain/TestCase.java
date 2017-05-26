package com.tpv.storagefiletest.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jack.Weng on 2017/4/27.
 */

public class TestCase implements Parcelable {
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
        return getStorageInfos()[0].getPath() + "--" + getStorageInfos()[1].getPath() + "--" + isNeedTest();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static final Parcelable.Creator<TestCase> CREATOR = new Creator<TestCase>() {
        @Override
        public TestCase[] newArray(int size) {
            return new TestCase[0];
        }
        @Override
        public TestCase createFromParcel(Parcel source) {
            return null;
        }
    };
}