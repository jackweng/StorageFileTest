package com.tpv.storagefiletest.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tpv.storagefiletest.R;
import com.tpv.storagefiletest.domain.StorageInfo;
import com.tpv.storagefiletest.domain.StorageState;
import com.tpv.storagefiletest.ui.PercentageBarChart.Entry;
import com.tpv.storagefiletest.utils.MyLog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "com.tpv.storagefiletest";
    private static final String EMMC = "/storage/emulated/0";
    private static final String SDCARD = "/mnt/external_sd";
    public static final int MEDIA_MOUNTED_UPDATE_UI = 0;

    private Context context;
    private int WinWidth;
    private ListView lv_storage_info;
    private StorageManager mStorageManager;
    private StorageListAdapter adapter;
    private ArrayList<StorageInfo> maps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WinWidth = metrics.widthPixels;
        MyLog.i("WinWidth = " + WinWidth);

        try {
            maps = getStorageInfo();
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.i("Can not get storage info.");
        }
        lv_storage_info = (ListView) findViewById(R.id.lv_main_storage_list);
        adapter = new StorageListAdapter(this, maps);
        lv_storage_info.setAdapter(adapter);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MEDIA_MOUNTED_UPDATE_UI:
                    UpdateListView();
                    break;
                default:
                    break;
            }
        }
    };

    private void UpdateStorageInfo() {
        try {
            if (maps.size() == 0) {
                maps = getStorageInfo();
            } else {
                maps.clear();
                ArrayList<StorageInfo> tempInfos = getStorageInfo();
                for (StorageInfo info : tempInfos) {
                    maps.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.i("Can not get storagevolume list.");
        }
        for (StorageInfo info : maps) {
            MyLog.i(info.toString());
        }
    }

    private void UpdateListView() {
        UpdateStorageInfo();
        adapter.notifyDataSetChanged();
    }

    private class HolderView {
        public TextView tv_label;
        public TextView tv_freesize;
        public PercentageBarChart pbc_freesize;
    }

    private class StorageListAdapter extends BaseAdapter {

        private ArrayList<StorageInfo> map;
        private LayoutInflater inflater;

        public StorageListAdapter(Context context, ArrayList<StorageInfo> maps) {
            this.map = maps;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return map.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HolderView holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_storage_main, null);
                holder = new HolderView();
                holder.tv_label = (TextView) convertView.findViewById(R.id.tv_storage_label);
                holder.tv_freesize = (TextView) convertView.findViewById(R.id.tv_storage_freesize);
                holder.pbc_freesize = (PercentageBarChart) convertView.findViewById(R.id.pbc_storage_freesize);
            } else {
                holder = (HolderView) convertView.getTag();
            }
            holder.tv_label.setWidth(WinWidth / 10);
            holder.tv_label.setText(map.get(position).getUserLabel());
            holder.tv_freesize.setWidth(WinWidth / 6);
            holder.tv_freesize.setText(Formatter.formatFileSize(context, map.get(position).getFreeSize())
                    + "/" + Formatter.formatFileSize(context, map.get(position).getMaxSize()));
            List<Entry> mEntries = new ArrayList<>();
            holder.pbc_freesize.setEntries(mEntries);
            UpdatePercentageBarChart(holder.pbc_freesize, mEntries, map.get(position).getUsedSizeEntry());
            return convertView;
        }
    }

    private ArrayList<StorageInfo> getStorageInfo() throws InvocationTargetException, IllegalAccessException {
        ArrayList<StorageInfo> storageInfos = new ArrayList<>();
        Method getVolumeList = null;
        try {
            getVolumeList = StorageManager.class.getMethod("getVolumeList");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            MyLog.i("Can not find method getStorageVolume().");
        }
        getVolumeList.setAccessible(true);
        Object[] objs = (Object[]) getVolumeList.invoke(mStorageManager);
        for (Object obj : objs) {
            StorageInfo info = new StorageInfo();
            try {
                Method getStorageId = obj.getClass().getMethod("getStorageId");
                info.setStorageId((int) getStorageId.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method getStorageId.");
            }
            try {
                Method getPath = obj.getClass().getMethod("getPath");
                info.setPath((String) getPath.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method getPath.");
            }
            try {
                Method getUserLabel = obj.getClass().getMethod("getUserLabel");
                info.setUserLabel((String) getUserLabel.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method getUserLabel.");
            }
            try {
                Method isPrimary = obj.getClass().getMethod("isPrimary");
                info.setPrimary((boolean) isPrimary.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method isPrimary.");
            }
            try {
                Method isRemovable = obj.getClass().getMethod("isRemovable");
                info.setRemovable((boolean) isRemovable.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method isRemovable.");
            }
            try {
                Method isEmulated = obj.getClass().getMethod("isEmulated");
                info.setEmulated((boolean) isEmulated.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method isEmulated.");
            }
            try {
                Method getMtpReserveSpace = obj.getClass().getMethod("getMtpReserveSpace");
                info.setMtpReserveSize((int) getMtpReserveSpace.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method getMtpReserveSpace.");
            }
            try {
                Method allowMassStorage = obj.getClass().getMethod("allowMassStorage");
                info.setAllowMassStorage((boolean) allowMassStorage.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method allowMassStorage.");
            }
            try {
                Method getMaxFileSize = obj.getClass().getMethod("getMaxFileSize");
                info.setMaxFileSize((long) getMaxFileSize.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method getMaxFileSize.");
            }
            try {
                Method getOwner = obj.getClass().getMethod("getOwner");
                info.setOwner((UserHandle) getOwner.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method getOwner.");
            }
            try {
                Method getUuid = obj.getClass().getMethod("getUuid");
                info.setUuid((String) getUuid.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method getUuid.");
            }
            try {
                Method getState = obj.getClass().getMethod("getState");
                info.setState((String) getState.invoke(obj));
            } catch (NoSuchMethodException e) {
                MyLog.i("Can not find method getState.");
            }
            if (info.getUserLabel() == null) {
                if (info.getPath().equals(EMMC)) {
                    info.setUserLabel("EMMC");
                } else if (info.getPath().equals(SDCARD)) {
                    info.setUserLabel("SDCard");
                }
            }
            if (info.getState().equals(StorageState.MOUNTED)) {
                StatFs statFs = new StatFs(info.getPath());
                long max_blocks = statFs.getBlockCountLong();
                long free_blocks = statFs.getAvailableBlocksLong();
                long block_size = statFs.getBlockSizeLong();
                info.setMaxSize(max_blocks * block_size);
                info.setUsedSize((max_blocks - free_blocks) * block_size);
                info.setFreeSize(free_blocks * block_size);
                MyLog.i("Percentage = " + info.getUsedSize() / (float) info.getMaxSize());
                Entry entry = PercentageBarChart.createEntry(0,
                        info.getUsedSize() / (float) info.getMaxSize(),
                        Color.GRAY);
                info.setUsedSizeEntry(entry);
                storageInfos.add(info);
            } else {
                info.setState(StorageState.UNMOUNTED);
            }
        }
        return storageInfos;
    }

    private void UpdatePercentageBarChart(PercentageBarChart mChart, List<Entry> mEntries, Entry entry) {
        clear(mEntries);
        addEntry(mEntries, entry);
        commit(mChart);
    }

    public void addEntry(List<Entry> mEntries, Entry entry) {
        mEntries.add(entry);
        Collections.sort(mEntries);
    }

    public void commit(PercentageBarChart mChart) {
        if (mChart != null) {
            mChart.invalidate();
        }
    }

    public void clear(List<Entry> mEntries) {
        mEntries.clear();
    }
}