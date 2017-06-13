package com.tpv.storagefiletest.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tpv.storagefiletest.R;
import com.tpv.storagefiletest.domain.StorageInfo;
import com.tpv.storagefiletest.domain.StorageState;
import com.tpv.storagefiletest.domain.TestCase;
import com.tpv.storagefiletest.domain.TestInfo;
import com.tpv.storagefiletest.domain.TransResult;
import com.tpv.storagefiletest.fragment.SpeedFragment;
import com.tpv.storagefiletest.fragment.StressFragment;
import com.tpv.storagefiletest.fragment.TransFragment;
import com.tpv.storagefiletest.ui.PercentageBarChart.Entry;
import com.tpv.storagefiletest.utils.MyLog;
import com.tpv.storagefiletest.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener,
        TransFragment.TransFragmentCallBack {

    public static final String TAG = "com.tpv.storagefiletest";
    private static final String EMMC = "/storage/emulated/0";
    private static final String SDCARD1 = "/mnt/external_sd";
    private static final String SDCARD2= "/mnt/sdcard";
    public static final int MEDIA_MOUNTED_UPDATE_UI = 0;
    public static final int START_FILE_TRANS_TEST = 1;
    public static final int SHOW_DIALOG_TEST_COUNT = 2;

    public static final String ROOTPATH = "/TpvStorageTest";
    public static final String SOURCEPATH = "/source";
    public static final String TARGETPATH = "/target";

    //fragments
    private static int CurrentFragment = 0;
    private static TransFragment transFragment;
    private static SpeedFragment speedFragment;
    private static StressFragment stressFragment;

    //textview in fragment
    private static TextView tv_trans;
    private static TextView tv_speed;
    private static TextView tv_stress;

    private static FragmentManager fragmentManager;

    public static Context context;
    private int WinWidth;
    private ListView lv_storage_info;
    private static StorageManager mStorageManager;
    private static StorageListAdapter adapter;
    private static ArrayList<StorageInfo> maps = new ArrayList<>();
    private static ArrayList<TestCase> testCases = new ArrayList<>();

    private static String transResult = "result:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyLog.i("MianActivity.onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WinWidth = metrics.widthPixels;

        initView();
        if (savedInstanceState == null) {
            try {
                maps = getStorageInfo();
                testCases = Utils.getTestCase(maps);
            } catch (Exception e) {
                e.printStackTrace();
                MyLog.i("Can not get storage info.");
            }
        } else {
            testCases = savedInstanceState.getParcelableArrayList("TestCases");
        }
        adapter = new StorageListAdapter(this, maps);
        lv_storage_info.setAdapter(adapter);
        fragmentManager = getFragmentManager();
        setTabSelection(CurrentFragment);
    }

    @Override
    protected void onResume() {
        MyLog.i("MianActivity.onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        MyLog.i("MianActivity.onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        MyLog.i("MianActivity.onRestart");
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        MyLog.i("MianActivity.onSaveInstanceState");
        outState.putParcelableArrayList("TestCases", testCases);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        MyLog.i("MianActivity.onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        MyLog.i("MianActivity.onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        MyLog.i("MainActivity.onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_trans_fragment:
                CurrentFragment = 0;
                break;
            case R.id.tv_speed_fragment:
                CurrentFragment = 1;
                break;
            case R.id.tv_stress_fragment:
                CurrentFragment = 2;
                break;
            default:
                break;
        }
        setTabSelection(CurrentFragment);
    }

    private void initView() {
        lv_storage_info = (ListView) findViewById(R.id.lv_main_storage_list);
        tv_trans = (TextView) findViewById(R.id.tv_trans_fragment);
        tv_trans.setOnClickListener(this);
        tv_speed = (TextView) findViewById(R.id.tv_speed_fragment);
        tv_speed.setOnClickListener(this);
        tv_stress = (TextView) findViewById(R.id.tv_stress_fragment);
        tv_stress.setOnClickListener(this);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MEDIA_MOUNTED_UPDATE_UI:
                    if (context != null) {
                        UpdateStorageInfo();
                        UpdateTestCaseInfo();
                        adapter.notifyDataSetChanged();
                        UpdateFragmentData(CurrentFragment);
                    }
                    break;
                case START_FILE_TRANS_TEST:
                    TestInfo info = (TestInfo) msg.obj;
                    TransFileTask task = new TransFileTask(context);
                    task.execute(info);
                    break;
                case SHOW_DIALOG_TEST_COUNT:
                    break;
                default:
                    break;
            }
        }
    };

    private static void UpdateTestCaseInfo() {
        try {
            if (testCases.size() == 0) {
                testCases = Utils.getTestCase(maps);
            } else {
                testCases.clear();
                ArrayList<TestCase> tempInfos = Utils.getTestCase(maps);
                for (TestCase info : tempInfos) {
                    testCases.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.i("Can not get storagevolume list.");
        }
    }
    private static void UpdateStorageInfo() {
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
            HolderView holder = new HolderView();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_storage_main, null);
                holder.tv_label = (TextView) convertView.findViewById(R.id.tv_storage_label);
                holder.tv_label.setWidth(WinWidth / 10);
                holder.tv_freesize = (TextView) convertView.findViewById(R.id.tv_storage_freesize);
                holder.tv_freesize.setWidth(WinWidth / 6);
                holder.pbc_freesize = (PercentageBarChart) convertView.findViewById(R.id.pbc_storage_freesize);
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }
            holder.tv_label.setText(map.get(position).getUserLabel());
            holder.tv_freesize.setText(Formatter.formatFileSize(context, map.get(position).getFreeSize())
                    + "/" + Formatter.formatFileSize(context, map.get(position).getMaxSize()));
            List<Entry> mEntries = new ArrayList<>();
            holder.pbc_freesize.setEntries(mEntries);
            UpdatePercentageBarChart(holder.pbc_freesize, mEntries, map.get(position).getUsedSizeEntry());
            return convertView;
        }
    }

    private static ArrayList<StorageInfo> getStorageInfo() throws InvocationTargetException, IllegalAccessException {
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
                } else if (info.getPath().equals(SDCARD1) || info.getPath().equals(SDCARD2)) {
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

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示档案传输，1表示读写速度，2表示Stress。
     */
    private static void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                tv_trans.setTextColor(Color.BLACK);
                tv_trans.setBackgroundColor(Color.WHITE);
                if (transFragment == null) {
                    transFragment = new TransFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("AllTestCases", testCases);
                transFragment.setArguments(bundle);
                transaction.replace(R.id.fragment_main, transFragment);
//                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
            case 1:
                tv_speed.setTextColor(Color.BLACK);
                tv_speed.setBackgroundColor(Color.WHITE);
                if (speedFragment == null) {
                    speedFragment = new SpeedFragment();
                }
                transaction.replace(R.id.fragment_main, speedFragment);
//                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
            case 2:
                tv_stress.setTextColor(Color.BLACK);
                tv_stress.setBackgroundColor(Color.WHITE);
                if (stressFragment == null) {
                    stressFragment = new StressFragment();
                }
                transaction.replace(R.id.fragment_main, stressFragment);
//                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private static void clearSelection() {
        tv_trans.setTextColor(Color.parseColor("#ffffffff"));
        tv_trans.setBackgroundColor(Color.argb(0, 255, 255, 255));
        tv_speed.setTextColor(Color.parseColor("#ffffffff"));
        tv_speed.setBackgroundColor(Color.argb(0, 255, 255, 255));
        tv_stress.setTextColor(Color.parseColor("#ffffffff"));
        tv_stress.setBackgroundColor(Color.argb(0, 255, 255, 255));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private static void hideFragments(FragmentTransaction transaction) {
        if (transFragment != null) {
            transaction.hide(transFragment);
            transFragment = null;
        }
        if (speedFragment != null) {
            transaction.hide(speedFragment);
            speedFragment = null;
        }
        if (stressFragment != null) {
            transaction.hide(stressFragment);
            stressFragment = null;
        }
    }

    private static void UpdateFragmentData(int index) {
        switch (index) {
            case 0:
                transFragment.setTestCaseInfo(testCases);
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    @Override
    public void ReturnTestCase(ArrayList<TestCase> testCases) {
        MainActivity.testCases = testCases;
    }

    private class TransFileTask extends AsyncTask<TestInfo, Integer, Boolean> {

        private ArrayList<TransResult> results = new ArrayList<>();
        private int i;
        private int j;
        private boolean isfileexist;
        private ProgressDialog pDialog;

        public TransFileTask(Context context) {
            isfileexist = false;
            i = 0;
            j = 0;
            pDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPostExecute(Boolean value) {
            super.onPostExecute(value);
        }

        @Override
        protected void onProgressUpdate(Integer[] values) {
            if (values[0] > 0) {
                pDialog.setMessage(FormatTestCountString(values[0]));
            } else {
                pDialog.setMessage(FormatTestCountString(values[0]));
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(TestInfo[] params) {
            File SourceFile = new File(params[0].getSourceFileInfo().getFilePath());
            File TargetFile = new File(params[0].getTargetPath() + "/" + params[0].getSourceFileInfo().getFileName());
            while (i++ < params[0].getCount()) {
                while (TargetFile.exists()) {
                    TargetFile = new File(params[0].getTargetPath()
                            + "/" + Utils.getFileNameNoEx(params[0].getSourceFileInfo().getFileName())
                            + (++j) + "."
                            + Utils.getExtensionName(params[0].getSourceFileInfo().getFileName()));
                }
                TransResult result = new TransResult();
                result.setTestIndex(i);
                long starttime = System.currentTimeMillis();
                try {
                    result.setIndexResult(Utils.TransferCopy(SourceFile, TargetFile));
                    result.setIndexReason("");
                } catch (IOException e) {
                    result.setIndexResult(false);
                    result.setIndexReason("IOException");
                }
                result.setIndexTime(System.currentTimeMillis() - starttime);
                results.add(result);
                publishProgress(i);
            }
            Log.i(TAG, "i = " + i);
            return true;
        }
    }

    private String FormatTestCountString(int count) {
        return getResources().getQuantityString(R.plurals.show_test_countdown, count, count);
    }
}