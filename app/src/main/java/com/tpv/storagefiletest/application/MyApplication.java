package com.tpv.storagefiletest.application;

import android.app.Application;
import android.content.Context;

import com.tpv.storagefiletest.domain.FileInfo;
import com.tpv.storagefiletest.domain.StorageInfo;
import com.tpv.storagefiletest.domain.StorageState;
import com.tpv.storagefiletest.domain.TestCase;
import com.tpv.storagefiletest.domain.TestInfo;
import com.tpv.storagefiletest.domain.TransResult;
import com.tpv.storagefiletest.utils.MyLog;

import java.util.ArrayList;

/**
 * Created by Jack.Weng on 2017/6/25.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static Context mContext;

    private FileInfo mFileInfo = null;
    private StorageInfo mStorageInfo = null;
    private StorageState mStorageState = null;
    private TestCase mTestCase = null;
    private TestInfo mTestInfo = null;
    private TransResult mTransResult = null;
    private ArrayList<TransResult> mResults = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.mContext;
    }

    public FileInfo getFileInfo() {
        return mFileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        if (null != mFileInfo)
            mFileInfo = null;
        mFileInfo = fileInfo;
    }

    public StorageInfo getStorageInfo() {
        return mStorageInfo;
    }

    public void setStorageInfo(StorageInfo storageInfo) {
        if (null != mStorageInfo)
            mStorageInfo = null;
        mStorageInfo = storageInfo;
    }

    public TestCase getTestCase() {
        return mTestCase;
    }

    public void setTestCase(TestCase testCase) {
        if (null != mTestCase)
            mTestCase = null;
        mTestCase = testCase;
    }

    public TestInfo getTestInfo() {
        return mTestInfo;
    }

    public void setTestInfo(TestInfo testInfo) {
        if (null != mTestInfo)
            mTestInfo = null;
        mTestInfo = testInfo;
    }

    public TransResult getTransResult() {
        return mTransResult;
    }

    public void setTransResult(TransResult transResult) {
        if (null != mTransResult)
            mTransResult = null;
        mTransResult = transResult;
    }

    public ArrayList<TransResult> getResults() {
        return mResults;
    }

    public void setResults(ArrayList<TransResult> results) {
        if (null != mResults && mResults.size() > 0) {
            mResults = null;
        }
        mResults = results;
    }
}
