package com.xxx.mvvm.ui.simplenetwork;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xxx.mvvm.R;
import com.xxx.mvvm.databinding.ItemSimpleNetworkBinding;
import com.xxx.mvvm.entity.DemoEntity;

public class SimpleAdapter extends BaseQuickAdapter<DemoEntity.ItemsEntity, BaseViewHolder> implements LoadMoreModule {


    public SimpleAdapter() {
        super(R.layout.item_simple_network);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DemoEntity.ItemsEntity itemsEntity) {
        // 获取 Binding  爆红是因为kt里面有，java找不到
        ItemSimpleNetworkBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.setViewModel(itemsEntity);
        binding.executePendingBindings();
    }
}
