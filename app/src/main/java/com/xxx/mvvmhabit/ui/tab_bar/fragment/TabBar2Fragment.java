package com.xxx.mvvmhabit.ui.tab_bar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xxx.mvvmhabit.BR;
import com.xxx.mvvmhabit.R;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;

public class TabBar2Fragment extends BaseFragment {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_2;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
