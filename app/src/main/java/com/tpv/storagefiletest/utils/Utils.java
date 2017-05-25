package com.tpv.storagefiletest.utils;

import android.util.Log;

import com.tpv.storagefiletest.domain.StorageInfo;
import com.tpv.storagefiletest.domain.TestCase;
import com.tpv.storagefiletest.ui.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by Jack.Weng on 2017/4/25.
 */

public class Utils {
    public static ArrayList<TestCase> getTestCase(ArrayList<StorageInfo> list) {
        ArrayList<TestCase> result = new ArrayList<>();
        StorageInfo[] temp;
        TestCase testCase;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                temp = new StorageInfo[2];
                testCase = new TestCase();
                temp[0] = list.get(i);
                temp[1] = list.get(j);
                testCase.setNeedTest(false);
                testCase.setStorageInfos(temp);
                result.add(testCase);
                temp = new StorageInfo[2];
                testCase = new TestCase();
                temp[0] = list.get(j);
                temp[1] = list.get(i);
                testCase.setNeedTest(false);
                testCase.setStorageInfos(temp);
                result.add(testCase);
            }
        }
        return result;
    }

    public static boolean CheckFileIsExists(String source, String target) {
        //根目录
        File RootDirectorySource = new File(source + MainActivity.ROOTPATH);
        File RootDirectoryTarget = new File(target + MainActivity.ROOTPATH);
        //源目录
        File SourceDirectorySource = new File(RootDirectorySource, MainActivity.SOURCEPATH);
        File SourceDirectoryTarget = new File(RootDirectoryTarget, MainActivity.SOURCEPATH);
        //目标目录
        File TargetDirectorySource = new File(RootDirectorySource, MainActivity.TARGETPATH);
        File TargetDirectoryTarget = new File(RootDirectoryTarget, MainActivity.TARGETPATH);
        if (!RootDirectorySource.exists()) {
            Log.i(MainActivity.TAG, RootDirectorySource.getAbsolutePath() + " is not existe!");
            if (RootDirectorySource.mkdir()) {
                Log.i(MainActivity.TAG, "Create directory " + RootDirectorySource.getAbsolutePath() + " success!");
                if (SourceDirectorySource.mkdir()) {
                    Log.i(MainActivity.TAG, "Create directory " + SourceDirectorySource.getAbsolutePath() + " success!");
                } else {
                    Log.i(MainActivity.TAG, "Create directory " + SourceDirectorySource.getAbsolutePath() + " failed!");
                }
                if (TargetDirectorySource.mkdir()) {
                    Log.i(MainActivity.TAG, "Create directory " + TargetDirectorySource.getAbsolutePath() + " success!");
                } else {
                    Log.i(MainActivity.TAG, "Create directory " + TargetDirectorySource.getAbsolutePath() + " failed!");
                }
            } else {
                Log.i(MainActivity.TAG, "Create directory " + RootDirectorySource.getAbsolutePath() + " failed!");
            }
            return false;
        } else {
            if (!SourceDirectorySource.exists()) {
                Log.i(MainActivity.TAG, SourceDirectorySource.getAbsolutePath() + " is not existe!");
                if (SourceDirectorySource.mkdir()) {
                    Log.i(MainActivity.TAG, "Create directory " + SourceDirectorySource.getAbsolutePath() + " success!");
                } else {
                    Log.i(MainActivity.TAG, "Create directory " + SourceDirectorySource.getAbsolutePath() + " failed!");
                }
                return false;
            } else {
                File[] childfiles = SourceDirectorySource.listFiles();
                if (childfiles.length == 0) {
                    Log.i(MainActivity.TAG, "Source file is null.");
                    return false;
                }
            }
        }
        if (!RootDirectoryTarget.exists()) {
            Log.i(MainActivity.TAG, RootDirectoryTarget.getAbsolutePath() + " is not existe!");
            if (RootDirectoryTarget.mkdir()) {
                Log.i(MainActivity.TAG, "Create directory " + RootDirectoryTarget.getAbsolutePath() + " success!");
                if (SourceDirectorySource.mkdir()) {
                    Log.i(MainActivity.TAG, "Create directory " + SourceDirectorySource.getAbsolutePath() + " success!");
                } else {
                    Log.i(MainActivity.TAG, "Create directory " + SourceDirectorySource.getAbsolutePath() + " failed!");
                }
                if (TargetDirectorySource.mkdir()) {
                    Log.i(MainActivity.TAG, "Create directory " + TargetDirectorySource.getAbsolutePath() + " success!");
                } else {
                    Log.i(MainActivity.TAG, "Create directory " + TargetDirectorySource.getAbsolutePath() + " failed!");
                }
            } else {
                Log.i(MainActivity.TAG, "Create directory " + RootDirectoryTarget.getAbsolutePath() + " failed!");
            }
            return false;
        } else {
            if (!SourceDirectoryTarget.exists()) {
                Log.i(MainActivity.TAG, SourceDirectoryTarget.getAbsolutePath() + " is not existe!");
                if (SourceDirectoryTarget.mkdir()) {
                    Log.i(MainActivity.TAG, "Create directory " + SourceDirectoryTarget.getAbsolutePath() + " success!");
                } else {
                    Log.i(MainActivity.TAG, "Create directory " + SourceDirectoryTarget.getAbsolutePath() + " failed!");
                }
                return false;
            } else {
                File[] childfiles = SourceDirectoryTarget.listFiles();
                if (childfiles.length == 0) {
                    Log.i(MainActivity.TAG, "Source file is null.");
                    return false;
                }
            }
        }
        return true;
    }

    private static void nioTransferCopy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
            inStream.close();
            in.close();
            outStream.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
