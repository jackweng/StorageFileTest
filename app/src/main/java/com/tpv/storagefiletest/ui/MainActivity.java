package com.tpv.storagefiletest.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tpv.storagefiletest.R;
import com.tpv.storagefiletest.domain.StorageInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "com.tpv.storagefiletest";

    private Context context;
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

        lv_storage_info = (ListView) findViewById(R.id.lv_main_storage_list);
        adapter = new StorageListAdapter(this, maps);
        lv_storage_info.setAdapter(adapter);
        UpdateView();
    }

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
            Log.i(TAG, "Can not get storagevolume list.");
        }
    }

    private void UpdateView() {
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
            // TODO show storageinfo to mainactivity.
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
            Log.i(TAG, "Can not find method getStorageVolume().");
        }
        getVolumeList.setAccessible(true);
        Object[] objs = (Object[]) getVolumeList.invoke(mStorageManager);
        for (Object obj : objs) {
            StorageInfo info = new StorageInfo();
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getStorageId = obj.getClass().getMethod("getStorageId");
                info.setStorageId((int) getStorageId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getStorageId.");
            }
            try {
                Method getPath = obj.getClass().getMethod("getPath");
                info.setPath((String) getPath.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getPath.");
            }
            try {
                Method getUserLabel = obj.getClass().getMethod("getUserLabel");
                info.setUserLabel((String) getUserLabel.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getUserLabel.");
            }
            try {
                Method isPrimary = obj.getClass().getMethod("isPrimary");
                info.setPrimary((boolean) isPrimary.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method isPrimary.");
            }
            try {
                Method isRemovable = obj.getClass().getMethod("isRemovable");
                info.setRemovable((boolean) isRemovable.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method isRemovable.");
            }
            try {
                Method isEmulated = obj.getClass().getMethod("isEmulated");
                info.setEmulated((boolean) isEmulated.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method isEmulated.");
            }
            try {
                Method getMtpReserveSpace = obj.getClass().getMethod("getMtpReserveSpace");
                info.setMtpReserveSize((int) getMtpReserveSpace.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getMtpReserveSpace.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
            try {
                Method getId = obj.getClass().getMethod("getId");
                info.setId((String) getId.invoke(obj));
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Can not find method getId.");
            }
        }
        // TODO
        return null;
    }
}