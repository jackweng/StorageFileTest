package com.tpv.storagefiletest.domain;

/**
 * Created by Jack.Weng on 2017/6/8.
 */

public class TransResult {
    private int TestIndex;
    private String IndexFileName;
    private long IndexFileSize;
    private boolean IndexResult;
    private String IndexReason;
    private float IndexTime;
    private String IndexSpeed;

    public int getTestIndex() {
        return TestIndex;
    }

    public void setTestIndex(int testIndex) {
        TestIndex = testIndex;
    }

    public boolean isIndexResult() {
        return IndexResult;
    }

    public void setIndexResult(boolean indexResult) {
        IndexResult = indexResult;
    }

    public String getIndexReason() {
        return IndexReason;
    }

    public void setIndexReason(String indexReason) {
        IndexReason = indexReason;
    }

    public float getIndexTime() {
        return IndexTime;
    }

    public void setIndexTime(float indexTime) {
        IndexTime = indexTime;
    }

    @Override
    public String toString() {
        return "Index:" + getTestIndex()
                + "\nResult:" + isIndexResult()
                + "\nReason:" + getIndexReason()
                + "\nTime:" + getIndexTime();
    }
}
