package com.tpv.storagefiletest.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tpv.storagefiletest.R;
import com.tpv.storagefiletest.application.MyApplication;
import com.tpv.storagefiletest.domain.FileInfo;
import com.tpv.storagefiletest.domain.TestCase;
import com.tpv.storagefiletest.domain.TestInfo;
import com.tpv.storagefiletest.domain.TransResult;
import com.tpv.storagefiletest.ui.MainActivity;
import com.tpv.storagefiletest.utils.MyLog;
import com.tpv.storagefiletest.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Jack.Weng on 2017/4/21.
 */

public class TransFragment extends Fragment implements OnClickListener {

    private MyApplication mApp;
    private ListView lv_testcase;
    private ListView lv_result;
    private ListView lv_files;
    private EditText edt_count;

    private Button btn_start;

    private TestCaseAdapter testCaseAdapter;
    private TestResultAdapter testResultAdapter;
    private ArrayList<TestCase> AllTestCases;
    private ArrayList<FileInfo> infos;
    private Bundle saveInstanceState;
    private TransFragmentCallBack callBack;
    private ArrayList<TransResult> testResults;

    private Context context;

    private boolean canNext;

    private FileInfo SourceFileInfo;
    private String TargetDirectoryPath;
    private int TransCount;

    // fragment生命周期
    @Override
    public void onAttach(Context context) {
        MyLog.i("TransFragment,onAttach.");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyLog.i("TransFragment,onCreate.");
        super.onCreate(savedInstanceState);
        context = MainActivity.context;
        mApp = (MyApplication) context.getApplicationContext();
        callBack = (TransFragmentCallBack) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MyLog.i("TransFragment,onCreateView.");
        AllTestCases = getArguments().getParcelableArrayList("AllTestCases");
        View view = inflater.inflate(R.layout.trans_fragment, container, false);
        btn_start = (Button) view.findViewById(R.id.btn_start_test);
        btn_start.setOnClickListener(this);
        lv_testcase = (ListView) view.findViewById(R.id.lv_testcase_trans);
        testCaseAdapter = new TestCaseAdapter(getActivity(), AllTestCases);
        lv_testcase.setAdapter(testCaseAdapter);
        lv_testcase.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (TestCase test : AllTestCases) {
                    test.setNeedTest(false);
                }
                AllTestCases.get(position).setNeedTest(!AllTestCases.get(position).isNeedTest());
                testCaseAdapter.notifyDataSetChanged();
                callBack.ReturnTestCase(AllTestCases);
            }
        });
        if (mApp.getResults() != null) {
            testResults = mApp.getResults();
        } else {
            testResults = new ArrayList<>();
        }
        lv_result = (ListView) view.findViewById(R.id.lv_result_trans);
        testResultAdapter = new TestResultAdapter(context, testResults);
        lv_result.setAdapter(testResultAdapter);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        MyLog.i("TransFragment,onSaveInstanceState.");
        super.onSaveInstanceState(outState);
    }

    public void setTestCaseInfo(ArrayList<TestCase> cases) {
        if (this.AllTestCases == null) {
            this.AllTestCases = cases;
        } else {
            this.AllTestCases.clear();
            for (TestCase testCase : cases) {
                this.AllTestCases.add(testCase);
            }
        }
        testCaseAdapter.notifyDataSetChanged();
    }

    public void onTestEnd(ArrayList<TransResult> results) {
        if (this.testResults == null) {
            MyLog.i("testResults == null");
            this.testResults = results;
        } else {
            MyLog.i("testResults == null");
            this.testResults.clear();
            for (TransResult result : results) {
                this.testResults.add(result);
            }
        }
        testResultAdapter.notifyDataSetChanged();
    }

    private class TestCaseHolderView {
        public RadioButton rb_isneedtest;
        public TextView tv_testcase;
    }

    private class TestCaseAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<TestCase> Cases;

        public TestCaseAdapter(Context context, ArrayList<TestCase> infos) {
            MyLog.i("New adapter.");
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
            TestCaseHolderView holder = new TestCaseHolderView();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.trans_list, null);
                holder.rb_isneedtest = (RadioButton) convertView.findViewById(R.id.rb_isneedtest);
                holder.tv_testcase = (TextView) convertView.findViewById(R.id.tv_testcase);
                convertView.setTag(holder);
            } else {
                holder = (TestCaseHolderView) convertView.getTag();
            }
            holder.rb_isneedtest.setChecked(Cases.get(position).isNeedTest());
            holder.tv_testcase.setText(Cases.get(position).getStorageInfos()[0].getUserLabel()
                    + getString(R.string.from_to)
                    + Cases.get(position).getStorageInfos()[1].getUserLabel());
            return convertView;
        }
    }

    private class TestResultHolderView {
        public TextView tv_index;
        public TextView tv_filename;
        public TextView tv_testresult;
    }

    private class TestResultAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<TransResult> Results;

        public TestResultAdapter(Context context, ArrayList<TransResult> results) {
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
            TestResultHolderView holder = new TestResultHolderView();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.trans_list_result, null);
                holder.tv_index = (TextView) convertView.findViewById(R.id.tv_result_index);
                holder.tv_filename = (TextView) convertView.findViewById(R.id.tv_result_filename);
                holder.tv_testresult = (TextView) convertView.findViewById(R.id.tv_result_result);
                convertView.setTag(holder);
            } else {
                holder = (TestResultHolderView) convertView.getTag();
            }
            holder.tv_index.setText(String.valueOf(Results.get(position).getTestIndex()));
            holder.tv_filename.setText(Results.get(position).getFileName());
            if (Results.get(position).getResult()) {
                holder.tv_testresult.setTextColor(Color.GREEN);
                holder.tv_testresult.setText(getString(R.string.result_true));
            } else {
                holder.tv_testresult.setTextColor(Color.RED);
                holder.tv_testresult.setText(getString(R.string.result_false));
            }
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_test:
                for (final TestCase testCase : AllTestCases) {
                    if (testCase.isNeedTest()) {
                        if (Utils.CheckFileIsExists(testCase.getStorageInfos()[0].getPath(),
                                testCase.getStorageInfos()[1].getPath())){
                            canNext = false;
                            TargetDirectoryPath = testCase.getStorageInfos()[1].getPath() + MainActivity.ROOTPATH + MainActivity.TARGETPATH;
                            infos = getSourceFileLists(testCase.getStorageInfos()[0].getPath());
                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setCancelable(false);
                            builder.setTitle(getString(R.string.title_choose_file));
                            LayoutInflater inflater = LayoutInflater.from(context);
                            View view = inflater.inflate(R.layout.dialog_files_list, null);
                            lv_files = (ListView) view.findViewById(R.id.lv_trans_files);
                            final DialogListAdapter dialogListAdapter = new DialogListAdapter(context, infos);
                            builder.setView(view);
                            builder.setPositiveButton(getString(R.string.dialog_next), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (canNext) {
                                        showSetCountDialog();
                                    } else {
                                        Toast.makeText(context, getString(R.string.message_choose_file_error), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            builder.setNegativeButton(getString(R.string.dialog_cancle), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create();
                            lv_files.setAdapter(dialogListAdapter);
                            lv_files.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    canNext = true;
                                    for (FileInfo info : infos) {
                                        info.setChecked(false);
                                    }
                                    infos.get(position).setChecked(true);
                                    dialogListAdapter.notifyDataSetChanged();
                                    SourceFileInfo = infos.get(position);
                                }
                            });
                            builder.show();
                        } else {
                            Toast.makeText(MainActivity.context,
                                    getString(R.string.message_source_file_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private class DialogView {
        public RadioButton rb_choose;
        public TextView tv_name;
        public TextView tv_size;
    }

    private class DialogListAdapter extends BaseAdapter {

        LayoutInflater inflater;
        ArrayList<FileInfo> infos = new ArrayList<>();

        public DialogListAdapter(Context context, ArrayList<FileInfo> infos) {
            this.infos = infos;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DialogView dialogView = new DialogView();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_files, null);
                dialogView.rb_choose = (RadioButton) convertView.findViewById(R.id.rb_files_item);
                dialogView.tv_name = (TextView) convertView.findViewById(R.id.tv_files_name);
                dialogView.tv_size = (TextView) convertView.findViewById(R.id.tv_files_size);
                convertView.setTag(dialogView);
            } else {
                dialogView = (DialogView) convertView.getTag();
            }
            dialogView.rb_choose.setChecked(infos.get(position).isChecked());
            dialogView.tv_name.setText(infos.get(position).getFileName());
            dialogView.tv_size.setText(infos.get(position).getFileSize());
            return convertView;
        }
    }

    public interface TransFragmentCallBack {
        void ReturnTestCase(ArrayList<TestCase> testCases);
        void onTestStart(TestInfo info);
    }

    private ArrayList<FileInfo> getSourceFileLists(String path) {
        ArrayList<FileInfo> list = new ArrayList<>();
        File directory = new File(path + MainActivity.ROOTPATH + MainActivity.SOURCEPATH);
        if (directory.exists() && directory.isDirectory()) {
            File[] childs = directory.listFiles();
            for (File child : childs) {
                FileInfo info = new FileInfo();
                info.setChecked(false);
                info.setFileName(child.getName());
                info.setFilePath(child.getAbsolutePath());
                try {
                    FileInputStream fis = new FileInputStream(child);
                    info.setFileSizeInt(fis.available());
                    info.setFileSize(Formatter.formatFileSize(context, fis.available()));
                } catch (Exception e) {
                    info.setFileSizeInt(0);
                    info.setFileSize("0 KB");
                }
                list.add(info);
            }
        }
        return list;
    }

    private void showSetCountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.title_trans_count));
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_trans_count, null);
        edt_count = (EditText) view.findViewById(R.id.edt_count);
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edt_count.getText())) {
                    Toast.makeText(context, "", Toast.LENGTH_LONG).show();
                } else {
                    TransCount = Integer.parseInt(edt_count.getText().toString());
                    TestInfo info = new TestInfo();
                    info.setSourceFileInfo(SourceFileInfo);
                    info.setTargetPath(TargetDirectoryPath);
                    info.setCount(TransCount);
                    callBack.onTestStart(info);
                }
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_cancle), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}