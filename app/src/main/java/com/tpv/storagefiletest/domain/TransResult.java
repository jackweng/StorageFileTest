package com.tpv.storagefiletest.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jack.Weng on 2017/6/8.
 */

public class TransResult implements Parcelable {
    private int TestIndex;
    private String FileName;
    private long FileSizeLong;
    private String FileSize;
    private boolean Result;
    private String Reason;
    private long TimeLong;
    private String Time;
    private String Speed;

    public int getTestIndex() {
        return TestIndex;
    }

    public void setTestIndex(int testIndex) {
        TestIndex = testIndex;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public long getFileSizeLong() {
        return FileSizeLong;
    }

    public void setFileSizeLong(long fileSizeLong) {
        FileSizeLong = fileSizeLong;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(String fileSize) {
        FileSize = fileSize;
    }

    public boolean getResult() {
        return Result;
    }

    public void setResult(boolean result) {
        Result = result;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public Long getTimeLong() {
        return TimeLong;
    }

    public void setTimeLong(Long timeLong) {
        TimeLong = timeLong;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    @Override
    public String toString() {
        return "Index:" + getTestIndex()
                + "\nFileName:" + getFileName()
                + "\nFileSizeLong:" + getFileSizeLong()
                + "\nFileSize:" + getFileSize()
                + "\nResult:" + getResult()
                + "\nReason:" + getReason()
                + "\nTimeLong:" + getTimeLong()
                + "\nTime:" + getTime()
                + "\nSpeed:" + getSpeed();
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
