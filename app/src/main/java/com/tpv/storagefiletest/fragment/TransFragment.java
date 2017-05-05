package com.tpv.storagefiletest.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.tpv.storagefiletest.R;
import com.tpv.storagefiletest.R.layout;
import com.tpv.storagefiletest.domain.StorageInfo;
import com.tpv.storagefiletest.domain.TestCase;
import com.tpv.storagefiletest.ui.MainActivity;
import com.tpv.storagefiletest.utils.MyLog;
import com.tpv.storagefiletest.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Jack.Weng on 2017/4/21.
 */

public class TransFragment extends Fragment implements OnClickListener {

    private Context context;
    private ListView lv_testcase;
    private TestCaseAdapter adapter;
    private ArrayList<StorageInfo> Infos;
    private ArrayList<TestCase> AllTestCases;
    private boolean[] isNeedTest;
    private ArrayList<TestCase> NeedTestCases;

    // fragment生命周期从上到下
    @Override
    public void onAttach(Context context) {
        MyLog.i("TransFragment,onAttach.");
        Infos = ((MainActivity) context).getStorageInfoList();
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MyLog.i("TransFragment,onCreate.");
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        AllTestCases = Utils.getTestCase(Infos);
        if (Infos == null) {
            MyLog.i("Infos is null.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MyLog.i("TransFragment,onCreateView.");
        View view = inflater.inflate(layout.trans_fragment, container, false);
        lv_testcase = (ListView) view.findViewById(R.id.lv_testcase_main);
        adapter = new TestCaseAdapter(context, AllTestCases);
        lv_testcase.setAdapter(adapter);
        lv_testcase.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyLog.i("OnItemClick");
                AllTestCases.get(position).setNeedTest(!AllTestCases.get(position).isNeedTest());
                MyLog.i("isNeedTest = " + AllTestCases.get(position).isNeedTest());
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MyLog.i("TransFragment,onActivityCreated.");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        MyLog.i("TransFragment,onStart.");
        super.onStart();
    }

    @Override
    public void onResume() {
        MyLog.i("TransFragment,onResume.");
        super.onResume();
    }

    @Override
    public void onPause() {
        MyLog.i("TransFragment,onPause.");
        super.onPause();
    }

    @Override
    public void onStop() {
        MyLog.i("TransFragment,onStop.");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        MyLog.i("TransFragment,onDestroyView.");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        MyLog.i("TransFragment,onDestroy.");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        MyLog.i("TransFragment,onDetach.");
        super.onDetach();
    }

    public void setStorageInfos(ArrayList<StorageInfo> infos) {
        if (this.Infos == null) {
            this.Infos = infos;
        } else {
            this.Infos.clear();
            for (StorageInfo info : infos) {
                this.Infos.add(info);
            }
        }
    }

    public void UpdateListView() {
        adapter.notifyDataSetChanged();
    }

    private class HolderView {
        public CheckBox cbox_isneedtest;
        public TextView tv_testcase;
    }

    private class TestCaseAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<TestCase> Cases;

        public TestCaseAdapter(Context context, ArrayList<TestCase> infos) {
            this.Cases = infos;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return Cases.size();
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
                convertView = inflater.inflate(layout.trans_list, null);
                holder.cbox_isneedtest = (CheckBox) convertView.findViewById(R.id.cbox_isneedtest);
                holder.tv_testcase = (TextView) convertView.findViewById(R.id.tv_testcase);
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }
            holder.cbox_isneedtest.setChecked(Cases.get(position).isNeedTest());
            holder.tv_testcase.setText(Cases.get(position).getStorageInfos()[0].getUserLabel()
                    + getString(R.string.from_to)
                    + Cases.get(position).getStorageInfos()[1].getUserLabel());
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_test:
                break;
            default:
                break;
        }
    }
}