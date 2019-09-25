package com.tpv.storagefiletest.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tpv.storagefiletest.R;
import com.tpv.storagefiletest.application.MyApplication;
import com.tpv.storagefiletest.domain.TransResult;
import com.tpv.storagefiletest.ui.MainActivity;
import com.tpv.storagefiletest.utils.MyLog;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * Created by Jack.Weng on 2017/4/24.
 */

public class SpeedFragment extends Fragment {

    private Context context;
    private MyApplication mApp;
    private ArrayList<TransResult> results;

    private ListView lv_result_speed;
    private SpeedResultAdapter adapter;

    @Override
    public void onAttach(Context context) {
        MyLog.i("SpeedFragment,onAttach.");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MyLog.i("SpeedFragment,onCreate.");
        super.onCreate(savedInstanceState);
        context = MainActivity.context;
        mApp = (MyApplication) context.getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MyLog.i("SpeedFragment,onCreateView.");
        View view = inflater.inflate(R.layout.speed_fragment, container, false);
        if (mApp.getResults() != null) {
            results = mApp.getResults();
        } else {
            results = new ArrayList<>();
        }
        for (TransResult result : results) {
            MyLog.i(result.toString());
        }
        lv_result_speed = view.findViewById(R.id.lv_result_speed);
        adapter = new SpeedResultAdapter(context, results);
        lv_result_speed.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MyLog.i("SpeedFragment,onActivityCreated.");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        MyLog.i("SpeedFragment,onStart.");
        super.onStart();
    }

    @Override
    public void onResume() {
        MyLog.i("SpeedFragment,onResume.");
        super.onResume();
    }

    @Override
    public void onPause() {
        MyLog.i("SpeedFragment,onPause.");
        super.onPause();
    }

    @Override
    public void onStop() {
        MyLog.i("SpeedFragment,onStop.");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        MyLog.i("SpeedFragment,onDestroyView.");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        MyLog.i("SpeedFragment,onDestroy.");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        MyLog.i("SpeedFragment,onDetach.");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        MyLog.i("SpeedFragment,onSaveInstanceState.");
        super.onSaveInstanceState(outState);
    }

    private class SpeedHolderView {
        public TextView tv_index;
        public TextView tv_filename;
        public TextView tv_filesize;
        public TextView tv_time;
        public TextView tv_speed;
    }

    private class SpeedResultAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<TransResult> Results;

        public SpeedResultAdapter(Context context, ArrayList<TransResult> results) {
            MyLog.i("New adapter.");
            this.Results = results;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return Results.size();
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
            SpeedHolderView holder = new SpeedHolderView();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.speed_list_result, null);
                holder.tv_index = convertView.findViewById(R.id.tv_result_index_speed);
                holder.tv_filename = convertView.findViewById(R.id.tv_result_filename_speed);
                holder.tv_filesize = convertView.findViewById(R.id.tv_result_filesize_speed);
                holder.tv_time = convertView.findViewById(R.id.tv_result_time_speed);
                holder.tv_speed = convertView.findViewById(R.id.tv_result_speed_speed);
                convertView.setTag(holder);
            } else {
                holder = (SpeedHolderView) convertView.getTag();
            }
            holder.tv_index.setText(String.valueOf(Results.get(position).getTestIndex()));
            holder.tv_filename.setText(Results.get(position).getFileName());
            holder.tv_filesize.setText(Results.get(position).getFileSize());
            holder.tv_time.setText(Results.get(position).getTime());
            holder.tv_speed.setText(Results.get(position).getSpeed());
            return convertView;
        }
    }
}