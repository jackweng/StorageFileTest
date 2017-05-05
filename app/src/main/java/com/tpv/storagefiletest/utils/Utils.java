package com.tpv.storagefiletest.utils;

import com.tpv.storagefiletest.domain.StorageInfo;
import com.tpv.storagefiletest.domain.TestCase;

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
}
