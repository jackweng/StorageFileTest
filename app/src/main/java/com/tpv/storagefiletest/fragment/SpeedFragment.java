package com.tpv.storagefiletest.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tpv.storagefiletest.R.layout;

/**
 * Created by Jack.Weng on 2017/4/24.
 */

public class SpeedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layout.speed_fragment, container, false);
    }
}