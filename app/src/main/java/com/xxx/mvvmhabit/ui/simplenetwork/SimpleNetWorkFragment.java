package com.xxx.mvvmhabit.ui.simplenetwork;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xxx.mvvmhabit.BR;
import com.xxx.mvvmhabit.R;
import com.xxx.mvvmhabit.databinding.FragmentSimpleNetworkBinding;
import com.xxx.mvvmhabit.ui.network.detail.DetailFragment;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 简单的网络请求
 */
public class SimpleNetWorkFragment extends BaseFragment<FragmentSimpleNetworkBinding, SimpleNetWorkViewModel> {

    private SimpleAdapter mAdapter;

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
        mAdapter = new SimpleAdapter();
        binding.rvContent.setAdapter(mAdapter);

        showDialog("请求中...");
        viewModel.demoGet();

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            //跳转到详情界面,传入条目的实体对象
            Bundle mBundle = new Bundle();
            mBundle.putParcelable("entity", adapter.getItems().get(position));
            viewModel.startContainerActivity(DetailFragment.class.getCanonicalName(), mBundle);
            return null;
        });

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
                mAdapter.submitList(demoEntity.getItems());
            }
        });
    }
}
