package com.tpv.storagefiletest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.tpv.storagefiletest.ui.MainActivity;

public class StorageMountReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_MEDIA_MOUNTED) ||
                action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
            Message msg = MainActivity.mHandler.obtainMessage();
            msg.what = MainActivity.MEDIA_MOUNTED_UPDATE_UI;
            MainActivity.mHandler.sendMessage(msg);
        }
    }
}
