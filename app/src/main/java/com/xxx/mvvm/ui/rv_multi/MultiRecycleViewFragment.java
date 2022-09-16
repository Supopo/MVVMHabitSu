package com.xxx.mvvm.ui.rv_multi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.xxx.mvvm.BR;
import com.xxx.mvvm.R;
import com.xxx.mvvm.databinding.FragmentMultiRvBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

public class MultiRecycleViewFragment extends BaseFragment<FragmentMultiRvBinding, MultiRecycleViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_multi_rv;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
