package com.tpv.storagefiletest.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
import com.tpv.storagefiletest.domain.FileInfo;
import com.tpv.storagefiletest.domain.TestCase;
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

    private ListView lv_testcase;
    private TextView tv_result;
    private ListView lv_files;
    private EditText edt_count;

    private Button btn_start;

    private TestCaseAdapter adapter;
    private ArrayList<TestCase> AllTestCases;
    private ArrayList<FileInfo> infos;
    private Bundle saveInstanceState;
    private TransFragmentCallBack callBack;
    private String testresult;

    private Context context;

    private boolean canNext;

    private String SourceFilePath;
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
        callBack = (TransFragmentCallBack) getActivity();
        context = MainActivity.context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MyLog.i("TransFragment,onCreateView.");
        AllTestCases = getArguments().getParcelableArrayList("AllTestCases");
        View view = inflater.inflate(R.layout.trans_fragment, container, false);
        btn_start = (Button) view.findViewById(R.id.btn_start_test);
        btn_start.setOnClickListener(this);
        lv_testcase = (ListView) view.findViewById(R.id.lv_testcase_main);
        adapter = new TestCaseAdapter(getActivity(), AllTestCases);
        lv_testcase.setAdapter(adapter);
        lv_testcase.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (TestCase test : AllTestCases) {
                    test.setNeedTest(false);
                }
                AllTestCases.get(position).setNeedTest(!AllTestCases.get(position).isNeedTest());
                adapter.notifyDataSetChanged();
                callBack.ReturnTestCase(AllTestCases);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        MyLog.i("TransFragment,onSaveInstanceState.");
        super.onSaveInstanceState(outState);
    }

    public void setTestCaseInfo(ArrayList<TestCase> cases) {
        MyLog.i("#####setTestCaseInfo#####");
        if (this.AllTestCases == null) {
            this.AllTestCases = cases;
        } else {
            this.AllTestCases.clear();
            for (TestCase testCase : cases) {
                this.AllTestCases.add(testCase);
            }
        }
    }

    public void setTestResult(String result) {
        testresult = result;
    }

    private class HolderView {
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
            HolderView holder = new HolderView();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.trans_list, null);
                holder.rb_isneedtest = (RadioButton) convertView.findViewById(R.id.rb_isneedtest);
                holder.tv_testcase = (TextView) convertView.findViewById(R.id.tv_testcase);
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }
            holder.rb_isneedtest.setChecked(Cases.get(position).isNeedTest());
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
                                    SourceFilePath = infos.get(position).getFilePath();
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
                    info.setFileSize(Formatter.formatFileSize(context, fis.available()));
                } catch (Exception e) {
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
                    Toast.makeText(context,
                            "SourceFilePath = " + SourceFilePath
                            + "\nTargetDirectoryPath = " + TargetDirectoryPath
                            + "\nTransCount = " + TransCount,
                            Toast.LENGTH_LONG).show();
                    Utils.CopyFileResult(SourceFilePath, TargetDirectoryPath, TransCount);
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