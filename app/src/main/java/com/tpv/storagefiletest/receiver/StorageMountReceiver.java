package com.tpv.storagefiletest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tpv.storagefiletest.utils.MyLog;

public class StorageMountReceiver extends BroadcastReceiver {

    StorageStateListener listener;

    public StorageMountReceiver(StorageStateListener listener) {
        super();
        MyLog.i("new StorageMountReceiver.");
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        MyLog.i("action = " + action);
        listener.onStorageStateChanged();
    }

    public interface StorageStateListener {
        void onStorageStateChanged();
    }
}
