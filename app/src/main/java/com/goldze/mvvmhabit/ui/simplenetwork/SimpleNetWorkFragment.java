package com.goldze.mvvmhabit.ui.simplenetwork;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.goldze.mvvmhabit.BR;
import com.goldze.mvvmhabit.R;
import com.goldze.mvvmhabit.databinding.FragmentNetworkBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 简单的网络请求
 */
public class SimpleNetWorkFragment extends BaseFragment<FragmentNetworkBinding, SimpleNetWorkViewModel> {

    @Override
    public void initParam() {
        super.initParam();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_simple_network;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        showDialog("请求中...");
        viewModel.demoGet();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.disDialog.observe(this, disDialog -> {
            if (disDialog != null && disDialog) {
                dismissDialog();
            }
        });

        viewModel.dataResult.observe(this, demoEntity -> {
            if (demoEntity != null && !demoEntity.getItems().isEmpty()) {
                viewModel.initListData(demoEntity);
            }
        });
    }
}
