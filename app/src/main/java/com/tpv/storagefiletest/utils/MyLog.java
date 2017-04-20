package com.tpv.storagefiletest.utils;

import android.util.Log;

import com.tpv.storagefiletest.domain.DebugFlag;
import com.tpv.storagefiletest.ui.MainActivity;

/**
 * Created by Jack.Weng on 2017/4/20.
 */

public class MyLog {
    public static void i(String message) {
        if (DebugFlag.isDebug) {
            Log.i(MainActivity.TAG, message);
        }
    }
    public static void e(String message) {
        if (DebugFlag.isDebug) {
            Log.e(MainActivity.TAG, message);
        }
    }
    public static void d(String message) {
        if (DebugFlag.isDebug) {
            Log.d(MainActivity.TAG, message);
        }
    }
    public static void v(String message) {
        if (DebugFlag.isDebug) {
            Log.v(MainActivity.TAG, message);
        }
    }
    public static void w(String message) {
        if (DebugFlag.isDebug) {
            Log.w(MainActivity.TAG, message);
        }
    }
}
